<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header">
	<div style="float: top; height: 10%;">
		<p style="text-align: left; padding: 10px;"><a style="color:#fff" href="${pageContext.request.contextPath }">home</a></p>
	</div>
	<div style="float: top; height: 70%;">
		<h1 ><a style="color:#fff" href="${pageContext.request.contextPath }/jblog/${blogVo.id}">${blogVo.title}</a></h1>
	</div>
	<div style="float: bottom; height: 10%;">
		<ul>
			<c:choose>
				<c:when test="${empty authUser }">
					<li><a href="${pageContext.request.contextPath }/user/login">로그인</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a></li>
					<c:if test="${authUser.id == blogVo.id }">
						<li><a href="${pageContext.request.contextPath }/jblog/admin/${authUser.id}/blog">블로그 관리</a></li>
					</c:if>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>