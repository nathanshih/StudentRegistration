package client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import domain.StudentInfo;

/**
 * This client will lookup all of these bounded objects using the names they have been bounded under.
 *
 * @author Nathan Shih
 * @since Jun 12, 2015
 */
public class StudentInfoLookup {

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
			// get all 3 StudentInfo objects and print them
			StudentInfo studentInfo1 = (StudentInfo) ic.lookup("StudentInfo1Name");
			System.out.println("Found StudentInfo object:");
			System.out.println(studentInfo1.toString());

			StudentInfo studentInfo2 = (StudentInfo) ic.lookup("StudentInfo2Name");
			System.out.println("Found StudentInfo object:");
			System.out.println(studentInfo2.toString());

			StudentInfo studentInfo3 = (StudentInfo) ic.lookup("StudentInfo3Name");
			System.out.println("Found StudentInfo object:");
			System.out.println(studentInfo3.toString());
		}
		catch(Exception e) {
			System.out.println("\n\n\t Unable To Bind => "+e);
			e.printStackTrace();
		}
	}
}
