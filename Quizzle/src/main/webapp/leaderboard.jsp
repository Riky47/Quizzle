<!DOCTYPE html>
	
<html>
	<meta name="viewport" content="width=device-width, initial-scale=0.9">
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
	
		<style>
			body {
				background: #FFFFFF;
				background-image: linear-gradient(#00b3ff, #00509C);
				background-repeat: no-repeat;
		}
	
	:root {
	  touch-action: pan-x pan-y;
	  height: 100% 
	  
	}
	
	
	#leaderDiv {
		margin: 200px 500px 300px 500px;
		border: 2px;
		padding: 5px;
		background: white;
		border-radius: 25px;
		box-shadow: 15px 24px 24px 0 rgba(0, 0, 0, 0.24), 0 15px 35px 0 rgba(0, 0, 0, 0.19);
	}
	
	.Leaderboard {
		color: #0072be;
		text-align: center;
		 font-family: Garamond;
	}
	
	.button {
		color: white;
		font-family: Garamond;
		padding: 8px 20px;
		text-align: center;
		border-radius: 25px;
		background-image: linear-gradient(#00b3ff, #00509C);
		cursor: pointer;
		border: 0px solid white;
	}
	.button1 {
		padding: 8px 15px;
	}
	
	.button:hover {
		box-shadow: 0 6px 8px 0 rgba(0, 0, 0, 0.24), 0 10px 25px 0
			rgba(0, 0, 0, 0.19);
	}
	
	form {
		text-align: center;
	}
	
	#Quizzle{
		text-align: center;
		font-size : 40px;
		color : white;
		text-decoration-line: underline;
		text-shadow: 8px -8px 10px black;
		font-family: Garamond;
	}
	
	#boardDiv{
		color: #00509C;
	}
	
	.boardElement{
		padding: 6px 12px;
		background: #FFFFFF;
		box-shadow: 0 6px 8px 0 rgba(0, 0, 0, 0.24), 0 10px 25px 0
			rgba(0, 0, 0, 0.19);
		margin: 20px;
		border-radius: 8px;	
		border-color: #0072be;
		border-width: 5px;
		border-style: outset;
		text-align: center;
	}
	
	.forms{
		text-align: center;
	}
	
	form{
		display: inline-block;
	}
	
	</style>
	<script type="text/javascript">
				window.addEventListener('load', function () {
					var leaderboard = JSON.parse(`${leaderboard}`) // Load and parse subjects Java vector'	
					var boardDiv = document.getElementById("boardDiv")
					var index = 0
					
					var items = items = Object.keys(leaderboard).map(function(key) {
						return [key, leaderboard[key]]
					});

					items.sort(function(first, second) {
						return second[1] - first[1]
					});
					
					for (var [position, data] of Object.entries(items)) {
						var label = document.createElement("h3") // Create new subject button
						
						label.classList.add("boardElement")
	                    label.innerHTML = (Number(position) +1) + ". " + data[0] + ": " + data[1]
						
						boardDiv.appendChild(label)
					}
				})
			</script>
	</head>
	<body>
		<div id="Quizzle">
			<h1>Quizzle</h1>
		</div>
		<div id="leaderDiv" >
			<h1 class="Leaderboard">Leaderboard</h1>
			<br>
			
			<div id="boardDiv"></div>
			<br>
				
			<div class="forms">
				<form action="mainServlet">
					<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
					
					<button class="button" type="submit" id="sendButton">Back</button>
				</form>
				<form action="mainServlet">
					<input type="hidden" name="userFlag" id="userFlag" value="${userFlag}">
					<input type="hidden" name="forceFlag" id="forceFlag" value="index">
	
					<button class="button button1"type="submit" id="sendButton">Logout</button>
				</form>
			</div>
		</div>
	</body>
	</html>