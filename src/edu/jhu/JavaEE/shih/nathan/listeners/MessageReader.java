package edu.jhu.JavaEE.shih.nathan.listeners;

import java.util.*;
import javax.naming.*;
import javax.jms.*;
import javax.rmi.PortableRemoteObject;

/**
 * Message reader to display messages sent to RegCourseTopic
 *
 * @author Nathan
 */
public class MessageReader implements MessageListener {

	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	public final static String connectionFactory = "weblogic.jms.ConnectionFactory";
	public final static String topicName = "RegCourseTopic";
	private TopicConnectionFactory tconFactory;
	private TopicSubscriber tsubscriber;
	private TopicConnection tcon;
	private TopicSession tsession;
	private Topic topic;
	private boolean quit = false;
	
	@Override
	public void onMessage(Message msg) {
		try {
			String msgText;
			if (msg instanceof TextMessage) {
				msgText = ((TextMessage) msg).getText();
			} else {
				msgText = msg.toString();
			}
			
			System.out.println(">>>>>>>>>>JMS Message Received:");
			System.out.println(msgText);
			System.out.println();
			
			if (msgText.equalsIgnoreCase("quit")) {
				synchronized(this) {
					quit = true;
					this.notifyAll(); // Notify main thread to quit
				}
			}
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		MessageReader mr = new MessageReader();
		InitialContext ic = getInitialContext("t3://localhost:7001");
		mr.init(ic, topicName);
		System.out.println("JMS consumer is ready to receive messages.");
		
		// go until quit event happens
		synchronized(mr) {
			while (!mr.quit) {
				try {
					mr.wait();
				} catch (InterruptedException ie) {
					
				}
			}
		}
		mr.close();
	}

	public void init(Context ctx, String topicName) throws NamingException, JMSException {
		tconFactory = (TopicConnectionFactory) PortableRemoteObject.narrow(ctx.lookup(connectionFactory), TopicConnectionFactory.class);
		tcon = tconFactory.createTopicConnection();
		tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		topic = (Topic) PortableRemoteObject.narrow(ctx.lookup(topicName), Topic.class);
		tsubscriber = tsession.createSubscriber(topic);
		tsubscriber.setMessageListener(this);
		tcon.start();
	}
	
	public void close() throws JMSException {
		tsubscriber.close();
		tsession.close();
		tcon.close();
	}
	
	@SuppressWarnings("unchecked")
	private static InitialContext getInitialContext(String url) throws NamingException {
		@SuppressWarnings("rawtypes")
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, url);
		env.put("weblogic.jndi.createIntermediateContexts", "true");
		return new InitialContext(env);
	}
}
