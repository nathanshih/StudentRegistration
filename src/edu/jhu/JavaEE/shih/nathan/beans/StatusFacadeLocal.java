package edu.jhu.JavaEE.shih.nathan.beans;

import javax.ejb.Local;

/**
 * Local session bean to handle Status page.
 *
 * @author Nathan
 */
@Local
public interface StatusFacadeLocal {

	/**
	 * Get the registration status for a course.
	 * 
	 * @param courseId
	 * @return
	 */
	public String getStatus(String courseId);
	
	/**
	 * Get the registration status for all courses.
	 * 
	 * @return
	 */
	public String getAllStatus();
}
