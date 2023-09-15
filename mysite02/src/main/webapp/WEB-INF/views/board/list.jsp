<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${param.kwd}"> 
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list}" var="vo" varStatus="status">
						<tr>
							<td>${page.boardStartNoPerPage - status.index}</td>
							<td style="padding-left:${(vo.depth - 1) * 30 }px">
							<c:if test="${vo.depth > 1}">
								<img src="${pageContext.request.contextPath}/assets/images/reply.png">
							</c:if>
							<c:choose>
								<c:when test="${vo.state eq 'active'}">
									<a href="${pageContext.request.contextPath}/board?a=view&board=${vo.no}&writer=${vo.userNo}&user=${no}">${vo.title}</a>
								</c:when>
								<c:otherwise>
									<a href="">삭제된 글 입니다.</a>
								</c:otherwise>
							</c:choose>
							</td>
							<td>${vo.userName}</td>
							<td>${vo.hit}</td>
							<td>${vo.regDate}</td>
							<td>
							<c:if test="${no == vo.userNo and vo.state eq 'active'}">
								<a href="${pageContext.request.contextPath}/board?a=delete&board=${vo.no}" class="del">삭제</a>
							</c:if>
							</td>
						</tr>		
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${page.beforePage > 0}">
							<li><a href="${pageContext.request.contextPath}/board?a=board&page=${page.beforePage}&kwd=${param.kwd}">◀</a></li>
						</c:if>
						<c:forEach begin="${page.startPage}" end="${page.endPage}" step="1" var="i">
							<li><a href="${pageContext.request.contextPath}/board?a=board&page=${i}&kwd=${param.kwd}">${i}</a></li>
						</c:forEach>
						<c:if test="${page.nextPage > 0}">
							<li><a href="${pageContext.request.contextPath}/board?a=board&page=${page.nextPage}&kwd=${param.kwd}">▶</a></li>
						</c:if>
					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<c:if test="${isAuth eq true}">
						<a href="${pageContext.request.contextPath}/board?a=writeform&user=${no}" id="new-book">글쓰기</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>