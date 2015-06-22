package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import domain.Course;

/**
 * This class will use the DataSource object defined in WLS to obtain a connection to the 
 * JHU database to query the Courses table.
 *
 * @author Nathan Shih
 * @since Jun 19, 2015
 */
public class CourseQueryViaDataSource {

	private final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	
	private String serverUrl;
	private String dataSourceName;
	private Connection con;	

	public CourseQueryViaDataSource(String serverUrl, String dataSourceName) {
		setServerUrl(serverUrl);
		setDataSourceName(dataSourceName);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public CourseQueryViaDataSource setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
		
		return this;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public CourseQueryViaDataSource setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
		
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Course> getAllCourses() {
		
		List<Course> courses = new ArrayList<Course>();
		
		InitialContext ic = null;
		try {
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
			env.put(Context.PROVIDER_URL, serverUrl);
			env.put(Context.SECURITY_PRINCIPAL,"nathanshih");
			env.put(Context.SECURITY_CREDENTIALS,"password1");
			ic = new InitialContext(env);
		}
		catch(Exception e) {
			System.out.println("\n\n\t Unable To Get The InitialContext => "+e);
		}

		try {
			// connecting to the data source
			DataSource ds = (DataSource) ic.lookup(dataSourceName);
			con = ds.getConnection();
			
			// querying the DB
			String sqlQuery = "SELECT * FROM Courses";
			PreparedStatement ps = con.prepareStatement(sqlQuery);
					
			System.out.println("Executing: " + sqlQuery);
			ResultSet rs = ps.executeQuery();
			
			// put results into List to return
			while(rs.next()) {
				Course course = new Course();
				
				course.setCourseId(rs.getInt("courseid"));
				course.setCourseName(rs.getString("course_name"));
				
				System.out.println("Adding course: " + course.toString());
				courses.add(course);
			}
		}
		catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		
		return courses;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Course getCourseById(int courseId) {
		
		InitialContext ic = null;
		try {
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
			env.put(Context.PROVIDER_URL, serverUrl);
			env.put(Context.SECURITY_PRINCIPAL,"nathanshih");
			env.put(Context.SECURITY_CREDENTIALS,"password1");
			ic = new InitialContext(env);
		}
		catch(Exception e) {
			System.out.println("\n\n\t Unable To Get The InitialContext => "+e);
		}

		Course course = null;
		try {
			// connecting to the data source
			DataSource ds = (DataSource) ic.lookup(dataSourceName);
			con = ds.getConnection();
			
			// querying the DB
			String sqlQuery = "SELECT * FROM Courses WHERE courseid=?";
			PreparedStatement ps = con.prepareStatement(sqlQuery);
			ps.setInt(1, courseId);
			System.out.println("Querying Courses table for courseid " + courseId);
			ResultSet rs = ps.executeQuery();
			
			// return results, if found otherwise null
			if (rs.next()) {
				course = new Course();
				
				course.setCourseId(rs.getInt("courseid"));
				course.setCourseName(rs.getString("course_name"));
				
				System.out.println("Course found: " + course.toString());
			}
		}
		catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		
		return course;
	}
	
	/**
	 * Close the connection manually.
	 */
	public void closeConnection() {
		// close the connection
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				System.err.println("Exception: " + e.getMessage());
			}
		}
	}
}
