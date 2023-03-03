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
				
				var index = 0
                		var buttons = []
				var answers = attributes.split(",")
                		var answersDiv = document.getElementById("answersDiv")
                		var answerFlag = document.getElementById("answerFlag")
				
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
					answersDiv.appendChild(btn)
				})
			})
		</script>	
	</head>
		<h1>Quiz</h1><br>
		<form action="mainServlet">
            		<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
            		<input type="hidden" name="answerFlag" id="answerFlag" value="${answerFlag}">

			<div id="answersDiv"></div>

            		</div><br>

            		<button type="submit">Conferma</button>
		</form>
	<body>
	</body>
</html>
