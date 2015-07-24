package edu.jhu.JavaEE.shih.nathan.beans;

import java.util.List;

import javax.ejb.Stateless;

import edu.jhu.JavaEE.shih.nathan.dao.CourseQueryViaDataSource;
import edu.jhu.JavaEE.shih.nathan.dao.RegistrationViaDataSource;

/**
 * EJB to handle course status.
 *
 * @author Nathan
 */
@Stateless
public class Status {

	private String serverUrl;
	private String dataSourceName;
	
	public Status() {
		
	}
	
	public Status(String serverUrl, String dataSourceName) {
		this();
		
		setServerUrl(serverUrl);
		setDataSourceName(dataSourceName);
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

	public String getStatus(int courseId) {
		
		CourseQueryViaDataSource courseQuery = new CourseQueryViaDataSource(serverUrl, dataSourceName);
		RegistrationViaDataSource registrarQuery = new RegistrationViaDataSource(courseId, serverUrl, dataSourceName);
		
		Course course = courseQuery.getCourseById(courseId);
		int numRegisteredStudents = registrarQuery.getNumberRegisteredStudents(courseId);
		
		String result = "<tr>";
		result = result + "<td>" + String.valueOf(course.getCourseId()) + "</td>";
		result = result + "<td>" + course.getCourseName() + "</td>";
		result = result + "<td>" + String.valueOf(numRegisteredStudents) + "</td>";
		result = result + "</tr>";
		
		return result;
	}
	
	public String getAllStatus() {
		
		CourseQueryViaDataSource courseQuery = new CourseQueryViaDataSource(serverUrl, dataSourceName);
		RegistrationViaDataSource registrarQuery = new RegistrationViaDataSource(serverUrl, dataSourceName);
		
		List<Course> courses = courseQuery.getAllCourses();
		
		String result = "";
		for (Course course : courses) {
			int numRegisteredStudents = registrarQuery.getNumberRegisteredStudents(course.getCourseId());
			
			result = result + "<tr>";
			result = result + "<td>" + String.valueOf(course.getCourseId()) + "</td>";
			result = result + "<td>" + course.getCourseName() + "</td>";
			result = result + "<td>" + String.valueOf(numRegisteredStudents) + "</td>";
			result = result + "</tr>";
		}
		
		return result;
	}
}
