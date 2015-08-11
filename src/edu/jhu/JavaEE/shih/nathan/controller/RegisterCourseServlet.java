package edu.jhu.JavaEE.shih.nathan.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import weblogic.rmi.extensions.PortableRemoteObject;
import edu.jhu.JavaEE.shih.nathan.beans.Course;
import edu.jhu.JavaEE.shih.nathan.dao.RegistrationViaDataSource;

/**
 * Servlet implementation class RegisterCourseServlet
 */
public class RegisterCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private int courseCapacity;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterCourseServlet() {
        super();
        
    }

    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	// set the courseCapacity from the init parameter specified in web.xml
    	String paramValue = getInitParameter("courseCapacity");
    	this.courseCapacity = Integer.parseInt(paramValue);
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RegisterCourseServlet called.");
		
		HttpSession session = request.getSession();
		
		String message = "";
		String forwardDestination = "";
		
		String action = request.getParameter("registerButton");
		
		if (action.toLowerCase().contains("register")) {
			// get the URL for WLS and DataSourceName from the session variable passed in from the RegistrationControllerServlet
			String serverUrl = (String) session.getAttribute("serverUrl");
			String dataSourceName = (String) session.getAttribute("dataSourceName");
			
			// save courseid as a session variable for later use
			Course course = (Course) session.getAttribute("course");
			
			// send JMS message with the RegCourseTopic
			try {
				sendMessage(session);
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
			// attempt registration
			RegistrationViaDataSource registration = new RegistrationViaDataSource(course.getCourseId(), courseCapacity, serverUrl, dataSourceName);
			int result = registration.registerCourse();
			if (result > 0) {
				message = "You have been registered to " + course.toString();
			} else {
				message = "Sorry, the registration to this course has been closed based on availability.";
			}
			
			// close connection
			registration.closeConnection();
			
			forwardDestination = "/registrarcourse.jsp";
		} else if (action.toLowerCase().contains("cancel")) {
			System.out.println("Canceling registration.");
			forwardDestination = "/courses.jsp";
		}
			
		// return response to caller
		request.setAttribute("message", message);
		request.getRequestDispatcher(forwardDestination).forward(request, response);
	}
	
	@SuppressWarnings("unchecked")
	private void sendMessage(HttpSession session) throws NamingException, JMSException {
		
		String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
		String TOPIC = "RegCourseTopic";
		String JMS_FACTORY = "weblogic.jms.ConnectionFactory";
		
		@SuppressWarnings("rawtypes")
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		Context ctx = new InitialContext(env);
		TopicConnectionFactory tconFactory = (TopicConnectionFactory) PortableRemoteObject.narrow(ctx.lookup(JMS_FACTORY), TopicConnectionFactory.class);
		
		TopicConnection tcon = tconFactory.createTopicConnection();
		TopicSession tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topic = (Topic) PortableRemoteObject.narrow(ctx.lookup(TOPIC), Topic.class);
		
		TopicPublisher tpublisher = tsession.createPublisher(topic);
		TextMessage msg = tsession.createTextMessage();
		
		Course course = (Course) session.getAttribute("course");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String message = "User_ID: " + session.getAttribute("userId");
		message = message + "\nStudent_Name: " + session.getAttribute("firstName") + ", " + session.getAttribute("lastName");	
		message = message + "\nCourse_ID: " + course.getCourseId();
		message = message + "\nCourse_Name: " + course.getCourseName();
		message = message + "\nDate_of_Registration: " + dateFormat.format(cal.getTime());
		System.out.println("SENDING>>>>>>>>>>");
		System.out.println(message);
		
		tcon.start();
		msg.setText(message);
		tpublisher.publish(msg);
		tpublisher.close();
		tsession.close();
		tcon.close();
	}
}
