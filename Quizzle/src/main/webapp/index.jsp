<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
	</head>

	<body>
		<h1>Quiz</h1><br>
		<form action="mainServlet">
			<input type="text" name="userFlag" placeholder="Username">
			<input type="hidden" name="subjectFlag" id="subjectFlag" value="">
			
			<button type="submit">Start</button>
		</form>
	</body>
</html>