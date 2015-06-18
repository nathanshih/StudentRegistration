package client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import domain.StudentInfo;

/**
 * This client will bind three StudentInfo objects into the WebLogic Server JNDI naming service.
 *
 * @author Nathan Shih
 * @since Jun 12, 2015
 */
public class StudentInfoBinding {
	
	private final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	private final static String serverUrl ="t3://localhost:7001";

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
			// bind 1 StudentInfo object
			StudentInfo studentInfo1 = new StudentInfo();
			studentInfo1.setFirstName("Joe");
			studentInfo1.setLastName("Dirt");
			studentInfo1.setEmail("joedirt@email.com");
			studentInfo1.setAddress("Joe Dirt ave ne");
			//studentInfo1.setPhone("1112223333");
			ic.bind("StudentInfo1Name", studentInfo1);
			System.out.println("\n\n\t StudentInfo Object Binded in the JNDI Tree Of Server Suuccessfully");
			System.out.println("\n\n\t You can use (StudentInfo)ic.lookup(\"StudentInfo1Name\");");
			System.out.println("\t To Lookup the StudentInfo Object from the JNDI Tree");
			
			// bind 2 StudentInfo object
			StudentInfo studentInfo2 = new StudentInfo();
			studentInfo2.setFirstName("Max");
			studentInfo2.setLastName("Payne");
			studentInfo2.setEmail("maxpayne@email.com");
			studentInfo2.setAddress("Max Payne ave ne");
			//studentInfo2.setPhone("4449991111");
			ic.bind("StudentInfo2Name", studentInfo2);
			System.out.println("\n\n\t StudentInfo Object Binded in the JNDI Tree Of Server Suuccessfully");
			System.out.println("\n\n\t You can use (StudentInfo)ic.lookup(\"StudentInfo2Name\");");
			System.out.println("\t To Lookup the StudentInfo Object from the JNDI Tree");
			
			// bind 3 StudentInfo object
			StudentInfo studentInfo3 = new StudentInfo();
			studentInfo3.setFirstName("Jason");
			studentInfo3.setLastName("Youngblood");
			studentInfo3.setEmail("jasonyoungblood@email.com");
			studentInfo3.setAddress("Jason Youngblood ave ne");
			//studentInfo3.setPhone("4565679304");
			ic.bind("StudentInfo3Name", studentInfo3);
			System.out.println("\n\n\t StudentInfo Object Binded in the JNDI Tree Of Server Suuccessfully");
			System.out.println("\n\n\t You can use (StudentInfo)ic.lookup(\"StudentInfo3Name\");");
			System.out.println("\t To Lookup the StudentInfo Object from the JNDI Tree");
		}
		catch(Exception e) {
			System.out.println("\n\n\t Unable To Bind => "+e);
			e.printStackTrace();
		}
	}
}
