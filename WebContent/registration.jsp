<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration Site</title>
    <link href="styles/registrationStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

    <h1>Registration Form A</h1>

    <form name="registerFormA" method="post" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="registerA">
        <div class="input">
            <label for="userId">User Id:</label>
            <input type="text" name="userId" maxlength="8" required>
            <label for="password">Password:</label>
            <input type="password" name="password" maxlength="8" required>
            <label for="passwordRepeat">Password (repeat):</label>
            <input type="password" name="passwordRepeat" maxlength="8" required>
            <label for="firstName">First name:</label>
            <input type="text" name="firstName" required>
            <label for="lastName">Last name:</label>
            <input type="text" name="lastName" required>
            <label for="ssn">SSN:</label>
            <input type="text" name="ssn" placeholder="555-55-5555" pattern="\d{3}-?\d{2}-?\d{4}" required>
            <label for="email">Email:</label>
            <input type="email" name="email" required>         
        </div>

        <div class="button">
            <input type="submit" name="continue" value="Continue">
        </div>
    </form>
    
    <h1>Registration Form B</h1>
    
    <form name="registerFormB" method="post" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="registerB">
		<div class="input">
			<label for="address">Address:</label> 
			<input type="text" name="address" required>
			<label for="city">City:</label>
			<input type="text" name="city" required> 
			<label for="state">State:</label> 
			<select name="state" required>
			    <option value="AL">AL</option>
			    <option value="AK">AK</option>
			    <option value="AZ">AZ</option>
			    <option value="AR">AR</option>
			    <option value="CA">CA</option>
			    <option value="CO">CO</option>
			    <option value="CT">CT</option>
			    <option value="DE">DE</option>
			    <option value="DC">DC</option>
			    <option value="FL">FL</option>
			    <option value="GA">GA</option>
			    <option value="HI">HI</option>
			    <option value="ID">ID</option>
			    <option value="IL">IL</option>
			    <option value="IN">IN</option>
			    <option value="IA">IA</option>
			    <option value="KS">KS</option>
			    <option value="KY">KY</option>
			    <option value="LA">LA</option>
			    <option value="ME">ME</option>
			    <option value="MD">MD</option>
			    <option value="MA">MA</option>
			    <option value="MI">MI</option>
			    <option value="MN">MN</option>
			    <option value="MS">MS</option>
			    <option value="MO">MO</option>
			    <option value="MT">MT</option>
			    <option value="NE">NE</option>
			    <option value="NV">NV</option>
			    <option value="NH">NH</option>
			    <option value="NJ">NJ</option>
			    <option value="NM">NM</option>
			    <option value="NY">NY</option>
			    <option value="NC">NC</option>
			    <option value="ND">ND</option>
			    <option value="OH">OH</option>
			    <option value="OK">OK</option>
			    <option value="OR">OR</option>
			    <option value="PA">PA</option>
			    <option value="RI">RI</option>
			    <option value="SC">SC</option>
			    <option value="SD">SD</option>
			    <option value="TN">TN</option>
			    <option value="TX">TX</option>
			    <option value="UT">UT</option>
			    <option value="VT">VT</option>
			    <option value="VA">VA</option>
			    <option value="WA">WA</option>
			    <option value="WV">WV</option>
			    <option value="WI">WI</option>
			    <option value="WY">WY</option>
		     </select> 
		     <label for="postalCode">ZIP / Postal Code:</label>
		     <input type="number" name="postalCode" required>
		</div>

		<div class="button">
            <input type="submit" name="register" value="Register">
            <input type="reset" value="Reset">
        </div>
    </form>
    
    <p>${message}</p>
    
</body>
</html>