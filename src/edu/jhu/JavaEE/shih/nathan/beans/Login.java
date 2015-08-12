package edu.jhu.JavaEE.shih.nathan.beans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.jhu.JavaEE.shih.nathan.dao.StudentLoginViaDataSource;

/**
 * This is the Login bean class that handles logins.
 * 
 * @author Nathan
 */
@ManagedBean
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String validateUsernamePassword() {
		
		FacesMessage facesMessage = null;
		HttpSession session = SessionBean.getSession();
		
		// get or set current login attempt via session variable
        int loginAttempt;
        if (session.getAttribute("loginAttempt") == null) {
        	loginAttempt = 1;
            session.setAttribute("loginAttempt", loginAttempt);            
        } else {
            loginAttempt = (int) session.getAttribute("loginAttempt");        
        }
        
        // check if loginAttempsAllowed has been exceeded
        if (loginAttempt > SessionBean.getLoginAttemptsAllowed()) {
        	session.invalidate();
        	
        	facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Max number of login attempts exceeded.", null);
        } else {
        	
        	// increment loginAttempts and set as session variable
            loginAttempt++;
            session.setAttribute("loginAttempt", loginAttempt);
            
			// get the URL for WLS and DataSourceName from web.xml
			String serverUrl = SessionBean.getServerUrl();
			String dataSourcenName = SessionBean.getDataSourceName();
			
			// query the Student table to perform login
			StudentLoginViaDataSource studentLogin = new StudentLoginViaDataSource(userId, password, serverUrl, dataSourcenName);
			ResultSet rs = studentLogin.login();
			
			try {
				if (rs.next()) {	
					String firstName = rs.getString("FIRST_NAME");
					String lastName = rs.getString("LAST_NAME");
					
					facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome to the site, " + firstName + " " + lastName, null); 
					// reset login attempts
					//session.removeAttribute("loginCount");
					session.setAttribute("userId", userId);
					session.setAttribute("firstName", firstName);
					session.setAttribute("lastName", lastName);
					session.setAttribute("message", facesMessage.getSummary());
					
					return "welcome.jsp";
				} else {
					facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Sorry, you don't have an account. You must register first.", null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// close connection
				studentLogin.closeConnection();
			}
			
			if (facesMessage == null) {
				facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null);	
			}
        }
			
		FacesContext.getCurrentInstance().addMessage("loginForm:messages", facesMessage);
		
		return "login";
	}
}