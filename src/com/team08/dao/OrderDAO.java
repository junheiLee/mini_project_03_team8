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
import com.team08.dto.OrderVO;

import jdk.jfr.DataAmount;

public class OrderDAO {

	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public OrderDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("OrderDAO() ERR : " + e.getMessage());
		}
	}
	
	public ArrayList<Integer> selectSeqOrderIng(String id) {
		ArrayList<Integer> oseqList = new ArrayList<Integer>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT DISTINCT oseq FROM order_view WHERE id = ? AND result = '1' ORDER BY oseq DESC";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				oseqList.add(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("selectSeqOrderIng() ERR : " + e.getMessage());
		}
		return oseqList;
	}
	
	public ArrayList<OrderVO> listOrderById(String id, String result, int oseq) {
		ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM order_view WHERE id = ? AND result LIKE '%'||?||'%' AND oseq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, result);
			pstmt.setInt(3, oseq);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderVO orderVO = new OrderVO();
				orderVO.setOdseq(rs.getInt("odseq"));
				orderVO.setOseq(rs.getInt("oseq"));
				orderVO.setId(rs.getString("id"));
				orderVO.setIndate(rs.getTimestamp("indate"));
				orderVO.setMname(rs.getString("mname"));
				orderVO.setZipNum(rs.getString("zip_num"));
				orderVO.setAddress(rs.getString("address"));
				orderVO.setPhone(rs.getString("phone"));
				orderVO.setPseq(rs.getInt("pseq"));
				orderVO.setQuantity(rs.getInt("quantity"));
				orderVO.setPname(rs.getString("pname"));
				orderVO.setPrice2(rs.getInt("price2"));
				orderVO.setResult(rs.getString("result"));
				orderList.add(orderVO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("listOrderById() ERR : " + e.getMessage());
		} 
		return orderList;
	}
	
	public int insertOrder(ArrayList<CartVO> cartList, String id) {
		int maxOseq = 0;
		try {
			conn = dataFactory.getConnection();
			String selectMaxOseq = "SELECT MAX(oseq) FROM orders";
			pstmt = conn.prepareStatement(selectMaxOseq);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				maxOseq = rs.getInt(1);
			}
			pstmt.close();

			String insertOrder = "INSERT INTO orders (oseq, id) VALUES (orders_seq.nextval, ?)";
			pstmt = conn.prepareStatement(insertOrder);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			for (CartVO cartVO : cartList) {
				insertOrderDetail(cartVO, maxOseq);
			}
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("insertOrder() ERR : " + e.getMessage());
		} 
		return maxOseq;
	}

	public void insertOrderDetail(CartVO cartVO, int maxOseq) {
		try {
			conn = dataFactory.getConnection();
			String insertOrderDetail = "INSERT INTO order_detail (odseq, oseq, pseq, quantity) "
					+ "VALUES (order_detail_seq.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(insertOrderDetail);
			pstmt.setInt(1, maxOseq);
			pstmt.setInt(2, cartVO.getPseq());
			pstmt.setInt(3, cartVO.getQuantity());
			pstmt.executeUpdate();
			pstmt.close();

			String updateCartResult = "UPDATE cart SET result = 2 where cseq = ?";
			pstmt = conn.prepareStatement(updateCartResult);
			pstmt.setInt(1, cartVO.getCseq());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
