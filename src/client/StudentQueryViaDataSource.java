package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * This client will use the DataSource object defined in WLS to obtain a connection to the 
 * JHU database and to print all records from table Student.
 *
 * @author Nathan Shih
 * @since Jun 12, 2015
 */
public class StudentQueryViaDataSource {

	private final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	private final static String serverUrl ="t3://localhost:7001";
	private static Connection con = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String ar[])throws Exception {
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
			DataSource ds = (DataSource) ic.lookup("jhuDataSource");
			con = ds.getConnection();
			
			// querying the DB
			PreparedStatement ps = con.prepareStatement("SELECT * FROM STUDENT");
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
		    System.out.println("querying SELECT * FROM Student");
		    System.out.println();
		    int columnsNumber = rsmd.getColumnCount();
		    
			// display results walking forward
		    System.out.println("Displaying results walking forward:");
		    while (rs.next()) {
		        for (int i = 1; i <= columnsNumber; i++) {
		            if (i > 1) System.out.print(",  ");
		            String columnValue = rs.getString(i);
		            System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
		        }
		        System.out.println("");
		    }
		}
		catch(Exception e) {
			System.err.println("Exception: "+e.getMessage());
		} finally {
			// close the connection
			if (con != null) {
				con.close();
			}
		}
	}
}
