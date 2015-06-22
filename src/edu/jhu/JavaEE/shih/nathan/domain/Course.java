package domain;

import java.io.Serializable;

/**
 * Domain object to support course business logic flow. This is the CourseSupportBean.java
 * per assignment requirements.
 * 
 * @author Nathan Shih
 * @since Jun 19, 2015
 */
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int courseId;
	private String courseName;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String toString() {
		return courseId + " " + courseName;
	}
}
