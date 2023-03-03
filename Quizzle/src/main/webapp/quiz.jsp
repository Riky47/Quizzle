<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		<script type="text/javascript">
			window.addEventListener('load', function () {
				var answersAttribute = "${answers}" // Load and parse answers Java vector
				answersAttribute = answersAttribute.replace("[", "")
				answersAttribute = answersAttribute.replace("]", "")

				var index = 0
				var buttons = [] // Stores answers buttons
				var maxAnswers = "${maxAnswers}" // Load maxAnswers Java integer
				var answers = answersAttribute.split(",") // Parse
				var answersDiv = document.getElementById("answersDiv")
				var answerFlag = document.getElementById("answerFlag")

				answers.forEach(function(answer) {
					var btn = document.createElement("Button") // Create new answer button
                    btn.innerHTML = answer
					btn.type = "button"

					let id = ++index; // Answer id
					btn.onclick = function() {
						buttons.forEach(function(button) {
							button.style.background = ""
						})

						if (maxAnswers > 1) { // If multiple answers
						    var ids = answerFlag.value.split(",")
						    var found = false
						    var value = ""

						    for (i=0; i<ids.length; i++) { // Check and parse flag value
								var num = Number(ids[i])
								if (typeof(num) == "number" && num > 0) {
								    ids[i] = num
								    if (num == id) { // Checks if the current id is already in
									found = true
								    }
								}
								else {
								    ids.splice(i, 1)
								    i--
								}
						    }

						    if (!found) { // If id its not already selected
								ids.push(id)
						    }

						    while (ids.length > maxAnswers) { // Delete olders
								ids.shift()
						    }

						    ids.forEach(function(answerId) { // Create new flag value
								value += (value == "" ? "" : ",") + answerId
								buttons[answerId -1].style.background = "#ff6666"
						    })

						    answerFlag.value = value
						}
						else {
							answerFlag.value = id // Change flag value
						    btn.style.background = "#ff6666"
						}
					}
					
					buttons.push(btn)
					answersDiv.appendChild(btn)
				})
			})
		</script>	
	</head>
	<body>	
		<h1>${question}</h1><br>
		<form action="mainServlet">
            <input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
            <input type="hidden" name="answerFlag" id="answerFlag" value="">
	
			<div id="answersDiv"></div>

            </div><br>

            <button type="submit">Send</button>
		</form>
	</body>
</html>
