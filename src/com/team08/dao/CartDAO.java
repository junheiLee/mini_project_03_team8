package com.team08.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.team08.dto.CartVO;

public class CartDAO {

	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public CartDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("CartDAO() ERR : " + e.getMessage());
		}
	}
	
	public ArrayList<CartVO> selectCarts(String userId) {
		ArrayList<CartVO> cartList = new ArrayList<CartVO>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM cart_view WHERE id = ? ORDER BY cseq DESC";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CartVO cartVO = new CartVO();
				cartVO.setCseq(rs.getInt(1));
				cartVO.setId(rs.getString(2));
				cartVO.setPseq(rs.getInt(3));
				cartVO.setMname(rs.getString(4));
				cartVO.setPname(rs.getString(5));
				cartVO.setQuantity(rs.getInt(6));
				cartVO.setIndate(rs.getTimestamp(7));
				cartVO.setPrice2(rs.getInt(8));
				cartList.add(cartVO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("selectCarts() ERR : " + e.getMessage());
		}
		return cartList;
	}
	
	public void insertCart(CartVO cartVO) {
		try {
			conn = dataFactory.getConnection();
			String query = "INSERT INTO cart (cseq, id, pseq, quantity) VALUES (cart_seq.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cartVO.getId());
			pstmt.setInt(2, cartVO.getPseq());
			pstmt.setInt(3, cartVO.getQuantity());
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("insertCart() ERR : " + e.getMessage());
		}
	}
	
	public void deleteCart(int cseq) {
		try {
			conn = dataFactory.getConnection();
			String query = "DELETE FROM cart WHERE cseq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cseq);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("deleteCart() ERR : " + e.getMessage());
		}
	}
}
