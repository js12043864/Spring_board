<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="kr.ac.kopo.kopo12.domain.*"%>
<%@ page import="kr.ac.kopo.kopo12.dao.*"%>
<%@ page import="kr.ac.kopo.kopo12.service.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
		window.addEventListener('DOMContentLoaded', function() {
			location.href = 'PostTable?id=${boardId}&from=1';
		});
		</script>
</head>
<body>

</body>
</html>