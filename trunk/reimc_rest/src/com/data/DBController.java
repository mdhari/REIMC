package com.data;


import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.entity.Logentry;
import com.json.Data;
import com.json.Request;

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
	
	public String addRequestwData(Request aRequest)
	{
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
		
		System.out.println("EntityManager" + em.toString());
		
		em.getTransaction().begin();
		
		em.persist(aRequest);
		
		em.getTransaction().commit();
	    
	    System.out.println("Added="+aRequest);
	    
	    return "SUUCESS";
		
	}
	
/*	String deviceId;
	String appName;
	
	ArrayList<Data> data;
*/
	//Data
	/*String timestamp;
	String logType;
	String logData;
	*/
	public String addRequestwDataToDB(Request aRequest)
	{
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
		
		System.out.println("EntityManager" + em.toString());
		
		
		
		ArrayList<Data> listOfData = new ArrayList<Data>();
		
		
		listOfData = aRequest.getData();
		
		Logentry logEntry ;
		Iterator it = listOfData.iterator();
		
		while(it.hasNext())
		{
			em.getTransaction().begin();

			logEntry = new Logentry();
			Data aData = (Data)it.next();
			
			logEntry.setAppName(aRequest.getAppName());
			logEntry.setDeviceId(aRequest.getDeviceId());
			
			logEntry.setLogDateTime(aData.getTimestamp());
			logEntry.setLogType(aData.getLogType());
			logEntry.setLogValue(aData.getLogData());
		
			em.persist(logEntry);
			
			em.getTransaction().commit();
		    
		    System.out.println("Added="+ logEntry);
		}
		
		return "SUUCESS";
		
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
