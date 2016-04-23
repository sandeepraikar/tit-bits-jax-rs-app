package edu.sraikar.messengerapp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.sraikar.messengerapp.model.ErrorMessage;

//@Provider
//Provider annotation is disabled for now to test WebApplicationException and NotFoundException froms the service class
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errMessage = new ErrorMessage(ex.getMessage(), 500,"some documentation link");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errMessage)
				.build();
	}


}
