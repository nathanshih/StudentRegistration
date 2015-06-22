package edu.jhu.JavaEE.shih.nathan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import edu.jhu.JavaEE.shih.nathan.domain.StudentInfo;

/**
 * This class will use the DataSource object defined in WLS to obtain a connection to the 
 * JHU database to insert into the Student table given a StudentInfo object.
 *
 * @author Nathan Shih
 * @since Jun 12, 2015
 */
public class StudentInsertViaDataSource {

	private final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	
	private String serverUrl;
	private String dataSourceName;
	private Connection con;	
	private StudentInfo studentInfo;
	
	/**
	 * Create the StudentInsertViaDataSource object with required parameters.
	 * 
	 * @param studentInfo the studentInfo object to insert
	 * @param serverUrl the URL of the WLS server
	 * @param dataSourceName the DataSourceName
	 */
	public StudentInsertViaDataSource(StudentInfo studentInfo, String serverUrl, String dataSourceName) {
		setStudentInfo(studentInfo);
		setServerUrl(serverUrl);
		setDataSourceName(dataSourceName);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public StudentInsertViaDataSource setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
		
		return this;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public StudentInsertViaDataSource setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
		
		return this;
	}
	
	public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	/**
	 * Attempt to login given the userId and password using the DataSource object.
	 *
	 * @return the ResultSet object containing any records found
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int insert() {
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

		int rowCount = 0;
		try {
			// connecting to the data source
			DataSource ds = (DataSource) ic.lookup(dataSourceName);
			con = ds.getConnection();
			
			// querying the DB
			String sqlQuery = "INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sqlQuery);
			ps.setString(1, studentInfo.getFirstName());
			ps.setString(2, studentInfo.getLastName());
			ps.setString(3, studentInfo.getSsn());
			ps.setString(4, studentInfo.getEmail());
			ps.setString(5, studentInfo.getAddress());
			ps.setString(6, studentInfo.getUserId());
			ps.setString(7, studentInfo.getPassword());
						
			rowCount = ps.executeUpdate();
		}
		catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		
		return rowCount;
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
