package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.RegistrationViaDataSource;
import domain.Course;

/**
 * Servlet implementation class RegisterCourseServlet
 */
public class RegisterCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private int courseCapacity;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterCourseServlet() {
        super();
        
    }

    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	// set the courseCapacity from the init parameter specified in web.xml
    	String paramValue = getInitParameter("courseCapacity");
    	this.courseCapacity = Integer.parseInt(paramValue);
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RegisterCourseServlet called.");
		
		HttpSession session = request.getSession();
		
		String message = "";

		// get the URL for WLS and DataSourceName from the session variable passed in from the RegistrationControllerServlet
		String serverUrl = (String) session.getAttribute("serverUrl");
		String dataSourceName = (String) session.getAttribute("dataSourceName");
		
		// save courseid as a session variable for later use
		Course course = (Course) session.getAttribute("course");
		
		// attempt registration
		RegistrationViaDataSource registration = new RegistrationViaDataSource(course.getCourseId(), courseCapacity, serverUrl, dataSourceName);
		int result = registration.registerCourse();
		if (result > 0) {
			message = "You have been registered to " + course.toString();
		} else {
			message = "Sorry, the registration to this course has been closed based on availability.";
		}
		
		// close connection
		registration.closeConnection();
			
		// return response to caller
		request.setAttribute("message", message);
		request.getRequestDispatcher("/registrarcourse.jsp").forward(request, response);
	}
}
