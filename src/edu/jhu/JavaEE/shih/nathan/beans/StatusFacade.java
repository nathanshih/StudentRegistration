package edu.jhu.JavaEE.shih.nathan.beans;

import javax.ejb.Remote;

/**
 * Remote session bean to handle Status page.
 *
 * @author Nathan
 */
@Remote
public interface StatusFacade {

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
