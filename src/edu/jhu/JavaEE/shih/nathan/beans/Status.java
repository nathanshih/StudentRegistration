package edu.jhu.JavaEE.shih.nathan.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 * EJB to handle course status.
 *
 * @author Nathan
 */
@Stateless(mappedName = "StatusFacade")
public class Status implements StatusFacade, StatusFacadeLocal {

	@PersistenceUnit(unitName="module10")
	private EntityManagerFactory emf;
	private EntityManager em;
	private String result;
	
	public Status() {
		
	}
	
	public Status(String serverUrl, String dataSourceName) {
		this();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("module10");
		em = emf.createEntityManager();
		
		setResult("<table><tr><th>Course ID</th><th>Course Name</th><th>Number of students registered</th></tr>");
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus(String courseId) {
		
		CourseBean course = em.find(CourseBean.class, courseId);
		if (course != null) {

			RegistrarBean registrar = em.find(RegistrarBean.class, courseId);
			int numRegisteredStudents = registrar.getNumberStudentsRegistered();
			
			result = result + "<tr>";
			result = result + "<td>" + String.valueOf(course.getCourseId()) + "</td>";
			result = result + "<td>" + course.getCourseName() + "</td>";
			result = result + "<td>" + String.valueOf(numRegisteredStudents) + "</td>";
			result = result + "</tr>";
			result = result + "</table>";
		} else {
			result = "Course not found.";
		}
		
		return result;
	}
	
	public String getAllStatus() {
		
		@SuppressWarnings("unchecked")
		List<CourseBean> courses = em.createQuery("SELECT r FROM CourseBean r").getResultList();
		
		for (CourseBean course : courses) {
			
			RegistrarBean registrar = em.find(RegistrarBean.class, course.getCourseId());
			int numRegisteredStudents = registrar.getNumberStudentsRegistered();
			
			result = result + "<tr>";
			result = result + "<td>" + String.valueOf(course.getCourseId()) + "</td>";
			result = result + "<td>" + course.getCourseName() + "</td>";
			result = result + "<td>" + String.valueOf(numRegisteredStudents) + "</td>";
			result = result + "</tr>";	
		}
		result = result + "</table>";
		
		return result;
	}
}
