package com.team08.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.team08.dto.AddressVO;
import com.team08.dto.MemberVO;

public class MemberDAO {
	
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("MemberDAO() ERR4 : " + e.getMessage());
		}
	}

	public MemberVO checkLogin(String id, String pwd) {
		MemberVO memberVO = null;
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM member WHERE id = ? AND pwd = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
				memberVO.setPwd(rs.getString("pwd"));
				memberVO.setName(rs.getString("name"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setZipNum(rs.getString("zip_num"));
				memberVO.setAddress(rs.getString("address"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setUseyn(rs.getString("useyn"));
				memberVO.setIndate(rs.getTimestamp("indate"));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("checkLogin() ERR : " + e.getMessage());
		}

		return memberVO;
	}
	
	public int insertMember(MemberVO memberVO) {
		int result = 0;
		try {
			conn = dataFactory.getConnection();
			String query = "INSERT INTO member (id, pwd, name, email, zip_num, address, phone) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberVO.getId());
			pstmt.setString(2, memberVO.getPwd());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getEmail());
			pstmt.setString(5, memberVO.getZipNum());
			pstmt.setString(6, memberVO.getAddress());
			pstmt.setString(7, memberVO.getPhone());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("insertMember() ERR : " + e.getMessage());
		}
		
		return result;
	}
	
	public int confirmID(String id) {
		int result = -1;
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM member WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = 1;
			}
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("confirmID() ERR : " + e.getMessage());
		}
		
		return result;
	}
	
	public ArrayList<AddressVO> selectAddressByDong(String dong) {
		ArrayList<AddressVO> addressList = new ArrayList<AddressVO>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM address WHERE dong LIKE '%'||?||'%'";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dong);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				AddressVO addressVO = new AddressVO();
				addressVO.setZipNum(rs.getString("zip_num"));
				addressVO.setSido(rs.getString("sido"));
				addressVO.setGugun(rs.getString("gugun"));
				addressVO.setDong(rs.getString("dong"));
				addressVO.setZipCode(rs.getString("zip_code"));
				addressVO.setBunji(rs.getString("bunji"));
				addressList.add(addressVO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("selectAddressByDong() ERR : " + e.getMessage());
		}
		
		return addressList;
	}
	
	public String findMemberId(String name, String email) {
		String id = "";
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT id FROM member WHERE name = ? AND email = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getString("id");
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("findMemberId() ERR : " + e.getMessage());
		}
		
		return id;
	}
	
	public String findMemberPassword(String id, String name, String email) {
		String password = "";
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT pwd FROM member WHERE id = ? AND name = ? AND email = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				password = rs.getString("pwd");
			}
		} catch (SQLException e) {
			System.out.println("findMemberPassword() ERR : " + e.getMessage());
		}
		
		return password;
	}
}
