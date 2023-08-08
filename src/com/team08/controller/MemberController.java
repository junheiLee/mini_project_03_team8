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

import com.team08.dto.AddressVO;
import com.team08.dto.MemberVO;
import com.team08.service.MemberService;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/members/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService;
       
    public MemberController() {
    	memberService = new MemberService();
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
		                                  
		if (action.equals("/loginForm.do")) {
			nextPage = "/member/login.jsp"; 
		} else if (action.equals("/login.do")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			
			MemberVO loginUser = memberService.login(id, pwd);
			if (loginUser != null) {
				request.getSession().setAttribute("loginUser", loginUser);
				nextPage = "/main";
			} else {
				nextPage = "/member/login_fail.jsp";
			}
		} else if (action.equals("/logout.do")) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			nextPage = "/main";
		} else if (action.equals("/contract.do")) {
			nextPage = "/member/contract.jsp";
		} else if (action.equals("/joinForm.do")) {
			nextPage = "/member/join.jsp";
		} else if (action.equals("/join.do")) {
			MemberVO memberVO = new MemberVO();
			memberVO.setId(request.getParameter("id"));
			memberVO.setPwd(request.getParameter("pwd"));
			memberVO.setName(request.getParameter("name"));
			memberVO.setEmail(request.getParameter("email"));
			memberVO.setZipNum(request.getParameter("zipNum"));
			memberVO.setAddress(request.getParameter("addr1") + " " + request.getParameter("addr2"));
			memberVO.setPhone(request.getParameter("phone"));
			
			int result = memberService.join(memberVO);
			if (result > 0) {
				nextPage = "/member/login.jsp";
			} else {
				nextPage = "/member/join_fail.jsp";
			}
		} else if (action.equals("/id_check_form")) {
			String id = request.getParameter("id");
			int message = memberService.confirmID(id);
			request.setAttribute("message", message);
			request.setAttribute("id", id);
			nextPage = "/member/idcheck.jsp";
		} else if (action.equals("/find_zip_num")) {
			String dong = request.getParameter("dong");
			if (dong != null && dong.trim().equals("") == false) {
				ArrayList<AddressVO> addressList = memberService.selectAddressByDong(dong);
				request.setAttribute("addressList", addressList);
			}
			nextPage = "/member/findZipNum.jsp";
		} else if (action.equals("/find_id_form")) {
			nextPage = "/member/findIdAndPassword.jsp";
		} else if (action.equals("/find_member_id.do")) {
			String name  = request.getParameter("name");
			String email = request.getParameter("email");
			String id = memberService.findMemberId(name, email);
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(id);
			return;
		} else if (action.equals("/find_member_password.do")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = memberService.findMemberPassword(id, name, email);
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(password);
			return;
		} 

		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
