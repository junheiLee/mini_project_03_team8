package com.team08.service.admin;

import java.util.ArrayList;

import com.team08.dao.QnaDAO;
import com.team08.dao.admin.AdminQnaDAO;
import com.team08.dto.QnaVO;

public class AdminQnaService {

	private QnaDAO qnaDAO;
	private AdminQnaDAO adminQnaDAO;
	
	public AdminQnaService() {
		qnaDAO = new QnaDAO();
		adminQnaDAO = new AdminQnaDAO();
	}
	
	public ArrayList<QnaVO> selectAllQnas() {
		return adminQnaDAO.selectAllQnas();
	}
	
	public QnaVO getQna(int qseq) {
		return qnaDAO.getQna(qseq);
	}
	
	public void resaveQna(QnaVO qnaVO) {
		adminQnaDAO.resaveQna(qnaVO);
	}
}
