package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import database.StudentLoginViaDataSource;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int loginAttemptsAllowed;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	// set the loginAttemptsAllowed from the init parameter specified in web.xml
    	String paramValue = getInitParameter("loginAttemptsAllowed");
    	this.loginAttemptsAllowed = Integer.parseInt(paramValue);
    }
	
	public int getLoginAttemptsAllowed() {
		return loginAttemptsAllowed;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LoginServlet called.");
		
		HttpSession session = request.getSession();

		String message = "";
		
		// get or set current login attempt via session variable
        int loginAttempt;
        if (session.getAttribute("loginCount") == null) {
            session.setAttribute("loginCount", 1);
            loginAttempt = 1;
        }
        else {
             loginAttempt = (Integer) session.getAttribute("loginCount");
        }
        
        // check if loginAttempsAllowed has been exceeded
		if (loginAttempt >= loginAttemptsAllowed) {
			message = "Max number of login attempts exceeded.";
			session.invalidate();
		
		// proceed with login attempt
		} else {
			loginAttempt++;
			session.setAttribute("loginCount", loginAttempt);
			String userId = request.getParameter("userId");
			String password = request.getParameter("password");
			
			// check if the userId or password meet requirements
			if (!validateFieldValue(userId) || !validateFieldValue(password)) {
				message = "UserId or Password invalid.";
				
			// query the Student table to perform login
			} else {
				// get the URL for WLS and DataSourceName from the session variable passed in from the RegistrationControllerServlet
				String serverUrl = (String) session.getAttribute("serverUrl");
				String dataSourceName = (String) session.getAttribute("dataSourceName");
				
				StudentLoginViaDataSource studentLogin = new StudentLoginViaDataSource(userId, password, serverUrl, dataSourceName);
				ResultSet rs = studentLogin.login();
				try {
					if (rs.next()) {	
						String firstName = rs.getString("FIRST_NAME");
						String lastName = rs.getString("LAST_NAME");

						message = "Welcome to the site, " + firstName + " " + lastName;
						
						// reset login attempts
						session.removeAttribute("loginCount");
					} else {
						message = "Sorry, you don't have an account. You must register first.";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					// close connection
					studentLogin.closeConnection();
				}
			}
		}

		// return response to caller
		request.setAttribute("message", message);
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	/**
	 * Per the assignment instructions, this will verify whether the userId or password
	 * meets theses requirements:
	 * <ol>
	 * 	<li>8 characters
	 * 	<li>cannot contain space(s)
	 * 	<li>cannot be empty or null
	 * </ol>
	 *
	 * @param value - the string value to validate
	 * @return
	 */
	private boolean validateFieldValue(String value) {
		
		// value cannot be empty or null
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		
		// value must be 8 characters
		if (value.length() != 8) {
			return false;
		}
		
		// value cannot contain space(s)
		if (value.contains(" ")) {
			return false;
		}
		
		// value is valid per assignment requirements
		return true;
	}
}
