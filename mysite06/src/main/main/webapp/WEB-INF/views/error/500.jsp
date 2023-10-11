<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>500 ERROR</title>
</head>
<body>
	<img src="${pageContext.request.contextPath}/assets/images/oops2.gif" width="70%"/>
	<h5>500:Error - Internal Server Error (Oooooooops!!)</h5>
	<p>
		죄송합니다. 오류가 발생했습니다.
		잠시후, 다시 시도해 주세요.
	</p>
</body>
</html>