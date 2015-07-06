package edu.jhu.JavaEE.shih.nathan.beans;

import java.io.Serializable;

/**
 * Domain object to support the course registration business logic flow.
 * This is the RegistrationSupportBean.java per assignment requirements.
 *
 * @author Nathan Shih
 * @since Jun 19, 2015
 */
public class RegistrationSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int courseId;
	private int numberStudentsRegistered;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public int getNumberStudentsRegistered() {
		return numberStudentsRegistered;
	}

	public String toString() {
		return "CourseID: " + courseId + ". Number of students registered: " + numberStudentsRegistered;
	}
}
