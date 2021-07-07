<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.ac.kopo.kopo12.domain.*" %>
<%@ page import="kr.ac.kopo.kopo12.dao.*" %>
<%@ page import="kr.ac.kopo.kopo12.service.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<link href="/JPAboard/resources/board.css?after" rel="stylesheet"
	type="text/css" />
	</head>
	<body>
		<div id="navigation">
			<a href="PostTable?id=${boardId}&from=1" id="navi"><h1>${name}</h1></a>
		</div>
		<div style="display: inline-block; position:absolute; left:605px;">
		<span id=key><b>"${keyWord}"검색결과</b></span>
		</div>
		<br>
		<table>
			<tr>
				<td class="Id"><b>번호</b></td>
				<td class="Ttl"><b>제목</b></td>
				<td class="day"><b>등록일</b></td>
			</tr>
			<c:forEach var="boardItemList" items="${boardItemContainList}">
				<c:set var="i" value="${i+1}"></c:set>
					<tr>
						<td class=num>${i}</td>
						<td class=title><a href=PostView?id=${boardItemList.getId()}&boardId=${boardId} id=movePage>${boardItemList.title}</a></td>
						<td class=date>${boardItemList.date}</td>
					</tr>
			</c:forEach>
		</table>
	</body>
</html>