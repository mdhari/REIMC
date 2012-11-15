package com.json;

import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class Client2 {

	public static void main(String[] args) {
		
		try {
			 
			Client client = Client.create();
	 
			WebResource webResource = client
			   .resource("http://localhost:8080/reimc_rest/bookreader/br/json");
	 
			Request aRequest = new Request();
			aRequest.setAppName("bookreader");
			aRequest.setDeviceId("device123");
			
			ArrayList<Data> list = new ArrayList<Data>();
			
			Data aData = new Data();
			aData.setLogData("10");
			aData.setLogType("battery");
			aData.setTimestamp("1352948897020");
			list.add(aData);
			
			Data bData = new Data();
			bData.setLogData("5");
			bData.setLogType("battery");
			bData.setTimestamp("1352948897020");
			list.add(bData);
			
			aRequest.setData(list);
			
			ObjectMapper mapper = new ObjectMapper();
			
			StringWriter  sw = new StringWriter ();
			
			mapper.writeValue(sw, aRequest );
			
			System.out.println(sw.toString());
			
			
			/*//System.out.println(input);
			ObjectMapper mapper = new ObjectMapper();
			//mapper.writeValue(null, aRequest);

			*/
			
			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, sw.toString());
	 
			
			/*if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}*/
	 
			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
	 
			
			
		/*	Request aRequestt = mapper.readValue(input, Request.class);
			
			System.out.println(aRequestt.getAppName() + aRequestt.getDeviceId());
			*/
			
			
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
		
	}
	
	
}
