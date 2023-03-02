<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		<script type="text/javascript">
			window.addEventListener('load', function () {
				var attributes = "${answers}"
				attributes = attributes.replace("[", "")
				attributes = attributes.replace("]", "")
				
				var index = 0;
				var answers = attributes.split(",")
				
				var form = document.getElementById("mainForm")
				var userFlag = document.createElement("Input")
				userFlag.type = "hidden"
				userFlag.name = "userFlag"
				userFlag.value = "${userFlag}"
				
				var answerFlag = document.createElement("Input")
				answerFlag.type = "hidden"
				answerFlag.name = "answerFlag"
				answerFlag.value = ""
				
				form.appendChild(userFlag)
				form.appendChild(answerFlag)
				
				var buttons = []
				answers.forEach(function(answer) {
					var btn = document.createElement("Button")
					btn.type = "button"
					btn.innerHTML = answer
					let id = ++index;
					
					btn.onclick = function() {
						buttons.forEach(function(button) {
							button.style.background = ""
						})
						
						btn.style.background = "#ff6666"
						answerFlag.value = id
					}
					
					buttons.push(btn)
					form.appendChild(btn)
				})
				
				var sendBtn = document.createElement("Button")
				sendBtn.type = "submit"
				sendBtn.innerHTML = "Conferma"
				
				form.appendChild(sendBtn)
			})
		</script>	
	</head>
		<h1>Quiz</h1><br>
		<form action="mainServlet" id="mainForm">
			
		</form>
	<body>
	</body>
</html>