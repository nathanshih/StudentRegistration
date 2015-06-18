package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * This client prints out the full contents of the STUDENT table using ResultSet class, 
 * first off by "walking forward" and then by "walking backward".
 *
 * @author Nathan Shih
 * @since Jun 11, 2015
 */
public class StudentQuery {

	private static Connection con = null;
	
	public static void main(String[] args) throws SQLException {
		
		try {
			con = DriverManager.getConnection("jdbc:derby:/Users/nshih/Documents/JHU;create=true");
			
			// create the SQL statement
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Student", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
		    
		    // display results walking backward
		    System.out.println();
		    System.out.println("Displaying results walking backward:");
		    rs.afterLast();
		    while (rs.previous()) {
		    	for (int i = 1; i <= columnsNumber; i++) {
		            if (i > 1) System.out.print(",  ");
		            String columnValue = rs.getString(i);
		            System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
		        }
		        System.out.println("");
		    }
			
		} catch (Exception e) {
			System.err.println("Exception: "+e.getMessage());
		} finally {
			// close the connection
			if (con != null) {
				con.close();
			}
		}
	}
}
