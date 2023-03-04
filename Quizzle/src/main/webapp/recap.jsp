<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		<style>
			.correct-answer {
				color: #4fb355;
			}
			.wrong-answer {
				color: #ff6666;
			}
		</style>
		<script type="text/javascript">	
			function space() {
				var space = document.createElement("br")
				space.classList.add("space")
				return space
			}
			
			function tab() {
				var tab = document.createElement("label")
				tab.classList.add("tab")
				tab.innerHTML = "&emsp;"
				return tab
			}
			
			window.addEventListener('load', function () {
				var recap = JSON.parse('${recap}')
				var recapDiv = document.getElementById("recapDiv")
				
				for (var [index, question] of Object.entries(recap)) {
					for (var [text, answers] of Object.entries(question)) {
						var answerList = document.createElement("ul")
						answerList.classList.add("answer-list")
						
						var questionLabel = document.createElement("h3")
						questionLabel.classList.add("question")
						questionLabel.innerHTML = text
						
						recapDiv.appendChild(questionLabel)
						recapDiv.appendChild(answerList)
						recapDiv.appendChild(space())
						
						for (var [answer, isCorrect] of Object.entries(answers)) {
							var item = document.createElement("li")
							item.classList.add("answer-item")
							
							var answerFlag = document.createElement("Label")
							answerFlag.classList.add(isCorrect ? "correct-answer" : "wrong-answer")
							answerFlag.innerHTML = isCorrect ? "[ Correct ]" : "[ Wrong ]"
							
							var answerLabel = document.createElement("Label")
							answerLabel.classList.add("answer-text")
							answerLabel.innerHTML = answer
							
							answerList.appendChild(item)
							item.appendChild(answerFlag)
							item.appendChild(tab())
							item.appendChild(answerLabel)
						}
					}
				}
			})
		</script>	
	</head>
	<body>	
		<h1>Final score: ${balance}</h1>
		<br>
		<form action="mainServlet">
			<div id="recapDiv">

            </div><br>
		</form>
	</body>
</html>
