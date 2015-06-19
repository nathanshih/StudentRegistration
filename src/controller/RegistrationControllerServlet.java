package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegistrationControllerServlet
 */
public class RegistrationControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String serverUrl;
	private String dataSourceName;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationControllerServlet() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	// set the serverUrl and dataSourceName from the init parameters specified in web.xml
    	setServerUrl(getInitParameter("serverUrl"));
    	setDataSourceName(getInitParameter("dataSourceName"));
    }
    
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RegistrationControllerServlet called.");
		
		// per requirements, set the URL to connect to WLS and the DataSource name as session variables
		HttpSession session = request.getSession();
		if (session.getAttribute("serverUrl") == null) {
			session.setAttribute("serverUrl", serverUrl);
		}
		if (session.getAttribute("dataSourceName") == null) {
			session.setAttribute("dataSourceName", dataSourceName);
		}
		
		// forward request to either the LoginServlet or RegisterServlet
		RequestDispatcher rd;
		String formName = request.getParameter("formName");
		if (formName.contains("register")) { // dispatch to RegisterServlet
			if (formName.equals("register")) {
				rd = request.getRequestDispatcher("/registration.jsp");
			} else {
				rd = request.getRequestDispatcher("RegisterServlet");
			}
		} else if (formName.equals("login")) { // dispatch to LoginServlet
			rd = request.getRequestDispatcher("LoginServlet");
		} else if (formName.equals("go")) {
			rd = request.getRequestDispatcher("/courses.jsp");
			if (request.getParameter("goRadio").equals("logout")) {
				session.invalidate();
				session = request.getSession();
				rd = request.getRequestDispatcher("/login.jsp");
			}
		} else { // dispatch back to same page
			rd = request.getRequestDispatcher("/login.jsp");
		}
		
		// store the form so RegisterServlet knows whether it is using form A or B
		session.setAttribute("formName", formName);
		
		rd.forward(request, response);
	}
}
