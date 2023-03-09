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
				var subjectsAttribute = "${subjects}" // Load and parse subjects Java vector
				subjectsAttribute = subjectsAttribute.replace("[", "")
				subjectsAttribute = subjectsAttribute.replace("]", "")
				
				var subjectFlag = document.getElementById("subjectFlag")
				var subjectsDiv = document.getElementById("subjectsDiv")
				var sendButton = document.getElementById("sendButton")
				var subjects = subjectsAttribute.split(",") // Parse
				var buttons = [] // Stores the buttons
				var index = 0
				
				subjects.forEach(function(subject) {
					var btn = document.createElement("Button") // Create new subject button
					btn.classList.add('non-selected')
                    btn.innerHTML = subject
					btn.type = "button"
					
					var id = ++index
					btn.onclick = function() {
						buttons.forEach(function(button) {
							button.classList.remove('selected')
							button.classList.remove('non-selected')
							button.classList.add('non-selected')
						})
						
						subjectFlag.value = id
						btn.classList.remove('non-selected')
						btn.classList.add('selected')
						sendButton.type = "submit"
					}
					
					buttons.push(btn)
					subjectsDiv.appendChild(btn)
				})
			})
		</script>
	</head>

	<body>
		<h1>Subject:</h1><br>
		<form action="mainServlet">
			<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
			<input type="hidden" name="subjectFlag" id="subjectFlag" value="">
			
			<div id="subjectsDiv">
			
			</div><br>
			
			<button type="button" id="sendButton">Select</button>
		</form>
	</body>
</html>