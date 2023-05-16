<!DOCTYPE html>
<html>
	<meta name="viewport" content="vidth=device=vidth, initial-scale=0.9">
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Audiowide">
		
		<style>
			#answersDiv{
				text-align: center;
			}
			
			body{
				background-color:  #FFFFFF;
				background-image: linear-gradient(#00b3ff,#00509C);
				background-repeat: no-repeat;
				text-align: center;
			}
			
			:root{
				touch-action: pan-x pan-y;
				height: 100%
			}
			
			#Quizzle{
				text-align: center;
				font-size: 40px;
				color: white;
				text-decoration-line:underline;
				text-shadow: 8px -8px 10px black;
				font-family: Garamond;
				}
			
			h2{
				font-family:garamond;
				padding: 15px 60px;
				text-align: center;
				color: #022d4a;
				font-size: 30px;
			}
				
			h3{
				color: #022d4a;
				font-family:garamond;
				text-align: center;
			}
			
			button{
				box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
				color: #ffffff;
				background-color:  #0072be;
				display: inline-block;
  				padding: 15px 25px;
 				font-size: 24px;
 				cursor: pointer;
				text-align: center;
			    border-radius: 8px;
			    margin: 20px 60px;
			    border: 2px solid white;
			    text-align: center;
			}
			
			button:hover {
			background-color: #189cde;
			}
			
			button:active {
				box-shadow: 0 0px 0;
 				background-color: #022d4a;
 				transform: translateY(4px);
 				 
 			}
 			
 			#sendButton{
 				background-color: #FFFFFF;
 				border: 2px solid #0072be;
 				color: #0072be;
 				border-radius: 60px;
 			}
			
			.selected{
				background-color: #022d4a;
			}
			
			.pulsante{
				text-align: center;
			}
		</style>
		<script type="text/javascript">
			window.addEventListener('load', function () {
				var answersAttribute = `${answers}` // Load and parse answers Java vector
				answersAttribute = answersAttribute.replace("[", "")
				answersAttribute = answersAttribute.replace("]", "")

				var index = 0
				var buttons = [] // Stores answers buttons
				var maxAnswers = `${maxAnswers}` // Load maxAnswers Java integer
				var answers = answersAttribute.split(",") // Parse
				var answersDiv = document.getElementById("answersDiv")
				var answerFlag = document.getElementById("answerFlag")
				var sendButton = document.getElementById("sendButton")

				answers.forEach(function(answer) {
					var btn = document.createElement("Button") // Create new answer button
					btn.classList.add("pulsante")
					btn.innerHTML = answer
					btn.type = "button"

					let id = ++index; // Answer id
					btn.onclick = function() {
						buttons.forEach(function(button) {
							button.classList.remove('selected')
							button.classList.remove('non-selected')
							button.classList.add('non-selected')
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
							
						    if (ids.length == maxAnswers) {
						    	sendButton.type = "submit"
						    }

						    ids.forEach(function(answerId) { // Create new flag value
								value += (value == "" ? "" : ",") + answerId
								buttons[answerId -1].classList.remove("non-selected")
								buttons[answerId -1].classList.add("selected")
						    })

						    answerFlag.value = value
						}
						else {
							answerFlag.value = id // Change flag value
						    sendButton.type = "submit"
						    btn.classList.remove("non-selected")
						    btn.classList.add("selected")
						}
					}
					
					buttons.push(btn)
					answersDiv.appendChild(btn)
				})
			})
		</script>	
	</head>
	<body>	
		<div id="Quizzle">
		<h1> Quizzle </h1>
		</div>
		<h3>Max reward: ${reward}</h3>
		<h2>${question}</h2>
		<br>
		<form action="mainServlet">
            <input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
            <input type="hidden" name="answerFlag" id="answerFlag" value="">
	
			<div id="answersDiv">

            </div><br>
            <button type="button" id="sendButton">Send</button>
		</form>
	</body>
</html>
