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

@Path("/br")
public class BookReaderAPI {

	
	@Path("/log")
	@GET
	@Produces({  MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String sayPlainTextHello() {
		System.out.println("Hello BookReader - i will handel log entries");
		return "GET Call: Hello BookReader - i will handel log entries";
	}
	
	@Path("/log")
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String newLogEntry(@FormParam("phoneNum") String phoneNum,
			@FormParam("logType") String logType,
			@FormParam("logValue") String logValue
			) throws IOException
	{
		System.out.println("POST call: phoneNum" + phoneNum + "logType" + logType + "logValue" + logValue );
		DBController dbController = new DBController();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		
		String temp = dbController.addLogEntry("bookreader", logType, logValue, phoneNum , dateFormat.format(date));
		 //addLogEntry(String appName, String logType, String logValue, String time, String phoneNum)
		return "POST call: Hello BookReader - i will handel log entries ="+ temp;
		
	}

}
