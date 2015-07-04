package edu.jhu.JavaEE.shih.nathan.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionBean {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static String getServerUrl() {
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("serverUrl");
	}
	
	public static String getDataSourceName() {
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("dataSourceName");
	}
	
	public static int getLoginAttemptsAllowed() {
		String value = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("loginAttemptsAllowed");
		return Integer.parseInt(value);
	}
}
