/**
 * 
 */
package edu.jhu.JavaEE.shih.nathan.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Nathan
 *
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
		System.out.println("UserID: " + getUserId());
		System.out.println("Password: " + getPassword());
		
		return "login";
	}
}