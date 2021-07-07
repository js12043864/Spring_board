<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.ac.kopo.kopo12.domain.*" %>
<%@ page import="kr.ac.kopo.kopo12.dao.*" %>
<%@ page import="kr.ac.kopo.kopo12.service.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*, java.text.*" %>
<html lang="en">
	<head>
	<link href="/JPAboard/resources/board.css?after" rel="stylesheet"
	type="text/css" />
	</head>
	<body>
		<div id="navigation"><a href="PostTable?id=${boardId}&from=1" id="navi"><h1>${name}</h1></a></div>
		<br>
		<form method="post" action="PostWrite?id=${boardId}">
			<table>
				<tr>
					<td class="option">번호</td>
					<td class="content">신규(insert)</td>
				</tr>
				<tr>
					<td class="option">제목</td>
					<td class="content"><input type="text" id="title" maxlength="20" required name="title"></td>
				</tr>
				<tr>
					<td class="option">일자</td>
					<td class="content"><span name="date">${date}</span></td>
				</tr>
				<tr>
					<td class="option">내용</td>
					<td class="content" id="content"><textarea name="content" required id="cnt" rows="20" placeholder="내용을 입력하세요"></textarea></td>
					
				</tr>
			</table>
			<div>
			<div style="width: 960px; display: inline-block;"></div>
			<input type="button" class="cancel" onclick="location.href='PostTable?id=${boardId}&from=1'" value="취소">
			<input type="submit" class="write" value="쓰기">
			</div>
		</form>
	</body>
</html>