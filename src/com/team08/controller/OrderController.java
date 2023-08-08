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
import com.team08.dto.OrderVO;
import com.team08.service.CartService;
import com.team08.service.OrderService;

/**
 * Servlet implementation class OrderController
 */                          
@WebServlet("/orders/*")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService;
	private CartService cartService;
       
    public OrderController() {
    	orderService = new OrderService();
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
		
		if (action.equals("/mypage")) {
			nextPage = "/mypage/mypage.jsp";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				ArrayList<Integer> oseqList = orderService.selectSeqOrderIng(loginUser.getId());
				ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
				for (int oseq : oseqList) {
					ArrayList<OrderVO> orderListIng = orderService.listOrderById(loginUser.getId(), "1", oseq);
					OrderVO orderVO = orderListIng.get(0);
					orderVO.setPname(orderVO.getPname() + " 외 " + orderListIng.size() + "건");

					int totalPrice = 0;
					for (OrderVO ovo : orderListIng) {
						totalPrice += ovo.getPrice2() * ovo.getQuantity();
					}
					orderVO.setPrice2(totalPrice);
					orderList.add(orderVO);
				}
				request.setAttribute("title", "진행 중인 주문 내역");
				request.setAttribute("orderList", orderList);
			}
		} else if (action.equals("/orderAll")) {
			nextPage = "/mypage/mypage.jsp";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				ArrayList<Integer> oseqList = orderService.selectSeqOrderIng(loginUser.getId());
				ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
				for (int oseq : oseqList) {
					ArrayList<OrderVO> orderListIng = orderService.listOrderById(loginUser.getId(), "1", oseq);
					OrderVO orderVO = orderListIng.get(0);
					orderVO.setPname(orderVO.getPname() + " 외 " + orderListIng.size() + "건");

					int totalPrice = 0;
					for (OrderVO ovo : orderListIng) {
						totalPrice += ovo.getPrice2() * ovo.getQuantity();
					}
					orderVO.setPrice2(totalPrice);
					orderList.add(orderVO);
				}
				request.setAttribute("title", "진행 중인 주문 내역");
				request.setAttribute("orderList", orderList);
			}
		} else if (action.equals("/orderDetail")) {
			nextPage = "/mypage/orderDetail.jsp";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				int oseq = Integer.parseInt(request.getParameter("oseq"));
				ArrayList<OrderVO> orderList = orderService.listOrderById(loginUser.getId(), "%", oseq);
				int totalPrice = 0;
				for (OrderVO ovo : orderList) {
					totalPrice += ovo.getPrice2() * ovo.getQuantity();
				}
				request.setAttribute("orderDetail", orderList.get(0));
				request.setAttribute("orderList", orderList);
				request.setAttribute("totalPrice", totalPrice);
			}
		} else if (action.equals("/orderInsert")) {
			nextPage = "/orders/orderList";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				ArrayList<CartVO> cartList = cartService.selectCarts(loginUser.getId());
				int maxOseq = orderService.insertOrder(cartList, loginUser.getId());
				nextPage = "/orders/orderList?oseq=" + maxOseq;
			}
		} else if (action.equals("/orderNowInsert")) {
			nextPage = "/orders/orderList";
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
				
				ArrayList<CartVO> cartList = new ArrayList<CartVO>();
				cartList.add(cartVO);
				int maxOseq = orderService.insertOrder(cartList, loginUser.getId());
				nextPage = "/orders/orderList?oseq=" + maxOseq;
			}
		} else if (action.equals("/orderList")) {
			nextPage = "/mypage/orderList.jsp";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				int oseq = Integer.parseInt(request.getParameter("oseq"));
				ArrayList<OrderVO> orderList = orderService.listOrderById(loginUser.getId(), "1", oseq);
				int totalPrice = 0;
				for (OrderVO orderVO : orderList) {
					totalPrice += orderVO.getPrice2() * orderVO.getQuantity();
				}
				request.setAttribute("orderList", orderList);
				request.setAttribute("totalPrice", totalPrice);
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
