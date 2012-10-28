package com.bookreader;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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
		return "POST call: Hello BookReader - i will handel log entries";
		
	}

}
