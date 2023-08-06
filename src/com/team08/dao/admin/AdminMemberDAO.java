package com.team08.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.team08.dto.MemberVO;
import javax.sql.DataSource;

public class AdminMemberDAO {

	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public AdminMemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("AdminMemberDAO() ERR : " + e.getMessage());
		}
	}
	
	public int workerCheck(String userId, String userPw) {
		int result = -1;
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT pwd FROM worker WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = 0;
				String dbPwd = rs.getString(1);
				if (dbPwd.equals(userPw)) {
					result = 1;
				}
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("workerCheck() ERR : " + e.getMessage());
		}
		return result;
	}
	
	public ArrayList<MemberVO> selectMembers(String memberName) {
		ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM member WHERE name LIKE '%'||?||'%' ORDER BY indate DESC";
			pstmt = conn.prepareStatement(query);
			if (memberName.equals("")) {
				pstmt.setString(1, "%");
			} else {
				pstmt.setString(1, memberName);
			}
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberVO memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
				memberVO.setPwd(rs.getString("pwd"));
				memberVO.setName(rs.getString("name"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setZipNum(rs.getString("zip_num"));
				memberVO.setAddress(rs.getString("address"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setUseyn(rs.getString("useyn"));
				memberVO.setIndate(rs.getTimestamp("indate"));
				memberList.add(memberVO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("selectMembers() ERR : " + e.getMessage());
		}
		return memberList;
	}
}
