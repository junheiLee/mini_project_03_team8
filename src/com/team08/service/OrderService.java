package com.team08.service;

import java.util.ArrayList;

import com.team08.dao.OrderDAO;
import com.team08.dto.CartVO;
import com.team08.dto.OrderVO;

public class OrderService {

	private OrderDAO orderDAO;
	
	public OrderService() {
		orderDAO = new OrderDAO();
	}
	
	public ArrayList<Integer> selectSeqOrderIng(String id) {
		return orderDAO.selectSeqOrderIng(id);
	}
	
	public ArrayList<OrderVO> listOrderById(String id, String result, int oseq) {
		return orderDAO.listOrderById(id, result, oseq);
	}
	
	public int insertOrder(ArrayList<CartVO> cartList, String id) {
		return orderDAO.insertOrder(cartList, id);
	}
}
