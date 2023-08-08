package com.team08.controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team08.dto.OrderVO;
import com.team08.service.admin.AdminOrderService;

/**
 * Servlet implementation class AdminOrderController
 */
@WebServlet("/admin/orders/*")
public class AdminOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminOrderService adminOrderService;
       
    public AdminOrderController() {
    	adminOrderService = new AdminOrderService();
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
		
		if (action.equals("/orderList")) {
			nextPage = "/admin/order/orderList.jsp";
			String key = "";
			if (request.getParameter("key") != null) {
				key = request.getParameter("key");
			}
			ArrayList<OrderVO> orderList = adminOrderService.listOrder(key);
			request.setAttribute("orderList", orderList);
		} else if (action.equals("/orderSave")) {
			nextPage = "/admin/orders/orderList";
			String[] resultArr = request.getParameterValues("result");
			for (String oseq : resultArr) {
				adminOrderService.updateOrderResult(oseq);
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
