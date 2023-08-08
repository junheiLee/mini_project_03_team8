package com.team08.service.admin;

import java.util.ArrayList;

import com.team08.dao.admin.AdminOrderDAO;
import com.team08.dto.OrderVO;

public class AdminOrderService {

	private AdminOrderDAO adminOrderDAO;
	
	public AdminOrderService() {
		adminOrderDAO = new AdminOrderDAO();
	}
	
	public ArrayList<OrderVO> listOrder(String memberName) {
		return adminOrderDAO.listOrder(memberName);
	}
	
	public void updateOrderResult(String oseq) {
		adminOrderDAO.updateOrderResult(oseq);
	}
}
