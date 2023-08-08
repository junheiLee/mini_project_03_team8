package com.team08.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.team08.dto.OrderVO;

public class AdminOrderDAO {

	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public AdminOrderDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("AdminOrderDAO() ERR : " + e.getMessage());
		}
	}
	
	public ArrayList<OrderVO> listOrder(String memberName) {
		ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM order_view WHERE mname LIKE '%'||?||'%' ORDER BY result, oseq DESC";
			pstmt = conn.prepareStatement(query);
			if (memberName == "") {
				pstmt.setString(1, "%");
			} else {
				pstmt.setString(1, memberName);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderVO orderVO = new OrderVO();
				orderVO.setOdseq(rs.getInt("odseq"));
				orderVO.setOseq(rs.getInt("oseq"));
				orderVO.setId(rs.getString("id"));
				orderVO.setPseq(rs.getInt("pseq"));
				orderVO.setMname(rs.getString("mname"));
				orderVO.setPname(rs.getString("pname"));
				orderVO.setQuantity(rs.getInt("quantity"));
				orderVO.setZipNum(rs.getString("zip_num"));
				orderVO.setAddress(rs.getString("address"));
				orderVO.setPhone(rs.getString("phone"));
				orderVO.setIndate(rs.getTimestamp("indate"));
				orderVO.setPrice2(rs.getInt("price2"));
				orderVO.setResult(rs.getString("result"));
				orderList.add(orderVO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("listOrder() ERR : " + e.getMessage());
		}
		return orderList;
	}
	
	public void updateOrderResult(String oseq) {
		try {
			conn = dataFactory.getConnection();
			String query = "UPDATE order_detail SET result = '2' WHERE odseq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, oseq);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("updateOrderResult() ERR : " + e.getMessage());
		}
	}
}
