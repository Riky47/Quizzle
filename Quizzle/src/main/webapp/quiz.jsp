<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>

	<body>
		<h1>Quiz ${answer_1}</h1><br>
		<form action="mainServlet">
			<input type="hidden" name="userFlag" value="${userFlag}">
			<input type="hidden" name="answerFlag" value="${answer_1}">
			<button type="submit">Inizia</button>
		</form>
	</body>
</html>