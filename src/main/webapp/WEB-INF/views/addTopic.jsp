  
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>發表新話題</title>
	<script>
	   function mySubmit(){
	      with(document){
	         var topicTitle = getElementById("topicTitle");
	         if(topicTitle.value == null || topicTitle.value.length ==0){
	            alert("標題不可為空值");
	            topicTitle.focus();
	            return false;
	         }else if(topicTitle.value.length > 100){
	            alert("標題不可大於100個字");
	            boardName.focus();
	            return false;
	         }
	          
	         var postText = getElementById("mainPost.postText");
	         if(postText.value == null || postText.value.length < 10 ){
	            alert("標題內容必須大於10個字");
	            postText.focus();
	            return false;
	         }
	           
	         return true;
	      }
	      
	   }
	</script>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<form action="<c:url value="/board/addTopic.html" />" method="post" onsubmit="return mySubmit()">
<table border="1px" width="100%">
	<tr>
		<td width="20%">標題</td>
		<td width="80%">
		<input style="width:80%;"  name="topicTitle" value="${topic.topicTitle}">
	</tr>
	<tr>
		<td width="20%">內容</td>
		<td width="80%">
		<textarea style="width:100%;height:400px"  name="mainPost.postText">${topic.mainPost.postText}</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="right">
		   <input type="submit" value="確定">
		   <input type="reset" value="清空">
		   <input type="hidden" name="boardId" value="${boardId}"> 
		</td>
	</tr>
</table>
</form>
</body>
</html>