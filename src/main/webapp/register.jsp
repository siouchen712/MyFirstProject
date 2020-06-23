<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用戶註冊</title>
<script type="text/javascript">
	function mycheck() {
		if (document.all("password").value != document.all("again").value) {
			alert("兩次輸入的密碼不同，請確認")
			return false
		} else {
			return true
		}
	}
</script>
</head>
<body>
<form action="<c:url value="/register/doRegister.html" />" method="post" onsubmit="return mycheck()">
<c:if test="${!empty errorMsg}">
   <div style="color=red">${errorMsg}</div>
</c:if>
<table border="1px" width="60%">
	<tr>
		<td width="20%">用戶名稱</td>
		<td width="80%">
		<input type="text" name="userName"/>
		</td>
	</tr>
	<tr>
		<td width="20%">密碼</td>
		<td width="80%"><input type="password" name="password"/></td>
	</tr>
	<tr>
		<td width="20%">密碼確認</td>
		<td width="80%"><input type="password" name="again"></td>
	</tr>
	<tr>
		<td colspan="2">
		   <input type="submit" value="保存">
		   <input type="reset" value="重置">
		</td>
	</tr>
</table>
</form>
</body>
</html>