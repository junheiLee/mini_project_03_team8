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

import com.team08.dto.MemberVO;
import com.team08.dto.QnaVO;
import com.team08.service.QnaService;

/**
 * Servlet implementation class QnaController
 */
@WebServlet("/qnas/*")
public class QnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private QnaService qnaService;
       
    public QnaController() {
    	qnaService = new QnaService();
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
		
		if (action.equals("/qnaList")) {
			nextPage = "/qna/qnaList.jsp";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				ArrayList<QnaVO> qnaList = qnaService.selectQnas(loginUser.getId());
				request.setAttribute("qnaList", qnaList);
			}
		} else if (action.equals("/qnaView")) {
			nextPage = "/qna/qnaView.jsp";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				int qseq = Integer.parseInt(request.getParameter("qseq"));
				QnaVO qnaVO = qnaService.getQna(qseq);
				request.setAttribute("qnaVO", qnaVO);
			}
		} else if (action.equals("/qnaWriteForm")) {
			nextPage = "/qna/qnaWrite.jsp";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			}
		} else if (action.equals("/qnaWrite")) {
			nextPage = "/qnas/qnaList";
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				nextPage = "/members/loginForm.do";
			} else {
				QnaVO qnaVO = new QnaVO();
				qnaVO.setSubject(request.getParameter("subject"));
				qnaVO.setContent(request.getParameter("content"));
				qnaService.insertQna(qnaVO, loginUser.getId());
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
