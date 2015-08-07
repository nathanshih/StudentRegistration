package edu.jhu.JavaEE.shih.nathan.beans;

import javax.ejb.Local;

/**
 * Local session bean to handle Status page.
 *
 * @author Nathan
 */
@Local
public interface StatusFacadeLocal {

	public String getStatus(String courseId);
	
	public String getAllStatus();
}
