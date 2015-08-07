package edu.jhu.JavaEE.shih.nathan.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is the persistent class for the REGISTRAR database table.
 *
 * @author Nathan
 */
@Entity
@Table(name = "REGISTRAR")
public class RegistrarBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String courseId;
	private int numberStudentsRegistered;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Id
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Column(name = "NUMBER_STUDENTS_REGISTERED")
	public int getNumberStudentsRegistered() {
		return numberStudentsRegistered;
	}
	
	public void setNumberStudentsRegistered(int numberStudentsRegistered) {
		this.numberStudentsRegistered = numberStudentsRegistered;
	}
	
	public String toString() {
		return "CourseID: " + courseId + ". Number of students registered: " + numberStudentsRegistered;
	}
}
