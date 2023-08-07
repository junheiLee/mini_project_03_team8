package com.team08.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.team08.dto.QnaVO;

public class AdminQnaDAO {

	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public AdminQnaDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("AdminQnaDAO() ERR : " + e.getMessage());
		}
	}
	
	public ArrayList<QnaVO> selectAllQnas() {
		ArrayList<QnaVO> qnaList = new ArrayList<QnaVO>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM qna ORDER BY indate DESC";
			pstmt = conn.prepareStatement(query);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				QnaVO qnaVO = new QnaVO();
				qnaVO.setQseq(rs.getInt("qseq"));
				qnaVO.setSubject(rs.getString("subject"));
				qnaVO.setContent(rs.getString("content"));
				qnaVO.setReply(rs.getString("reply"));
				qnaVO.setId(rs.getString("id"));
				qnaVO.setRep(rs.getString("rep"));
				qnaVO.setIndate(rs.getTimestamp("indate"));
				qnaList.add(qnaVO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("selectAllQnas() ERR : " + e.getMessage());
		}
		return qnaList;
	}
	
	public void resaveQna(QnaVO qnaVO) {
		try {
			conn = dataFactory.getConnection();
			String query = "UPDATE qna SET reply = ?, rep = '2' WHERE qseq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, qnaVO.getReply());
			pstmt.setInt(2, qnaVO.getQseq());
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("resaveQna() ERR : " + e.getMessage());
		}
	}
}
