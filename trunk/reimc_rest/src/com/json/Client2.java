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
	 
			//String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
	 
	
			String input = "{\"deviceId\":\"ASDF1234\",\"appName\":\"EBookReader\",\"data\":\"[\"{\"timestamp\":\"123456789\",\"logType\":\"Battery\",\"logData\":\"10\"}]\"}";
			
			//String input = "{\"deviceId\":\"ASDF1234\",\"appName\":\"EBookReader\"}";
			
			Request aRequest = new Request();
			aRequest.setAppName("testing");
			aRequest.setDeviceId("deviceTesting");
			
			ArrayList<Data> list = new ArrayList<Data>();
			
			Data aData = new Data();
			aData.setLogData("RenData");
			aData.setLogType("RenDataType");
			aData.setTimestamp("111456789");
			
			Data bData = new Data();
			bData.setLogData("RenBlogData");
			bData.setLogType("RenBlogType");
			bData.setTimestamp("1113456789");
			
			list.add(aData);
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
