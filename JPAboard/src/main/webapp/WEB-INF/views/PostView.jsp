<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="kr.ac.kopo.kopo12.domain.*"%>
<%@ page import="kr.ac.kopo.kopo12.dao.*"%>
<%@ page import="kr.ac.kopo.kopo12.service.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<link href="/JPAboard/resources/board.css?after" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div id="navigation">
		<a href="PostTable?id=${boardId}&from=1" id="navi"><h1>${name}</h1></a>
	</div>
	<form method="post" action="CommentInsert?id=${id}&boardId=${boardId}">
		<table>
			<tr>
				<td class="option">번호</td>
				<td class="content">${number}</td>
			</tr>
			<tr>
				<td class="option">제목</td>
				<td class="content">${boardItem.getTitle()}</td>
			</tr>
			<tr>
				<td class="option">일자</td>
				<td class="content">${boardItem.getDate()}</td>
			</tr>
			<tr>
				<td class="option">내용</td>
				<td class="content">${boardItem.getContent()}</td>
			</tr>
		</table>
		<div>
			<div style="width: 960px; display: inline-block;"></div>
			<input type="button" class="list"
				onclick="location.href='PostTable?id=${boardId}&from=1'" value="목록">
			<input type="button" class="update"
				onclick="location.href='PostUpdate?id=${id}&boardId=${boardId}'"
				value="수정">
		</div>
		<br> <br>
		<div>
			<div style="width: 595px; display: inline-block;"></div>
			<b>댓글</b>
			<div>
				<div>
					<textarea name="comment" id="cmt" rows="4" placeholder="댓글을 입력하세요"></textarea>
					<br>
					<div style="width: 1105px; display: inline-block;"></div>
					<input type="submit" class="addComment" value="댓글달기">
				</div>
	</form>
	<br>
	<table>
		<tr>
			<td class="number" style="text-align: center;"><b>번호</b></td>
			<td class="plusContent" style="text-align: center;"><b>댓글내용</b></td>
			<td class="Date"><b>날짜</b></td>
		</tr>
		<c:forEach var="comment" items="${showComments}">
			<c:set var="a" value="${a+1}"></c:set>
			<tr>
				<td class=number>${a}</td>
		 		<td class=plusContent>${comment.getContent()}</td>
				<td class=date>${comment.getDate()}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>