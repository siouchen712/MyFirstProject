<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="pageUrl" required="true" rtexprvalue="true" description="分頁對應 URL" %>
<%@ attribute name="pageAttrKey" required="true" rtexprvalue="true" description="Page在Request屬性鍵值"%>
<c:set var="pageUrl" value="${pageUrl}" />
<%
   String separator = pageUrl.indexOf("?") > -1?"&":"?";
   jspContext.setAttribute("pageResult", request.getAttribute(pageAttrKey));
   jspContext.setAttribute("pageUrl", pageUrl);
   jspContext.setAttribute("separator", separator);
%>
<div style="font:12px;background-color:#DDDDDD">
	共${pageResult.totalPageCount}頁，第${pageResult.currentPageNo}頁
	<c:if test="${pageResult.currentPageNo <=1}">
	     首頁&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.currentPageNo >1 }">
	   <a href="<c:url value="${pageUrl}"/>${separator}pageNo=1">首頁</a>&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.hasPreviousPage}">
	  <a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.currentPageNo -1 }">上一頁</a>&nbsp;&nbsp;
	</c:if>
	<c:if test="${!pageResult.hasPreviousPage}">
	  上一頁&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.hasNextPage}">
	  <a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.currentPageNo +1 }">下一頁</a>&nbsp;&nbsp;
	</c:if>
	<c:if test="${!pageResult.hasNextPage}">
	  下一頁&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.currentPageNo >= pageResult.totalPageCount}">
	   末頁&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.currentPageNo < pageResult.totalPageCount}">
	   <a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.totalPageCount }">末頁</a>&nbsp;&nbsp;
	</c:if>
</div>