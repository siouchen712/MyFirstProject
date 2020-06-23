<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加論壇版塊</title>
	<script>
	   function mySubmit(){
	      with(document){
	         var boardName = getElementById("board.boardName");
	         if(boardName.value == null || boardName.value.length ==0){
	            alert("版塊名稱不能為空");
	            boardName.focus();
	            return false;
	         }else if(boardName.value.length > 150){
	            alert("版塊名稱長度不可超過50個字");
	            boardName.focus();
	            return false;
	         }
	          
	         var boardDesc = getElementById("board.boardDesc");
	         if(boardDesc.value != null && boardDesc.value.length > 255){
	            alert("版塊描述不可超過255個字");
	            boardDesc.focus();
	            return false;
	         }
	           
	         return true;
	      }
	      
	   }
	</script>
</head>
<body>
<%@ include file="includeTop.jsp" %>
<form action="<c:url value="/forum/addBoard.html"/>" method="post" onsubmit="return mySubmit()">
<table border="1px"  width="100%">
	<tr>
		<td width="20%">版塊名稱</td>
		<td width="80%"><input  type="text" name="boardName" style="width:60%;"/></td>
	</tr>
	<tr>
		<td width="20%">版塊簡介</td>
		<td width="80%">
		    <textarea style="width:80%;height:120px"  name="boardDesc"></textarea>
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