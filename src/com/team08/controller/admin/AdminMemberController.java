package com.team08.controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team08.dto.MemberVO;
import com.team08.service.admin.AdminMemberService;

/**
 * Servlet implementation class AdminMemberController
 */
@WebServlet("/admin/members/*")
public class AdminMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminMemberService adminMemberService;
       
    public AdminMemberController() {
    	adminMemberService = new AdminMemberService();
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
			nextPage = "/admin/main.jsp";
		} else if (action.equals("/login.do")) {
			nextPage = "/admin/members/loginForm.do";
			String msg = "";
			String workerId = request.getParameter("workerId");
			String workerPwd = request.getParameter("workerPwd");
			
			int result = adminMemberService.workerLogin(workerId, workerPwd);
			if (result == 1) {
				HttpSession session = request.getSession();
				session.setAttribute("workerId", workerId);
				nextPage = "/admin/items/list";
				System.out.println("admin/members/login.do");
			} else if (result == 0) {
				msg = "비밀번호를 확인하세요.";
			} else if (result == -1) {
				msg = "아이디를 확인하세요";
			}
			request.setAttribute("message", msg);
		} else if (action.equals("/logout.do")) {
			nextPage = "/admin/members/loginForm.do";
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
				request.setAttribute("message", "");
			}
		} else if (action.equals("/memberList")) {
			nextPage = "/admin/member/memberList.jsp";
			String key = "";
			if (request.getParameter("key") != null) {
				key = request.getParameter("key");
			}
			ArrayList<MemberVO> memberList = adminMemberService.selectMembers(key);
			request.setAttribute("memberList", memberList);			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
