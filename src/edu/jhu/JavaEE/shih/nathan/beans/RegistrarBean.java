package edu.jhu.JavaEE.shih.nathan.beans;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * This is the persistent class for the REGISTRAR database table.
 *
 * @author Nathan
 */
public class RegistrarBean implements Serializable {

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
	
	@Column(name = "NUMBER_STUDENTS_REGISTERED")
	public int getNumberStudentsRegistered() {
		return numberStudentsRegistered;
	}

	public String toString() {
		return "CourseID: " + courseId + ". Number of students registered: " + numberStudentsRegistered;
	}
}
