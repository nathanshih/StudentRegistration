package edu.jhu.JavaEE.shih.nathan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
/**
 * This class will use the DataSource object defined in WLS to obtain a connection to the 
 * JHU database to register a course by writing to the Registrar table.
 *
 * @author Nathan Shih
 * @since Jun 19, 2015
 */
public class RegistrationViaDataSource {

	private final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	
	private String serverUrl;
	private String dataSourceName;
	private Connection con;
	private int courseId;
	private int courseCapacity;

	public RegistrationViaDataSource(String serverUrl, String dataSourceName) {
		this(0, serverUrl, dataSourceName);
	}
	
	public RegistrationViaDataSource(int courseId, String serverUrl, String dataSourceName) {
		this(courseId, 0, serverUrl, dataSourceName);
	}
	
	public RegistrationViaDataSource(int courseId, int courseCapacity, String serverUrl, String dataSourceName) {
		setCourseId(courseId);
		setCourseCapacity(courseCapacity);
		setServerUrl(serverUrl);
		setDataSourceName(dataSourceName);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public RegistrationViaDataSource setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
		
		return this;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public RegistrationViaDataSource setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
		
		return this;
	}

	public int getCourseId() {
		return courseId;
	}

	public RegistrationViaDataSource setCourseId(int courseId) {
		this.courseId = courseId;
		
		return this;
	}
	
	public int getCourseCapacity() {
		return courseCapacity;
	}
	
	public RegistrationViaDataSource setCourseCapacity(int courseCapacity) {
		this.courseCapacity = courseCapacity;
		
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	public int registerCourse() {
		
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
			
			// check if courseId already exists
			String sqlQuery = "SELECT number_students_registered FROM Registrar WHERE courseid=?";
			PreparedStatement ps = con.prepareStatement(sqlQuery);
			ps.setInt(1, courseId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// record already exists so do an update
				int numberStudentsRegistered = rs.getInt("number_students_registered");	
				System.out.println("Registrar record found, number of students currently registered: " + numberStudentsRegistered);
				if (numberStudentsRegistered < courseCapacity) {
					numberStudentsRegistered++;
					sqlQuery = "UPDATE Registrar SET number_students_registered=? WHERE courseid=?";
					ps = con.prepareStatement(sqlQuery);
					ps.setInt(1, numberStudentsRegistered);
					ps.setInt(2, courseId);
					System.out.println("Updating number of registered students.");
				} else { // course capacity reached
					System.out.println("Course capacity reached: " + courseCapacity);
					return 0;
				}
			} else {
				// record does not exist therefore do an insert
				sqlQuery = "INSERT INTO Registrar VALUES (?,?)";
				ps = con.prepareStatement(sqlQuery);
				ps.setInt(1, courseId);
				ps.setInt(2, 1);
				System.out.println("Adding first student to course.");
			}
			
			return ps.executeUpdate();
		}
		catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		
		return 0;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getNumberRegisteredStudents(int courseId) {
				
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

		int numberStudentsRegistered = 0;
		
		try {
			// connecting to the data source
			DataSource ds = (DataSource) ic.lookup(dataSourceName);
			con = ds.getConnection();
			
			// check if courseId already exists
			String sqlQuery = "SELECT number_students_registered FROM Registrar WHERE courseid=?";
			PreparedStatement ps = con.prepareStatement(sqlQuery);
			ps.setInt(1, courseId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// record found
				numberStudentsRegistered = rs.getInt("number_students_registered");	
				System.out.println("Registrar record found, number of students currently registered: " + numberStudentsRegistered);
			}
		}
		catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		
		return numberStudentsRegistered;
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
