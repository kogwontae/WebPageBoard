<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>

<!-- サイドのナビゲーションバー部分のJSP -->
<html>
<head>
 <style>
   .no-underline{
      text-decoration:none;
   }
 </style>
  <meta charset="UTF-8">
  <title>사이드 메뉴</title>
</head>
<body>
	<h1>사이드 메뉴</h1>
	<!-- 		Sessionがログアウトの状態の場合、会員と掲示板管理ボタンを押すとログインフォームに移動 -->
<!-- 	ログインの状態の場合、テキスト通りのページに移動する。 -->
	<c:choose>
		<c:when test="${isLogOn == true  && member!= null}">
			<h1>
				<a href="${context}./member/listMembers.do"  class="listMembers">회원관리</a><br>
			    <a href="#"  class="no-underline">게시판관리</a><br>
		    </h1>
		 </c:when>
		 <c:otherwise>
		 	<h1>
				<a href="${context}./member/loginForm.do"  class="tologin1">회원관리</a><br>
			    <a href="${context}./member/loginForm.do"  class="tologin2">게시판관리</a><br>
		    </h1>			
		 </c:otherwise>
	</c:choose>	 
	<%-- 
	<h1>
		<a href="${contextPath}/member/listMembers.do"  class="no-underline">회원관리</a><br>
		<a href="${contextPath}/board/listArticles.do"  class="no-underline">게시판관리</a><br>
		<a href="#"  class="no-underline">상품관리</a><br>
	</h1>
	 --%>
</body>
</html>