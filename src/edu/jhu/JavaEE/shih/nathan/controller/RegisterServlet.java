package edu.jhu.JavaEE.shih.nathan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.jhu.JavaEE.shih.nathan.dao.StudentInsertViaDataSource;
import edu.jhu.JavaEE.shih.nathan.domain.StudentInfo;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RegisterServlet called.");

		HttpSession session = request.getSession();

		String message = "";
		String forwardDestination = "";
		
		// get the StudentInfo object stored as a session variable
		StudentInfo studentInfo;
		if (session.getAttribute("studentInfo") == null) {
			studentInfo = new StudentInfo();
		} else {
			studentInfo = (StudentInfo) session.getAttribute("studentInfo");
		}

		String formName = (String) session.getAttribute("formName");
		
		if (formName.equals("registerA")) { // process form A
			studentInfo.setUserId(request.getParameter("userId"));
			studentInfo.setPassword(request.getParameter("password"));
			studentInfo.setFirstName(request.getParameter("firstName"));
			studentInfo.setLastName(request.getParameter("lastName"));
			studentInfo.setSsn(request.getParameter("ssn"));
			studentInfo.setEmail(request.getParameter("email"));

			// store info from form A in session variable for later usage
			session.setAttribute("studentInfo", studentInfo);
			
			System.out.println("Student info saved: " + studentInfo.toString());
			
			message = "Form A saved.";
			forwardDestination = "/registration.jsp";
		} else if (formName.equals("registerB")) { // process form B and do registration
			String address = "";
			address = request.getParameter("address");
			address = address + " " + request.getParameter("city");
			address = address + ", " + request.getParameter("state");
			address = address + " " + request.getParameter("postalCode");
			
			studentInfo.setAddress(address);
			
			// store info from form b in session variable for later usage
			session.setAttribute("studentInfo", studentInfo);
			
			// get the URL for WLS and DataSourceName from the session variable passed in from the RegistrationControllerServlet
			String serverUrl = (String) session.getAttribute("serverUrl");
			String dataSourceName = (String) session.getAttribute("dataSourceName");
			
			System.out.println("Student info to be registered: " + studentInfo.toString());
			
			// register the student
			StudentInsertViaDataSource studentInsert = new StudentInsertViaDataSource(studentInfo, serverUrl, dataSourceName);
			int result = studentInsert.insert();
			if (result > 0) {
				message = "Welcome to the site, " + studentInfo.getFirstName() + " " + studentInfo.getLastName();

				request.setAttribute("message", message);
				forwardDestination = "/login.jsp";
			} else {
				message = "Student not registered.";
			}
			
			// close connection
			studentInsert.closeConnection();
		}
			
		// return response to caller
		request.setAttribute("message", message);
		request.getRequestDispatcher(forwardDestination).forward(request, response);
	}
}
