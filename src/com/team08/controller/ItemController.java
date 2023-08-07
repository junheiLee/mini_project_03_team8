package com.team08.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team08.service.ItemService;

/**
 * 상품 상세페이지 및 목록 처리 컨트롤러
 * 
 * 상품 상세페이지 요청을 받으면 doGet method를 실행하여 상품 상세 페이지를 보여주고,
 * 카테고리를 선택하면 해당 카테고리의 상품 목록을 보여준다.
 * 
 * @author junheiLee
 */
@WebServlet("/items")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ItemService service = ItemService.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "";
		
		String pseq = request.getParameter("pseq");
		String kind = request.getParameter("kind");
		
		if(pseq != null) {
			url = "product/productDetail.jsp";
			request.setAttribute("productVO", service.getProduct(pseq));
	
		}
		if(kind != null) {
			url = "product/productKind.jsp";
			request.setAttribute("productKindList", service.listKindProduct(kind));
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}
}
