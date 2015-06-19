package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import database.CourseQueryViaDataSource;
import domain.Course;

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
		
		String action = request.getParameter("courseButton");
		
		if (StringUtils.containsIgnoreCase(action, "refresh")) {
			refreshCourseList(session);
			
			message = "Refreshing course list.";
		} else if (StringUtils.containsIgnoreCase(action, "register")) {
			refreshCourseList(session);

			message = "Registering course...";
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
	}
}
