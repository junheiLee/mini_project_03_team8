package com.team08.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team08.dto.CartVO;
import com.team08.dto.MemberVO;
import com.team08.service.CartService;

/**
 * Servlet implementation class CartController
 */
@WebServlet("/carts/*")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CartService cartService;
       
    public CartController() {
    	cartService = new CartService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo();
		
		if (action.equals("/cartList")) {
			nextPage = "/mypage/cartList.jsp";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				ArrayList<CartVO> cartList = cartService.selectCarts(loginUser.getId());
				int totalPrice = 0;
				for (CartVO cartVO : cartList) {
					totalPrice += cartVO.getPrice2() * cartVO.getQuantity();
				}
				request.setAttribute("cartList", cartList);
				request.setAttribute("totalPrice", totalPrice);
			}
		} else if (action.equals("/cartInsert")) {
			nextPage = "/carts/cartList";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				CartVO cartVO = new CartVO();
				String id = loginUser.getId();
				int pseq = Integer.parseInt(request.getParameter("pseq"));
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				cartVO.setId(id);
				cartVO.setPseq(pseq);
				cartVO.setQuantity(quantity);
				cartService.insertCart(cartVO);
			}
		} else if (action.equals("/cartDelete")) {
			nextPage = "/carts/cartList";
			String[] cseqArr = request.getParameterValues("cseq");
			for (String cseq : cseqArr) {
				cartService.deleteCart(Integer.parseInt(cseq));
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
