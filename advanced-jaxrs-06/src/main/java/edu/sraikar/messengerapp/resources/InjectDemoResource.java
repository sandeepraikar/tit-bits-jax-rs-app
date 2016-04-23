package edu.sraikar.messengerapp.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	//Note @MatrixParam uses ';' insead of '?'
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam ("param") String matrixParam,
											@HeaderParam("sampleHeader") String header,
											@CookieParam("cname") String cookie){
											
		return "Matrix param: "+ matrixParam+" customHeader:  "+header + " Cookie: "+cookie ;
	}
	
	//@ Context UriInfo is for accessing info abt uri, etc..
	//@ Context HttpHeaders provides us the handle on all the headers which the resource is serving
	/*
	 * Exanple: from the above method getParamUsingAnnotations(args..)
	 * The user need not know about the names of all the param attributes and can instead use HttpHeaders to extract
	 * and process it
	 * 
	 */
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers){
		String absolutePath= uriInfo.getAbsolutePath().toString();
		String cookie = headers.getCookies().toString();
		return "Path : "+ absolutePath +" Cookie : "+ cookie;
	}

}