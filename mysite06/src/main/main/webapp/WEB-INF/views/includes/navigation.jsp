<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="navigation">
<spring:eval expression="@site.isAdmin()" var="isAdmin"/>
	<ul>
		<li><a href="${pageContext.request.contextPath}">조용현</a></li>
		<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
		<li><a href="${pageContext.request.contextPath}/board">게시판</a></li>
		<li><a href="${pageContext.request.contextPath}/gallery">갤러리</a></li>
		<c:if test="${isAdmin eq true}">
			<li><a href="${pageContext.request.contextPath}/admin">관리자페이지</a></li>
		</c:if>
	</ul>
</div>