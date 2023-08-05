<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<article>
	<center>
	
	<form name="findId">
	<table>
	<tr>
		<td align="right"><label> 이름</label></td>
		<td><input type="text" name="name" value=""></td>
	</tr>	
	<tr>
		<td align="right"><label> 이메일</label></td>
		<td><input type="text" name="email" value=""></td>
	</tr>
	<tr>
		<td align="center" colspan="2"><input type="button" value="아이디 찾기" onclick="findMemberId()"></td>
	</tr>
	<div id="foundIdContainer" style="display: none;">
    	<p>찾은 아이디: <span id="foundId"></span></p>
	</div>
	</table>
	</form>
	<p><p><p><p><p><p><p><p><p><p><p><p>
	
	
	<form name="findPW">
	<table>
	<tr>
		<td align="right"><label> 아이디</label></td>
		<td><input type="text" name="memberId" value=""></td>
	</tr>	
	<tr>
		<td align="right"><label> 이름</label></td>
		<td><input type="text" name="name" value=""></td>
	</tr>	
	<tr>
		<td align="right"><label> 이메일</label></td>
		<td><input type="text" name="email" value=""></td>
	</tr>
	<tr>
		<td align="center" colspan="2"><input type="button" value="비밀번호 찾기" onclick="findPassword()"></td>
	</tr>
	</table>
	</form>
	</center>
</article>
<%@ include file="../footer.jsp" %>  