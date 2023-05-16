<!DOCTYPE html>

<html>
	<meta name = "viewport" content="width=device-widtk,initial-scale=0.9">
	<head>
		<meta charset="ISO-8859-1">
		<title>Quizzle</title>
		<style type="text/css">
			body{
				background:#FFFFFF ;
				background-image: linear-gradient(#00b3ff,#00509C);
				background-repeat : no-repeat;
				font-family : Garamond;
				text-align: center;
				font-size: 40px;
			}
			:root {
				touch-action : pan-x pan-y;
				height: 100%
			}
			h1{
				color : white;
				text-decoration-line: underline;
				text-shadow: 8px 8px 10px black;
				font-family : Garamond;
				font-size: 80px;
				text-align: center;
				
			}
			p{
				color :white;
				line-height: 10px;
			}
			button{
				background-image: linear-gradient(#00b3ff,#00509C);
				color : white;
				padding : 8px 20px;
				cursor : pointer;
				border-radius: 25px;
				text-align: center;
				border: white;
			}
			img{
  				opacity: 1;
  				border-radius: 50%;
			}
			input{
				line-height: 20px;
			}
			button:hover {
  				box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
			}
		</style>
		<script type="text/javascript">
			window.addEventListener('load', function () {
				var userbox = document.getElementById('userbox')
				var sendButton = document.getElementById('sendButton')
				
				userbox.addEventListener('input', function() {
					if (userbox.value == "") {
						sendButton.type = "button"
					}
					else {
						sendButton.type = "submit"
					}
				})
			})
		</script>
	</head>

	<body>
		<h1>Quizzle</h1>
		<img src="https://image.isu.pub/130530084357-ad9d8074218d17be2b743ff567badedb/jpg/page_1.jpg"  style="width: 465px; height: 348.75px; margin: 0px";>
		<p>Inserisci nome utente</p><br>
		<form action="mainServlet">
			<input type="text" name="userFlag" id="userbox" placeholder="Username">
			<input type="hidden" name="forceFlag" id="forcePage" value="subjects">
			
			<button type="button" id="sendButton">Start</button>
		</form><br>
		
		<form action="mainServlet">
			<input type="hidden" name="forceFlag" id="forcePage" value="leaderboard">
			<button type="submit" id="sendButton">Leaderboard</button>
		</form>
	</body>
</html>