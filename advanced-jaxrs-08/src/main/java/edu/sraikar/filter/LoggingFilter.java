package edu.sraikar.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter{


	//Executed before the request is being served!
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Headers : "+ requestContext.getHeaders());
	}

	//Executed after the response is prepared
	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		System.out.println(responseContext.getHeaders());
	}

}
