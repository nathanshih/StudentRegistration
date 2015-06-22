<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration Site</title>
    <link href="styles/loginStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

    <h1>Courses available for registration</h1>
        
    <form name="registrarForm" method="post" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="registrarForm">
        Register for course ${course}?
        <div class="button">
            <input type="submit" name="registerButton" value="Register">
            <input type="submit" name="registerButton" value="Cancel">
        </div>
    </form>

    <p>${message}</p>

</body>
</html>