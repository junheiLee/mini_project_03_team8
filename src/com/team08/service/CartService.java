package com.team08.service;

import java.util.ArrayList;

import com.team08.dao.CartDAO;
import com.team08.dto.CartVO;

public class CartService {

	private CartDAO cartDAO;
	
	public CartService() {
		cartDAO = new CartDAO();
	}
	
	public ArrayList<CartVO> selectCarts(String userId) {
		return cartDAO.selectCarts(userId);
	}
	
	public void insertCart(CartVO cartVO) {
		cartDAO.insertCart(cartVO);
	}
	
	public void deleteCart(int cseq) {
		cartDAO.deleteCart(cseq);
	}
	
	public CartVO getCart(int cseq) {
		CartVO cartVO = new CartVO();
        cartVO.setCseq(cseq);
        return cartVO;
	}
}
