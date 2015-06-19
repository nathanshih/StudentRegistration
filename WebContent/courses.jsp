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
        
    <form name="courseForm" method="post" action="RegistrationControllerServlet">
        <input type="hidden" name="formName" value="courseForm">
        <div class="input">
            <label for="courses">Coures:</label>
            <select name="coures">
	            <c:forEach var="courseList" items="${courseList}">
	                <option value="${courseList.courseId}">${courseList}</option>
	            </c:forEach>
            </select>
        </div>
        <div class="button">
            <input type="submit" name="courseButton" value="Refresh Course List">
            <input type="submit" name="courseButton" value="Register">
        </div>
    </form>

    <p>${message}</p>

</body>
</html>