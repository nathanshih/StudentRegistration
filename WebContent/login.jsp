<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration Site</title>
    <link href="styles/loginStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

	<h1>Welcome to the Student Registration Site</h1>

	<h3>If you already have an account, please login</h3>
        
	<form name="loginForm" method="post" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="login">
		<div class="input">
			<label for="userId">User Id:</label>
            <input type="text" name="userId" maxlength="8">
            <label for="password">Password:</label>
            <input type="password" name="password" maxlength="8">
		</div>
		<div class="button">
			<input type="submit" name="login" value="Login">
            <input type="reset" value="Reset">
		</div>
	</form>

	<p>${message}</p>

	<h3>For new users, please register first</h3>

	<form name="registerForm" method="post" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="register">
		<div class="button">
			<input type="submit" name="register" value="Register">
		</div>
	</form>

</body>
</html>