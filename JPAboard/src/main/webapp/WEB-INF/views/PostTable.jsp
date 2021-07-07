<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="kr.ac.kopo.kopo12.domain.*"%>
<%@ page import="kr.ac.kopo.kopo12.dao.*"%>
<%@ page import="kr.ac.kopo.kopo12.service.*"%>
<%@ page import="kr.ac.kopo.kopo12.boardWeb.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="/JPAboard/resources/board.css?after" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div id="navigation">
		<a href="PostTable?id=${boardId}&from=1" id="navi"
			style="width: 500px; display: inline-block;"><h1>${name}</h1></a> <input
			type="button" id="selectBoard" onclick="location.href='boardSelect'"
			value="게시판 선택">
	</div>
	<div>
		<form method="post" id="move" action="PostSearch?boardId=${boardId}">
			<input type="text" class="search" name="keyWord" placeholder="검색어 입력">
			<input type="submit" id="search" value="검색">
		</form>
	</div>
	<br>
	<table>
		<tr>
			<td class="Id"><b>번호</b></td>
			<td class="Ttl"><b>제목</b></td>
			<td class="day"><b>등록일</b></td>
		</tr>

		<c:forEach begin="${first}" end="${end}" varStatus="status">
			<c:set var="i" value="${i+1}"></c:set>
			<tr>
				<td class=num><c:out value="${i+first}"></c:out></td>
				<td class=title><a
					href=PostView?id=${boardItemList[i+first-1].getId()}&boardId=${boardItemList[i+first-1].getBoard().getId()}
					id=movePage>${boardItemList[i+first-1].getTitle()}</a></td>
				<td class=date>${boardItemList[i+first-1].getDate()}</td>
			<tr>
		</c:forEach>
	</table>
	<div>
		<div style="width: 1103px; display: inline-block;"></div>
		<button class="new" onclick="location.href='PostInsert?id=${boardId}'">신규</button>
	</div>
	<div>
		<br>
		<div style="width: 850px; display: inline-block;"></div>
		<span><a href="PostTable?id=${boardId}&from=1">처음</a></span> <span><a
			href=PostTable?id=${boardId}&from=${boardNum[4]-1}><</a></span>
		<c:set var="a" value="0"></c:set>
		<c:set var="do_loop" value="false"></c:set>
		<c:forEach begin="${boardNum[2]}" end="${boardNum[2] + 10}"
			varStatus="status">

			<c:if test="${do_loop ne true}">
				<c:set var="a" value="${a+1}"></c:set>
				<c:choose>
					<c:when test="${a+boardNum[2]-1 > boardNum[3]}">
						<c:set var="do_loop" value="true"></c:set>
					</c:when>
					<c:otherwise>
						<span><a
							href=PostTable?id=${boardId}&from=${a+boardNum[2]-1}>[${a+boardNum[2]-1}]</a></span>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:forEach>
		<span><a href=PostTable?id=${boardId}&from=${boardNum[4]+1}>></a></span>
		<span><a href=PostTable?id=${boardId}&from=${(boardNum[3]-1)*10 + 1}>끝</a></span>
		<span>&nbsp;현재페이지: ${boardNum[4]}</span>
	</div>
</body>
</html>