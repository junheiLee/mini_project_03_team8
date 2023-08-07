package com.team08.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.team08.dto.QnaVO;

public class QnaDAO {

	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	public QnaDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("MemberDAO() ERR : " + e.getMessage());
		}
	}
	
	public ArrayList<QnaVO> selectQnas(String id) {
		ArrayList<QnaVO> qnaList = new ArrayList<QnaVO>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM qna WHERE id = ? ORDER BY qseq DESC";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
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
			System.out.println("selectQnas() ERR : " + e.getMessage());
		}
		return qnaList;
	}
	
	public QnaVO getQna(int qseq) {
		QnaVO qnaVO = null;
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT * FROM qna WHERE qseq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, qseq);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				qnaVO = new QnaVO();
				qnaVO.setQseq(qseq);
				qnaVO.setSubject(rs.getString("subject"));
				qnaVO.setContent(rs.getString("content"));
				qnaVO.setReply(rs.getString("reply"));
				qnaVO.setId(rs.getString("id"));
				qnaVO.setRep(rs.getString("rep"));
				qnaVO.setIndate(rs.getTimestamp("indate"));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("getQna() ERR : " + e.getMessage());
		}
		return qnaVO;
	}
	
	public void insertQna(QnaVO qnaVO, String sessionId) {
		try {
			conn = dataFactory.getConnection();
			String query = "INSERT INTO qna (qseq, subject, content, id) VALUES (qna_seq.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, qnaVO.getSubject());
			pstmt.setString(2, qnaVO.getContent());
			pstmt.setString(3, sessionId);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("insertQna() ERR : " + e.getMessage());
		}
	}
}
