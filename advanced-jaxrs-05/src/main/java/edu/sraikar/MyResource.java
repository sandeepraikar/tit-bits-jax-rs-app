package edu.sraikar;


import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("test")
public class MyResource {

	@GET
	@Produces(value= {MediaType.TEXT_PLAIN, "text/shortdate"})
	public Date testMethod(){
		return Calendar.getInstance().getTime();
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public String testPostMethod(String message){
		return "testing post";
	}
}
