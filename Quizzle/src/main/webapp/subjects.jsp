<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		<style>
		
		
		#Quizzle{

  font-size: 40px;
  
  text-align: center;
  
  
  color: white;
  text-decoration-line:underline;
  text-shadow:8px -8px 10px black;
  font-family:Garamond;
  

}

	:root{
		touch-action: pan-x pan-y;
		height: 100%;
	}
		
body {
  background-color: #FFFFFF;
  background-image: linear-gradient(#00b3ff, #00509C);
  background-repeat: no-repeat;
   
}



h2 {
  color:white;
  text-align: center;
}

p {
  font-family: verdana;
  
  font-size: 20px;
}
.button {
    color:white;
    font-family:Garamond
    top: 50%;
    background-color: blue;
}
.form-submit-button {

background: #0066A2;

color: red;

border-style: outset;

border-color: #0066A2;

height: 100px;

width: 100px;

font: bold 15px arial, sans-serif;

text-shadow:none;

}
	
			.selected {
  color:white;
  font-family: Garamond;
  padding:8px 20px;
  text-align:center;
  border-radius: 25px;
  background-color:#0072be;
  cursor:pointer;
}

.selected:hover {box-shadow: 0 6px 8px 0 rgba;
rgba(0, 0, 0.19);
}



.selected:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
.non-selected {
   color:white;
  font-family: Garamond;
  padding:8px 20px;
  text-align:center;
  border-radius: 25px;
  background-color:#0072be;
  cursor:pointer;
     margin: 0 auto;
  
}

.non-selected:hover {box-shadow: 0 6px 8px 0 rgba;
rgba(0, 0, 0.19);}



.non-selected:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}

	</style>
		<script type="text/javascript">
			window.addEventListener('load', function () {
				var subjectsAttribute = `${subjects}` // Load and parse subjects Java vector
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
	<div id=Quizzle>
		<h1> Quizzle </h1>
	</div>
		<h2>Subject:</h2><br>
		
		
		<form action="mainServlet">
			<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
			<input type="hidden" name="subjectFlag" id="subjectFlag" value="">
			
			<div id="subjectsDiv">
			
			</div><br>
			
	
			<button type="button" id="sendButton">Select</button>
		</form>
	</body>
</html>