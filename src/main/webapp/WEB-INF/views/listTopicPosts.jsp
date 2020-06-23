<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="baobaotao" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${topic.topicTitle}</title>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<table border="1px" width="100%">
	<c:forEach var="post" items="${pagedPost.result}">
		<tr>
			<td colspan="2">${post.postTitle}</td>
		</tr>
		<tr>
			<td width="20%">
			用戶：${post.user.userName}<br>
			積分：${post.user.credit}<br>
			時間：<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${post.createTime}"/></td>
			<td>${post.postText}</td>
		</tr>
	</c:forEach>
</table>
<baobaotao:PageBar 
	  pageUrl="/board/listTopicPosts-${boardForm.topicId}.html"
	  pageAttrKey="pagedPost"/>

	<script>
	   function mySubmit(){
	      with(document){
	         var postTitle = getElementById("post.postTitle");
	         if(postTitle.value != null && postTitle.value.length > 50){
	            alert("帖子標題最大長度可能超過50個字，請調整");
	            postTitle.focus();
	            return false;
	         }
	          
	         var postText = getElementById("post.postText");
	         if(postText.value == null || postText.value.length < 10){
	            alert("回覆帖子內容不能小於10個字");
	            postText.focus();
	            return false;
	         }
	           
	         return true;
	      }
	      
	   }
	</script>			   
<form action="<c:url value="/board/addPost.html"/>" method="post" onsubmit="return mySubmit()">
<table border="1px" width="100%">
	<tr>
		<td width="20%">標題</td>
		<td width="80%"><input type="text" name="postTitle" style="width:100%" /></td>
	</tr>
	<tr>
		<td width="20%">內容</td>
		<td width="80%"><textarea style="width:100%;height:100px"  name="postText"></textarea></td>
	</tr>
	<tr>
		<td colspan="2" align="right">
		   <input type="submit" value="確定">
		   <input type="reset" value="清空">
		   <input type="hidden" name="boardId" value="${topic.boardId}"/>
		   <input type="hidden" name="topic.topicId" value="${topic.topicId}"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>