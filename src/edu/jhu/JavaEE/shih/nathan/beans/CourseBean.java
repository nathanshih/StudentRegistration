package edu.jhu.JavaEE.shih.nathan.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is the persistent class for the COURSE database table.
 *
 * @author Nathan
 */
@Entity
@Table(name = "COURSES")
public class CourseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String courseId;
	private String courseName;
	
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
	
	@Column(name = "COURSE_NAME")
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
