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
			.selected {
				background-color: #ff6666;
			}
		</style>
		<script type="text/javascript">	
			var answersRecap = JSON.parse('${answersRecap}')
			var pointsRecap = JSON.parse('${pointsRecap}')
		
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
			
			function loadSubject(sub) {
				var recapDiv = document.getElementById("recapDiv")
				recapDiv.innerHTML = ""
				
				for (var [subject, questions] of Object.entries(answersRecap)) {
					if (sub == subject) {
						if (typeof(pointsRecap[subject]) == "number") {
							var title = document.createElement("h2")
							title.classList.add("subject")
							title.innerHTML = subject + ": " + pointsRecap[subject]						
							recapDiv.appendChild(title)
						}
						
						for (var [index, question] of Object.entries(questions)) {
							for (var [text, answers] of Object.entries(question)) {
								var answerList = document.createElement("ul")
								answerList.classList.add("answer-list")
								
								var questionLabel = document.createElement("h3")
								questionLabel.classList.add("question")
								questionLabel.innerHTML = index + ". " + text
								
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
					}
				}
			}
			
			window.addEventListener('load', function () {
				var subjectsDiv = document.getElementById("subjectsDiv")
				var buttons = [] // Stores the buttons
				var index = 0
				var first = true
				
				for (let [subject, questions] of Object.entries(answersRecap)) {
					let btn = document.createElement("Button") // Create new subject button
					btn.classList.add('non-selected')
	                btn.innerHTML = subject
					btn.type = "button"
					
					let id = ++index
					btn.onclick = function() {
						buttons.forEach(function(button) {
							button.classList.remove('selected')
							button.classList.remove('non-selected')
							button.classList.add('non-selected')
						})
						
						btn.classList.remove('non-selected')
						btn.classList.add('selected')
						loadSubject(subject);
					}
					
					if (first) {
						first = false

						btn.classList.remove('non-selected')
						btn.classList.add('selected')
						loadSubject(subject);
					}
					
					buttons.push(btn)
					subjectsDiv.appendChild(btn)
				}
			})
		</script>
	</head>
	<body>	
		<h1>Final score: ${balance}</h1>
		<br>
		<form action="mainServlet">
			<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
			<input type="hidden" name="forceFlag" id="forcePage" value="subjects">
			<div id="subjectsDiv">
			
			</div><br>
		
			<div id="recapDiv">

            </div><br>
            
            <button type="submit" id="sendButton">Start new quiz</button>
		</form>
		
		<form action="mainServlet">
			<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
			<input type="hidden" name="forceFlag" id="forcePage" value="leaderboard">
			
			<button type="submit" id="sendButton">Leaderboard</button>
		</form>
	</body>
</html>
