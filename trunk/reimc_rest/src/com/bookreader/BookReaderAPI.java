package com.bookreader;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.data.DBController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.Request;

@Path("/br")
public class BookReaderAPI {

	
	@Path("/log")
	@GET
	@Produces({  MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String sayPlainTextHello() {
		System.out.println("Hello BookReader - i will handel log entries");
		return "GET Call: Hello BookReader - i will handel log entries";
	}
	
	//String appName, String deviceID, String logType, String logValue, String phoneNum, String time
	
	@Path("/log")
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String newLogEntry(
			@FormParam("phoneNum") String phoneNum,
			@FormParam("logType") String logType,
			@FormParam("logValue") String logValue,
			@FormParam("deviceId") String deviceId,
			@FormParam("timeStamp") String timeStamp,
			@FormParam("appName") String appName
			
			) throws IOException
	{
		System.out.println("POST call: phoneNum" + phoneNum + "logType" + logType + "logValue" + logValue );
		DBController dbController = new DBController();
		
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();*/
		
		// dateFormat.format(date)
		String temp = dbController.addLogEntry(appName, deviceId, logType, logValue, phoneNum , timeStamp);
		return "POST call: Hello BookReader - i will handel log entries ="+ temp;
		
	}

	
	@Path("/json")
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public String newLogEntry( String input ) throws JsonParseException, JsonMappingException, IOException 
	{
		ObjectMapper mapper = new ObjectMapper();
		Request aRequest = mapper.readValue(input, Request.class);
		
		System.out.println(aRequest.getAppName() + aRequest.getDeviceId() + aRequest.getData().size());
		
		System.out.println("POST JSON call: AppName" + aRequest.getDeviceId() + "Data Length" + aRequest.getData().size());
		DBController dbController = new DBController();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		String temp = dbController.addRequestwDataToDB(aRequest);
		
		return temp;
		
		// dateFormat.format(date)
		//String temp = dbController.addLogEntry(appName, deviceId, logType, logValue, phoneNum , timeStamp);*/
		//String temp = dbController.addLogEntry(appName, deviceId, logType, logValue, phoneNum , timeStamp);
		//return "POST call: Hello BookReader - i will handel log entries =";
		
	}
}
