<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> 
<%
  request.setCharacterEncoding("UTF-8");
%> 

<head>
<meta charset="UTF-8">
<title>글쓰기창</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
  function backToList(obj){
    obj.action="${contextPath}/board/listArticles.do";
    obj.submit();
  }
</script>
 <title>글쓰기창</title>
</head>
<body>
<h1 style="text-align:center">글쓰기</h1>
  <form name="articleForm" method="post"   action="${contextPath}/board/addNewArticle.do" >
    <table border="0" align="center">
      		<tr>
				<td align="right"> 작성자</td>
				<td colspan=2  align="left"><input type="text" size="20" maxlength="100"  value="${member.name }" readonly/> </td>
			</tr>
	     	<tr>
			    <td align="right">글제목: </td>
			    <td colspan="2"><input type="text" size="67"  maxlength="500" name="title" /></td>
			</tr>
	 		<tr>
				<td align="right" valign="top"><br>글내용: </td>
				<td colspan=2><textarea name="content" rows="10" cols="65" maxlength="4000"></textarea> </td>
     		</tr>
	        	 <td align="right"> </td>
	      		 <td colspan="2">
			       <input type="submit" value="글쓰기" />
			       <input type=button value="목록보기"onClick="backToList(this.form)" />
	      		 </td>
     		</tr>
    </table>
    
    
  </form>
</body>
</html>
