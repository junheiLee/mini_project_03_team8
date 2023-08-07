package com.team08.service;

import java.util.ArrayList;

import com.team08.dao.QnaDAO;
import com.team08.dto.QnaVO;

public class QnaService {

	private QnaDAO qnaDAO;

	public QnaService() {
		qnaDAO = new QnaDAO();
	}
	
	public ArrayList<QnaVO> selectQnas(String id) {
		return qnaDAO.selectQnas(id);
	}

	public QnaVO getQna(int qseq) {
		return qnaDAO.getQna(qseq);
	}
	
	public void insertQna(QnaVO qnaVO, String sessionId) {
		qnaDAO.insertQna(qnaVO, sessionId);
	}
}
