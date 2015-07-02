package edu.jhu.JavaEE.shih.nathan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import edu.jhu.JavaEE.shih.nathan.beans.Course;
import edu.jhu.JavaEE.shih.nathan.dao.CourseQueryViaDataSource;

/**
 * Servlet implementation class CourseServlet
 */
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CourseServlet called.");
		
		HttpSession session = request.getSession();
		
		String message = "";
		String forwardDestination = "";
		
		String action = request.getParameter("courseButton");
		
		if (StringUtils.containsIgnoreCase(action, "refresh")) {
			refreshCourseList(session);
			
			message = "Refreshing course list.";
			forwardDestination = "/courses.jsp";
		} else if (StringUtils.containsIgnoreCase(action, "register")) {
			
			// get the courseid from the request
			int courseId = Integer.parseInt(request.getParameter("courses"));
			
			// get the URL for WLS and DataSourceName from the session variable passed in from the RegistrationControllerServlet
			String serverUrl = (String) session.getAttribute("serverUrl");
			String dataSourceName = (String) session.getAttribute("dataSourceName");
			
			// get the course information
			CourseQueryViaDataSource courseQuery = new CourseQueryViaDataSource(serverUrl, dataSourceName);
			Course course = courseQuery.getCourseById(courseId);
			
			// save courseid as a session variable for later use
			session.setAttribute("course", course);
			
			forwardDestination = "/registrarcourse.jsp";
			
			// close connection
			courseQuery.closeConnection();
		}
			
		// return response to caller
		request.setAttribute("message", message);
		request.getRequestDispatcher(forwardDestination).forward(request, response);
	}
	
	private void refreshCourseList(HttpSession session) {
		CourseQueryViaDataSource courseQuery = new CourseQueryViaDataSource((String) session.getAttribute("serverUrl"), 
																			(String) session.getAttribute("dataSourceName"));
		List<Course> courseList = courseQuery.getAllCourses();
		session.setAttribute("courseList", courseList);
		courseQuery.closeConnection();
	}
}
