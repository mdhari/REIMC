package com.client;

import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/*import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;*/
import com.sun.jersey.api.representation.Form;

public class LogClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*		 ClientConfig config = new DefaultClientConfig();
		    Client client = Client.create(config);
		    WebResource service = client.resource(getBaseURI());

		    HashMap <String, String> logInfo = new HashMap<String, String>();
		    logInfo.put("APPNAME", "BookReader");
		    logInfo.put("PHONE", "galexyPhone");
		    logInfo.put("API", "BatteryStatusUpdate");
		    logInfo.put("LEVEL", "10");
		    logInfo.put("TIME", "01/02/2012 11:15:30");
		    
		    //Add log entry
		    ClientResponse response = service.path("log").path("addlogEntry").put(ClientResponse.class, logInfo);
		    System.out.println(response.getStatus());
		    */
		    //System.out.println(service.path("log").path("addlogEntry"));
		   // System.out.println(service.path("log").path("addlogEntry").accept(MediaType.TEXT_XML).get(String.class));
	}

	 private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://localhost:8080/TestingWebProj/").build();
		  }
	 
}
