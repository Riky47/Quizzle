<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		<script type="text/javascript">
			window.addEventListener('load', function () {
				var userbox = document.getElementById('userbox')
				var sendButton = document.getElementById('sendButton')
				
				userbox.addEventListener('input', function() {
					if (userbox.value == "") {
						sendButton.type = "button"
					}
					else {
						sendButton.type = "submit"
					}
				})
			})
		</script>
	</head>

	<body>
		<h1>Quiz</h1><br>
		<form action="mainServlet">
			<input type="text" name="userFlag" id="userbox" placeholder="Username">
			<input type="hidden" name="forceFlag" id="forcePage" value="subjects">
			
			<button type="button" id="sendButton">Start</button>
		</form><br>
		
		<form action="mainServlet">
			<input type="hidden" name="forceFlag" id="forcePage" value="leaderboard">
			<button type="submit" id="sendButton">Leaderboard</button>
		</form>
	</body>
</html>