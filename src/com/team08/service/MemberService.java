package com.team08.service;

import java.util.ArrayList;

import com.team08.dao.MemberDAO;
import com.team08.dto.AddressVO;
import com.team08.dto.MemberVO;

public class MemberService {

	private MemberDAO memberDAO;

	public MemberService() {
		memberDAO = new MemberDAO();
	}
	
	public MemberVO login(String id, String pwd) {
		return memberDAO.checkLogin(id, pwd);
	}
	
	public int join(MemberVO memberVO) {
		return memberDAO.insertMember(memberVO);
	}
	
	public int confirmID(String id) {
		return memberDAO.confirmID(id);
	}
	
	public ArrayList<AddressVO> selectAddressByDong(String dong) {
		return memberDAO.selectAddressByDong(dong);
	}
	
	public String findMemberId(String name, String email) {
		return memberDAO.findMemberId(name, email);
	}
	
	public String findMemberPassword(String id, String name, String email) {
		return memberDAO.findMemberPassword(id, name, email);
	}
}
