<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用戶鎖定/解鎖</title>
</head>
<body>
<%@ include file="includeTop.jsp" %>
alert(${context})
<form action="<c:url value="/forum/userLockManage.html"/>"  method="post" >
<table border="1px" width="100%">
	<tr>
		<td width="20%">用戶</td>
		<td width="80%"><select name="userName">
		     <option>請選擇</option>
		     <c:forEach var="user" items="${users}">
		       <option value="${user.userName}">${user.userName}</option>
		     </c:forEach>
		</select></td>
	</tr>
	<tr>
		<td width="20%">鎖定/解鎖</td>
		<td width="80%">
		   <input type="radio" name="locked" value="1" />鎖定 
		   <input type="radio" name="locked" value="0" />解鎖
		</td>
	</tr>
	<tr>
		<td colspan="2">
		   <input type="submit" value="確定">
		   <input type="reset" value="清空">
		</td>
	</tr>
</table>
</form>
</body>
</html>