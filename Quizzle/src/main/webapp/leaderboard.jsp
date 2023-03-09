<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		<style>
			.selected {
				background-color: #ff6666;
			}
		</style>
		<script type="text/javascript">
			window.addEventListener('load', function () {
				var leaderboard = JSON.parse('${leaderboard}') // Load and parse subjects Java vector'	
				var boardDiv = document.getElementById("boardDiv")
				var index = 0
	
				for (var [user, points] of Object.entries(leaderboard)) {
					console.log(index, user)
					var label = document.createElement("h3") // Create new subject button
					label.classList.add('non-selected')
                    label.innerHTML = (++index) + ". " + user + ": " + points
					
					boardDiv.appendChild(label)
				}
			})
		</script>
	</head>

	<body>
		<h1>Leaderboard:</h1><br>
		<form action="mainServlet">
			<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
			<div id="boardDiv">
			
			</div><br>
			
			<button type="submit" id="sendButton">Back</button>
		</form>
	</body>
</html>