package com.data;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.json.JSONArray;

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
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
		Calendar calendar;
		
		ArrayList<Data> listOfData = new ArrayList<Data>();
		
		
		listOfData = aRequest.getData();
		
		Logentry logEntry ;
		Iterator it = listOfData.iterator();
		
		while(it.hasNext())
		{
			//calendar  = Calendar.getInstance();
			em.getTransaction().begin();

			logEntry = new Logentry();
			Data aData = (Data)it.next();
			
			logEntry.setAppName(aRequest.getAppName());
			logEntry.setDeviceId(aRequest.getDeviceId());
			//System.out.println("milisec" + aData.getTimestamp().toString() );
			//calendar.setTimeInMillis(Long.parseLong(aData.getTimestamp().toString()));
			 
			//logEntry.setLogDateTime(sdf.format(calendar.getTime()));
			logEntry.setLogDateTime(aData.getTimestamp().toString());
			logEntry.setLogType(aData.getLogType());
			logEntry.setLogValue(aData.getLogData());
		
			em.persist(logEntry);
			
			em.getTransaction().commit();
		    
		    System.out.println("Added="+ logEntry);
		}
		
		return "SUUCESS";
		
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getLogForDeviceId(String deviceId){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
		
		Query q = em.createQuery("select x from Logentry x");
		List<Logentry> results = (List<Logentry>) q.getResultList ();
		
		JSONArray jsonArray = new JSONArray();
		
		for(Logentry logEntry:results){
			JSONArray subJSONArray = new JSONArray();
			subJSONArray.put(Long.parseLong(logEntry.getLogDateTime()));
			subJSONArray.put(Long.parseLong(logEntry.getLogValue()));
			jsonArray.put(subJSONArray);
		}
		
		return jsonArray;
		
	}
	
	@SuppressWarnings("unchecked")
	public String getRawLogForDeviceId(String deviceId){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
		
		Query q = em.createQuery("select x from Logentry x");
		List<Logentry> results = (List<Logentry>) q.getResultList ();
		
		String retVal = "";
		for(Logentry logEntry:results){
			retVal += logEntry.getLogDateTime() + "," + logEntry.getLogValue() + "\n";
		}
		
		return retVal;
		
	}
	
	@SuppressWarnings("unchecked")
	public String getCleanRawLogForDeviceId(String deviceId){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
		
		Query q = em.createQuery("select x from Logentry x");
		List<Logentry> results = (List<Logentry>) q.getResultList ();
		
		Long zero = Long.parseLong((results.get(0).getLogDateTime()));
		int batteryLevel = Integer.parseInt(results.get(0).getLogValue());
		Long lastDateTime = Long.parseLong((results.get(0).getLogDateTime()));
		
		String retVal = "";
//		for(Logentry logEntry:results){
//			if(batteryLevel < Integer.parseInt(logEntry.getLogValue())){
//				// must of been charged,reset
//				batteryLevel = Integer.parseInt(logEntry.getLogValue());
//				zero = Long.parseLong(logEntry.getLogDateTime());
//			}
//			
//			if(batteryLevel > Integer.parseInt(logEntry.getLogValue())){
//				retVal += lastDateTime-zero + "," + batteryLevel + "\n";
//				batteryLevel = Integer.parseInt(logEntry.getLogValue());
//				
//			}
//			
//			lastDateTime = Long.parseLong(logEntry.getLogDateTime());
//		}
		
		for(Logentry logEntry:results){
			if(Integer.parseInt(logEntry.getLogValue()) == 100){
				zero = Long.parseLong(logEntry.getLogDateTime());
				batteryLevel = Integer.parseInt(logEntry.getLogValue());
			}
			
			if(batteryLevel > Integer.parseInt(logEntry.getLogValue())){
			retVal += lastDateTime-zero + "," + batteryLevel + "\n";
			batteryLevel = Integer.parseInt(logEntry.getLogValue());
			
		}
		
		lastDateTime = Long.parseLong(logEntry.getLogDateTime());
			
		}
		
		return retVal;
		
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
