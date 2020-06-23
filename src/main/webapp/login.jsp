<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
    String context = request.getContextPath();
    request.setAttribute("context",context);
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用戶登入</title>
</head>
<body>
<c:if test="${!empty errorMsg}">
  <div style="color:red">${errorMsg}</div>
</c:if>
<form action="<c:url value="/login/doLogin.html"/>" method="post">
<table border="1px">
	<tr>
		<td width="20%">用戶名稱</td>
		<td width="80%"><input type="text" name="userName"/></td>
	</tr>
	<tr>
		<td width="20%">密碼</td>
		<td width="80%"><input type="password" name="password"/></td>
	</tr>
	<tr>
		<td colspan="2">
		   <input type="submit" value="登入">
		   <input type="reset" value="清空">
		</td>
	</tr>
</table>
</form>
<a href="${context}/register.jsp">用戶註冊</a>
</body>
</html>