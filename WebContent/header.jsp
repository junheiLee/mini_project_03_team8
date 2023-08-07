<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nonage Shop</title>
<link href="${contextPath }/css/shopping.css" rel="stylesheet">
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="${contextPath }/member/member.js"></script>
<script type="text/javascript" src="${contextPath }/mypage/mypage.js"></script>
</head>
<script>
	var contextPath = "${pageContext.request.contextPath }";
</script>
<body>
	<div id="wrap">
		<!--헤더파일 들어가는 곳 시작 -->
		<header>
			<!--로고 들어가는 곳 시작--->
			<div id="logo">
				<a href="${contextPath }/main"> <img
					src="${contextPath }/images/logo.gif" width="180" height="100" alt="nonageshop">
				</a>
			</div>
			<!--로고 들어가는 곳 끝-->
			<nav id="catagory_menu">
				<ul>
					<c:choose>
						<c:when test="${empty sessionScope.loginUser}">
							<li><a href="${contextPath }/members/loginForm.do"
								style="width: 110px;">LOGIN(CUSTOMER</a> <a
								href="${contextPath }/admin/members/loginForm.do"
								style="width: 100px;">| ADMIN)</a></li>
							<li>/</li>
							<li><a href="${contextPath }/members/contract.do">JOIN</a></li>
						</c:when>
						<c:otherwise>
							<li style="color: orange">
								${sessionScope.loginUser.name}(${sessionScope.loginUser.id})</li>
							<li><a href="${contextPath }/members/logout.do">LOGOUT</a></li>
						</c:otherwise>
					</c:choose>
					<li>/</li>
					<li><a href="NonageServlet?command=cart_list">CART</a></li>
					<li>/</li>
					<li><a href="NonageServlet?command=mypage">MY PAGE</a></li>
					<li>/</li>
					<li><a href="${contextPath }/qnas/qnaList">Q&amp;A(1:1)</a>
					</li>
				</ul>
			</nav>

			<nav id="top_menu">
				<ul>
					<li><a href="${contextPath}/items?kind=1">Heels</a>
					</li>
					<li><a href="${contextPath}/items?kind=2">Boots</a>
					</li>
					<li><a href="${contextPath}/items?kind=3">Sandals</a>
					</li>
					<li><a href="${contextPath}/items?kind=4">Sneakers</a>
					</li>
					<li><a href="${contextPath}/items?kind=5">On
							Sale</a></li>
				</ul>
			</nav>
			<div class="clear"></div>
			<hr>
		</header>
		<!--헤더파일 들어가는 곳 끝 -->
		