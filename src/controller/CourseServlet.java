package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import database.CourseQueryViaDataSource;
import database.RegistrationViaDataSource;
import domain.Course;

/**
 * Servlet implementation class CourseServlet
 */
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private int courseCapacity;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseServlet() {
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
		System.out.println("CourseServlet called.");
		
		HttpSession session = request.getSession();
		
		String message = "";
		
		String action = request.getParameter("courseButton");
		
		if (StringUtils.containsIgnoreCase(action, "refresh")) {
			refreshCourseList(session);
			
			message = "Refreshing course list.";
		} else if (StringUtils.containsIgnoreCase(action, "register")) {
			// get the URL for WLS and DataSourceName from the session variable passed in from the RegistrationControllerServlet
			String serverUrl = (String) session.getAttribute("serverUrl");
			String dataSourceName = (String) session.getAttribute("dataSourceName");
			
			int courseId = Integer.parseInt(request.getParameter("courses"));
			
			// attempt registration
			RegistrationViaDataSource registration = new RegistrationViaDataSource(courseId, courseCapacity, serverUrl, dataSourceName);
			int result = registration.registerCourse();
			if (result > 0) {
				message = "You have been registered to " + courseId;
 			} else {
				message = "Sorry, the registration to this course has been closed based on availability.";
			}
			
			refreshCourseList(session);
			
			// close connection
			registration.closeConnection();
		}
			
		// return response to caller
		request.setAttribute("message", message);
		request.getRequestDispatcher("/courses.jsp").forward(request, response);
	}
	
	private void refreshCourseList(HttpSession session) {
		CourseQueryViaDataSource courseQuery = new CourseQueryViaDataSource((String) session.getAttribute("serverUrl"), 
																			(String) session.getAttribute("dataSourceName"));
		List<Course> courseList = courseQuery.getAllCourses();
		session.setAttribute("courseList", courseList);
		courseQuery.closeConnection();
	}
}
