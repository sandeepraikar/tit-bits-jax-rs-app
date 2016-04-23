package edu.sraikar.messengerapp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.sraikar.messengerapp.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errMessage = new ErrorMessage(ex.getMessage(), 404,"some documentation link");
		return Response.status(Status.NOT_FOUND)
				.entity(errMessage)
				.build();
	}

}
