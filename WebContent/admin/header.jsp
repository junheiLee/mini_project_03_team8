<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freeflux Admin</title>
<link rel="stylesheet" href="${contextPath }/admin/css/admin.css">
<script type="text/javascript" src="${contextPath }/admin/product/product.js"></script>
<script>
	var contextPath = "${pageContext.request.contextPath }";
</script>
<c:choose>
	<c:when test="${empty workerId}">
		<script type="text/javascript">
			location.href = contextPath + '/admin/members/loginForm.do';
		</script>
	</c:when>
</c:choose>
</head>
<body onload="go_ab()">
	<div id="wrap">
		<header>
			<div id="logo">
				<a href="${contextPath }/admin/members/loginForm.do"> <img
					style="width: 800px" src="${contextPath }/admin/images/bar_01.gif"> <img
					src="${contextPath }/admin/images/text.gif">
				</a>
			</div>
			<input class="btn" type="button" value="logout" style="float: right;"
				onClick="location.href='${contextPath }/admin/members/logout.do'">
		</header>
		<div class="clear"></div>
