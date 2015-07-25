<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration Site</title>
    <link href="styles/loginStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

    <h1>Course registration status</h1>
        
    <form name="statusForm" method="get" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="statusForm">
        <input type="text" name="courseId">
        <div class="button">
            <input type="submit" name="courseStatus" value="Get course status">
        </div>
    </form>

	<br>
	
	${message}

</body>
</html>