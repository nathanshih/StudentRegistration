package edu.jhu.JavaEE.shih.nathan.beans;

import javax.ejb.Remote;

/**
 * Remote session bean to handle Status page.
 *
 * @author Nathan
 */
@Remote
public interface StatusFacade {

	public String getStatus(String courseId);
	
	public String getAllStatus();
}
