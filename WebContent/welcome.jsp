<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration Site</title>
    <link href="styles/loginStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

	<h1>Welcome to the Student Registration Site</h1>

	<p>${message}</p>
	
	<h3>Select your next action:</h3>
	
	<form name=goForm method="post" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="go">
        <label for="course">Register for course</label>
        <input type="radio" name="goRadio" value="course" checked><br>
        <label for="logout">Logout</label>
        <input type="radio" name="goRadio" value="logout">
        <div class="button">
            <input type="submit" name="submit" value="Submit">
        </div>
	</form>

	<h3>For new users, please register first</h3>

	<form name="registerForm" method="post" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="register">
		<div class="button">
			<input type="submit" name="register" value="Register">
		</div>
	</form>

</body>
</html>