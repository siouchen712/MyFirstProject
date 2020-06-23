<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<a href="<c:url value="/index.jsp"/>">首頁</a>&nbsp;&nbsp;
<c:if test="${!empty USER_CONTEXT.userName}">
   ${USER_CONTEXT.userName}(${USER_CONTEXT.credit}),歡迎你的到來,<a href="<c:url value="/login/doLogout.html"/>">登出</a></c:if>
&nbsp;&nbsp;
<c:if test="${empty USER_CONTEXT.userName}">
   <a href="<c:url value="/login.jsp"/>">登入</a>&nbsp;&nbsp;
   <a href="<c:url value="/register.jsp"/>">註冊</a>
</c:if>
<c:if test="${USER_CONTEXT.userType == 2}">
   <a href="<c:url value="/forum/addBoardPage.html"/>">新建論壇版塊</a>&nbsp;&nbsp;
   <a href="<c:url value="/forum/setBoardManagerPage.html"/>">論壇版塊管理員</a>&nbsp;&nbsp;
   <a href="<c:url value="/forum/userLockManagePage.html"/>">用戶鎖定/解鎖</a>
</c:if>