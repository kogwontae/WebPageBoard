<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%-- 
<c:set var="article"  value="${articleMap.article}"  />
<c:set var="imageFileList"  value="${articleMap.imageFileList}"  />

 --%>
<%
request.setCharacterEncoding("UTF-8");
%>

<head>
<meta charset="UTF-8">
<title>글보기</title>
<style>
#tr_btn_modify {
	display: none;
}
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
///board/listArticlesに戻るメッソード
     function backToList(obj){
	    obj.action="${contextPath}/board/listArticles.do";
	    obj.submit();
     }
//  「修正する」ボタンを押したとき、文のタイトルと内容を修正できるようにし、必要なボタンを活性化
	 function fn_enable(obj){
		 document.getElementById("i_title").disabled=false;
		 document.getElementById("i_content").disabled=false;
		 document.getElementById("tr_btn_modify").style.display="inline";
		 document.getElementById("tr_btn").style.display="none";
	 }
	 
// 	 修正した文をDBに設定
	 function fn_modify_article(obj){
		 obj.action="${contextPath}/board/modArticle.do";
		 obj.submit();
	 }
	 
// 	 投稿文を削除
	 function fn_remove_article(url,articleNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
		 
	     form.appendChild(articleNOInput);
	     document.body.appendChild(form);
	     form.submit();
	 }
	 
// 	 コメントの内容の有無チェック
	 function fn_reply(){
		 var frmReply = document.frmReply;
		 
		 if(frmReply.repl.value.length == 0 || frmReply.repl.value == ""){
			 alert("내용을 입력해주세요.");
		 }else{
			 frmReply.submit();
		 }
	 }
	 
// 	 コメントの削除
	 function fn_remove_reply(url,replyNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","replyNO");
	     articleNOInput.setAttribute("value", replyNO);
		 
	     form.appendChild(articleNOInput);
	     document.body.appendChild(form);
	     form.submit();
	 }
 </script>
</head>
<body>
	<form name="frmArticle" method="post" action="${contextPath}" enctype="multipart/form-data">
		<table border=0 align="center">
			<tr>
				<td width=150 align="center" bgcolor=#FF9933>글번호</td>
				<td><input type="text" value="${article.articleNO }" disabled />
					<input type="hidden" name="articleNO" value="${article.articleNO}" />
				</td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">작성자 아이디</td>
				<td><input type=text value="${article.id }" name="writer" disabled /></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">제목</td>
				<td><input type=text value="${article.title }" name="title" id="i_title" disabled /></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">내용</td>
				<td><textarea rows="20" cols="60" name="content" id="i_content" disabled />${article.content }</textarea></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">등록일자</td>
				<td><input type=text value="<fmt:formatDate value="${article.writeDate}" />" disabled />
				</td>
			</tr>
			<tr id="tr_btn_modify" align="center">
				<td colspan="2"><input type=button value="수정반영하기" onClick="fn_modify_article(frmArticle)"> 
				<input type=button value="취소" onClick="backToList(frmArticle)"></td>
			</tr>

			<tr id="tr_btn">
				<td colspan="2" align="center">
					<c:if test="${member.id == article.id }">
						<input type=button value="수정하기" onClick="fn_enable(this.form)">
						<input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO})">
					</c:if> 
					<input type=button value="리스트로 돌아가기" onClick="backToList(this.form)"> 
				</td>
			</tr>
		</table>
	</form>
	<br><br>
	<div class="re" style="padding-top: 10px">
		<form name="frmReply" method="get" action="${contextPath}/board/addReply.do">
			<table border=0 align="center">
			<tr>
				<td width="150" align="center">아이디:${member.id } </td>
				<td><input type="text" name="r_content" id="repl" maxlength='100'  style="width:300px;height:50px"></td>
				<td><input type="button" value="댓글달기" onClick="fn_reply()"></td>
			</tr>
			</table>	
			<input type="hidden" id="articleNO" name="articleNO" value=${article.articleNO}>
		</form>
	</div>
	<br><br>
	<hr width="80%"></hr>
	
<table align="center" width="80%"  >
	<c:choose>
  	<c:when test="${replyList == null }" >
	    <tr  height="10">
	      <td colspan="4"><p align="center"><b><span style="font-size:9pt;">댓글이 없습니다.</span></b></p></td>  
	    </tr>
  	</c:when>
  	<c:when test="${replyList !=null }" >
  		<tr align="center">
				<td width="10%">아이디</td>
				<td align='center'  width="35%">내용</a></td>
			  	<td width="10%">작성일자</td>
		</tr>
    	<c:forEach  var="reply" items="${replyList }" >
		    <tr align="center">
				<td width="10%">${reply.writerID }</td>
				<td align='center'  width="35%">${reply.r_content }</a></td>
			  	<td width="10%">${reply.r_date}</td> 
			  	<c:if test="${member.id == reply.writerID }">
			  		<td width="5%"><input type=button value="댓글삭제" onClick="fn_remove_reply('${contextPath}/board/removeReply.do', ${reply.replyNO})"></td> 
			  	</c:if>	
			</tr>
    	</c:forEach>
    </c:when>
    </c:choose>
</table>
	
</body>
</html>