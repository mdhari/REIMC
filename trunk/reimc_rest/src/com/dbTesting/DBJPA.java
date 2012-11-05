package com.dbTesting;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.entity.Logentry;

public class DBJPA {
	 private static final String PERSISTENCE_UNIT_NAME = "LogEntry";
	 private static EntityManagerFactory factory;
	 public static EntityManager em;
	
	 
	 public static EntityManager getEntityManger()
	 {
		 factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		 em = factory.createEntityManager();
		 
		 System.out.println("EntityManager"+ em.toString());
		return em;
	 }
	 
	 
/*	public static void main(String[] args) {
		
		 factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		    EntityManager em = factory.createEntityManager();
		    
			getEntityManger();
		
		    
		    // Read the existing log entries
		    Query q = em.createQuery("select t from Logentry t");
		    List<Logentry> LogentryList = q.getResultList();
		    for (Logentry alogentry : LogentryList) {
		      System.out.println(alogentry);
		    }
		    System.out.println("Size: " + LogentryList.size());
		    
		    // Create new log entrie
		    em.getTransaction().begin();
		    Logentry aLogentry = new Logentry();
		    aLogentry.setAppName("bookreader");
		    aLogentry.setDeviceId("12345");
		    aLogentry.setLogDateTime("10/30/2012 01:01");
		    aLogentry.setLogPhoneNum("9252979434");
		    aLogentry.setLogType("BatteryUsage");
		    aLogentry.setLogValue("15");
		    em.persist(aLogentry);
		    em.getTransaction().commit();

		    System.out.println(aLogentry);
		    
		    em.close();
		  
		    
		  

	}*/

}
