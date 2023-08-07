package com.team08.controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team08.dto.QnaVO;
import com.team08.service.admin.AdminQnaService;

/**
 * Servlet implementation class AdminQnaController
 */
@WebServlet("/admin/qnas/*")
public class AdminQnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminQnaService adminQnaService;
       
    public AdminQnaController() {
    	adminQnaService = new AdminQnaService();
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
			nextPage = "/admin/qna/qnaList.jsp";
			ArrayList<QnaVO> qnaList = adminQnaService.selectAllQnas();
			request.setAttribute("qnaList", qnaList);
		} else if (action.equals("/qnaDetail")) {
			nextPage = "/admin/qna/qnaDetail.jsp";
			int qseq = Integer.parseInt(request.getParameter("qseq"));
			
			QnaVO qnaVO = adminQnaService.getQna(qseq);
			request.setAttribute("qnaVO", qnaVO);
		} else if (action.equals("/qnaRepsave")) {
			nextPage = "/admin/qnas/qnaList";
			int qseq = Integer.parseInt(request.getParameter("qseq"));
			String reply = request.getParameter("reply");
			
			QnaVO qnaVO = new QnaVO();
			qnaVO.setQseq(qseq);
			qnaVO.setReply(reply);
			adminQnaService.resaveQna(qnaVO);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
