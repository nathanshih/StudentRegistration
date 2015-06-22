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
 * JHU database to query the Student table given the userId and password.
 *
 * @author Nathan Shih
 * @since Jun 12, 2015
 */
public class StudentLoginViaDataSource {

	private final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	
	private String serverUrl;
	private String dataSourceName;
	private Connection con;	
	private String userId;
	private String password;
	private ResultSet rs;
	
	/**
	 * Create the StudentLoginViaDataSource object with required parameters.
	 * 
	 * @param userId the userId to login with
	 * @param password the password associated with the userId
	 * @param serverUrl the URL of the WLS server
	 * @param dataSourceName the DataSourceName
	 */
	public StudentLoginViaDataSource(String userId, String password, String serverUrl, String dataSourceName) {
		setUserId(userId);
		setPassword(password);
		setServerUrl(serverUrl);
		setDataSourceName(dataSourceName);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public StudentLoginViaDataSource setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
		
		return this;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public StudentLoginViaDataSource setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
		
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public StudentLoginViaDataSource setUserId(String userId) {
		this.userId = userId;
		
		return this;
	}

	public String getPassword() {
		return password;
	}
	
	public ResultSet getResultSet() {
		return rs;
	}

	public StudentLoginViaDataSource setPassword(String password) {
		this.password = password;
		
		return this;
	}

	/**
	 * Attempt to login given the userId and password using the DataSource object.
	 *
	 * @return the ResultSet object containing any records found
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResultSet login() {
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
			String sqlQuery = "SELECT * FROM Student WHERE userid=? and password=?";
			PreparedStatement ps = con.prepareStatement(sqlQuery);
			ps.setString(1, userId);
			ps.setString(2, password);
			rs = ps.executeQuery();
		}
		catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		
		return rs;
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
