package com.team08.service.admin;

import java.util.ArrayList;

import com.team08.dao.admin.AdminMemberDAO;
import com.team08.dto.MemberVO;

public class AdminMemberService {

	private AdminMemberDAO adminMemberDAO;
	
	public AdminMemberService() {
		adminMemberDAO = new AdminMemberDAO();
	}
	
	public int workerLogin(String id , String pwd) {
		return adminMemberDAO.workerCheck(id, pwd);
	}
	
	public ArrayList<MemberVO> selectMembers(String memberName) {
		return adminMemberDAO.selectMembers(memberName);
	}
}
