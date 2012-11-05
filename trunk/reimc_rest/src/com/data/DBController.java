package com.data;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.entity.Logentry;

public class DBController {

	private static final String PERSISTENCE_UNIT_NAME = "LogEntry";
	private static EntityManagerFactory factory;
	public EntityManager em;

	public DBController()
	{
		/*factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();

		System.out.println("EntityManager" + em.toString());*/

		

	}
	public String addLogEntry(String appName, String deviceID, String logType, String logValue, String phoneNum, String time)
	{
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();

		System.out.println("EntityManager" + em.toString());

		
		// Create new log entriy
	    em.getTransaction().begin();
	    Logentry aLogentry = new Logentry();
	    aLogentry.setAppName(appName);
	    aLogentry.setDeviceId(deviceID);
	    aLogentry.setLogDateTime(time);
	    aLogentry.setLogPhoneNum(phoneNum);
	    aLogentry.setLogType(logType);
	    aLogentry.setLogValue(logValue);
	    em.persist(aLogentry);
	    em.getTransaction().commit();
	    
	    System.out.println("Added="+aLogentry);
	    	
	    if(aLogentry.getLogEntryId() > 0)
	    	return "SUCCESS";
	    else
	    	return "ERROR";
	}
}
