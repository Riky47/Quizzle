<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		<script type="text/javascript">
			window.addEventListener('load', function () {
				var subjectsAttribute = "${subjects}" // Load and parse subjects Java vector
				subjectsAttribute = subjectsAttribute.replace("[", "")
				subjectsAttribute = subjectsAttribute.replace("]", "")
				
				var subjectFlag = document.getElementById("subjectFlag")
				var subjectsDiv = document.getElementById("subjectsDiv")
				var sendButton = document.getElementById("sendButton")
				var subjects = subjectsAttribute.split(",") // Parse
				var buttons = [] // Stores the buttons
				var index = 0
				
				console.log(subjects)
				subjects.forEach(function(subject) {
					var btn = document.createElement("Button") // Create new subject button
                    btn.innerHTML = subject
					btn.type = "button"
					
					var id = ++index
					btn.onclick = function() {
						buttons.forEach(function(button) {
							button.style.background = ""
						})
						
						subjectFlag.value = id
						btn.style.background = "#ff6666"
						sendButton.type = "submit"
					}
					
					buttons.push(btn)
					subjectsDiv.appendChild(btn)
				})
			})
		</script>
	</head>

	<body>
		<h1>Quiz</h1><br>
		<form action="mainServlet">
			<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
			<input type="hidden" name="subjectFlag" id="subjectFlag" value="">
			
			<div id="subjectsDiv">
			
			</div><br>
			
			<button type="button" id="sendButton">Select</button>
		</form>
	</body>
</html>