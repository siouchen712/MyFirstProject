<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="baobaotao" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>論壇版塊頁面</title>
		<script>
		   
		</script>
	</head>
	<body>
		<%@ include file="includeTop.jsp"%>
	<div>
		<table border="1px" width="100%">
			<c:set var="isboardManager" value="${false}" />
			<c:forEach items="${USER_CONTEXT.manBoards}" var="manBoard">
				<c:if test="${manBoard.boardId == board.boardId}">
					<c:set var="isboardManager" value="${true}" />
				</c:if>
			</c:forEach> 
			<tr>
			   <c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
			     <td></td>
			   </c:if>
				<td bgcolor="#EEEEEE">
					${board.boardName}
				</td>
				<td colspan="4" bgcolor="#EEEEEE" align="right">
				   <c:if test="${USER_CONTEXT != null}">
						<a href="<c:url value="/board/addTopicPage-${board.boardId}.html"/>">發表新話題</a>
				   </c:if>	
				</td>

			</tr>
			<tr>
			   <c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
			     <td>
			        <script>
			            function switchSelectBox(){
			                var selectBoxs = document.all("topicIds");
			                if(!selectBoxs) return ;
			                if(typeof(selectBoxs.length) == "undefined"){//only one checkbox
			                    selectBoxs.checked = event.srcElement.checked;
			                }else{//many checkbox ,so is a array 
			                  for(var i = 0 ; i < selectBoxs.length ; i++){
			                     selectBoxs[i].checked = event.srcElement.checked;
			                  }
			                } 
			            }
			        </script>
			        <input type="checkbox" onclick="switchSelectBox()"/>
			     </td>
			   </c:if>
				<td width="50%">
					標題
				</td>
				<td width="10%">
					發表人
				</td>
				<td width="10%">
					回覆數
				</td>
				<td width="15%">
					發表時間
				</td>
				<td width="15%">
					最後回覆時間
				</td>
			</tr>
			<c:forEach var="topic" items="${pagedTopic.result}">
				<tr>
				    <c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
			          <td>
			          	<input type="checkbox" name="topicIds" value="${topic.topicId}"/>
			          </td>
			        </c:if>
		        	<td>
						<a  href="<c:url value="/board/listTopicPosts-${topic.topicId}.html"/>">
							<c:if test="${topic.digest > 0}">
							  <font color=red>★</font>
							</c:if>
							${topic.topicTitle} 
						</a>
					</td>
					<td>
						${topic.user.userName}
						<br>
						<br>
					</td>
					<td>
						${topic.replies}
						<br>
						<br>
					</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
							value="${topic.createTime}" />
					</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
							value="${topic.lastPost}" />
					</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<baobaotao:PageBar
			   pageUrl="/board/listBoardTopics-${board.boardId}.html"
			   pageAttrKey="pagedTopic"/>
	    <c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
	         <script>
	            function getSelectedTopicIds(){
	                var selectBoxs = document.all("topicIds");
	                if(!selectBoxs) return null;
	                if(typeof(selectBoxs.length) == "undefined" && selectBoxs.checked){   
	                    return selectBoxs.value;
	                }else{//many checkbox ,so is a array 
	                  var ids = "";
	                  var split = ""
	                  for(var i = 0 ; i < selectBoxs.length ; i++){
	                     if(selectBoxs[i].checked){
	                        ids += split+selectBoxs[i].value;
	                        split = ",";
	                     }
	                  }
	                  return ids;
	                }
	            }
	            function deleteTopics(){
	               var ids = getSelectedTopicIds();
	               if(ids){
	                  var url = "<c:url value="/board/removeTopic.html"/>?topicIds="+ids+"&boardId=${board.boardId}";
	                  //alert(url);
	                  location.href = url;
	               }
	            }
	            function setDefinedTopis(){
	               var ids = getSelectedTopicIds();
	               if(ids){
	                  var url = "<c:url value="/board/makeDigestTopic.html"/>?topicIds="+ids+"&boardId=${board.boardId}";
	                  location.href = url;
	               }	            
	            }
	         </script>
			<input type="button" value="刪除" onclick="deleteTopics()">
			<input type="button" value="置精華帖"  onclick="setDefinedTopis()">
		</c:if>
		
	</body>
</html>