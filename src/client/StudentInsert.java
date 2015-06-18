package client;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * This client connects to the JHU database and inserts all the rows of 
 * data from the input file into STUDENT table.
 *
 * @author Nathan Shih
 * @since Jun 11, 2015
 */
public class StudentInsert {

	private static Connection con = null;

	public static void main(String[] args) throws SQLException {

		try {
			con = DriverManager.getConnection("jdbc:derby:/Users/nshih/Documents/JHU;create=true");
			
			// read from file
			File studentData = new File("./resources/student_data.txt");
			if (studentData.exists()) {
				Scanner scanner = new Scanner(studentData);
				
				// read line by line
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					System.out.println("Next line to insert: " + line);
					String[] values = line.split(" ");
					
					// create the SQL string to insert
					String sql = "INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStatement = con.prepareStatement(sql);
					for (int i = 1; i < values.length + 1; i++) {
						preparedStatement.setString(i, values[i - 1]);
					}
					
					// executing SQL statement
					System.out.println("Executing SQL statement.");
					preparedStatement.executeUpdate();
					preparedStatement.close();
				}

				scanner.close();

			} else {
				System.err.println("File not found!");
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
