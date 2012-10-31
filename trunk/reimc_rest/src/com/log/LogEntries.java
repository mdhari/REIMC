package com.log;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/addlogEntry")
public class LogEntries {

	@GET
	@Produces({  MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String sayPlainTextHello() {
		return "Hello Jersey - i will handel log entries";
	}

//	@PUT
//	@Produces({  MediaType.TEXT_HTML,MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	public String putType1(@PathParam("appName") String appName, @QueryParam("BatteryLevel") String BatteryLevel ) {
//		System.out.println("WE got something putType1" + "appName" + appName  + "BatteryLevel" + BatteryLevel);
//		return "SUCCESS";
//	}
//
//	
//	@PUT
//	@Produces({  MediaType.TEXT_HTML,MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	public String putType2(@QueryParam("appName") String appName, @QueryParam("BatteryLevel") String BatteryLevel ) {
//		System.out.println("WE got something putType2" + appName + "," + BatteryLevel);
//		return "SUCCESS";
//	}
	
	
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newLogEntry(@FormParam("id") String id,
			@FormParam("summary") String summary,
			@FormParam("description") String description) throws IOException
	{
		System.out.println("id" + id + "summary" + summary + "description" + description );
		
		/*Todo todo = new Todo(id, summary);
		if (description != null) {
			todo.setDescription(description);
		}
		TodoDao.instance.getModel().put(id, todo);

		servletResponse.sendRedirect("../create_todo.html");*/
	}
	
}
