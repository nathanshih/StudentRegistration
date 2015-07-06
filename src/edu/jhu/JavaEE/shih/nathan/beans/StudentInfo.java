package edu.jhu.JavaEE.shih.nathan.beans;

import java.io.Serializable;

/**
 * Domain object for the student information.
 *
 * @author Nathan Shih
 * @since Jun 12, 2015
 */
public class StudentInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String ssn;
	private String userId;
	private String password;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

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

	/**
	 * Call getter on each field and print the value.
	 */
	@Override
	public String toString() {
		
		return "First_Name: " + getFirstName() + "\n" 
			   + "Last_Name: " + getLastName() + "\n"
			   + "Address: " + getAddress() + "\n"
			   + "UserId: " + getUserId() + "\n"
			   + "Email: " + getEmail() + "\n";
	}
}
