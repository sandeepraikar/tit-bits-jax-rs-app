package edu.sraikar.messengerapp.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.sraikar.messengerapp.model.Message;
import edu.sraikar.messengerapp.resources.bean.MessageFilterBean;
import edu.sraikar.messengerapp.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService service = new MessageService();
	
	/* v_0.1
	@GET      // This is to specify the relevant HTTP method!!
	@Produces(MediaType.TEXT_PLAIN) //This specifies response Format (TEXT_PLAIN indicates that the response is plain text)
	public String getMessages(){
		return "HelloWorld";
	}*/
	
	//@GET      // This is to specify the relevant HTTP method!!
	//@Produces and @Consumes has now been added to class level
	//@Produces(MediaType.APPLICATION_JSON) //Sending the response as XML
	//public List<Message> getMessages(){
	//	return service.getAllMessages();
	//}
	
	
	//New method for getMessages with query params year, start, size
	/*	@GET
	public List<Message> getMessages(@QueryParam("year") int year,
									 @QueryParam("start") int start,
									 @QueryParam("size") int size) {
		if (year > 0) {
			return service.getAllMessagesForYear(year);
		}
		if (start >= 0 && size > 0) {
			return service.getAllMessagesPaginated(start, size);
		}
		return service.getAllMessages();
	}
	*/

	//New method for getMessage to illustrate BeanParam annotations
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("JSON Message called");
		if (filterBean.getYear() > 0) {
			return service.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return service.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return service.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("XML Message called");
		if (filterBean.getYear() > 0) {
			return service.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return service.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return service.getAllMessages();
	}
	
	/*@GET
	@Path("/{messageId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String test(@PathParam("messageId") String messageId){
		return "Got Path param :"+messageId;
	}*/
	
	@GET
	@Path("/{messageId}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo){
		Message message = service.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "profile");
		return message;
	}


	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder() // -> http://localhost:8080/MessengerApp/webapi/
				 .path(MessageResource.class) // -> messages
				 .path(MessageResource.class, "getCommentResource") //-> {messageId}/Comments
				 .path(CommentResource.class)
				 .resolveTemplate("messageId", message.getId()) //replaces {messageId} with the value
				 .build();
		return uri.toString();
	}


	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder() // -> http://localhost:8080/MessengerApp/webapi/
						 .path(ProfileResource.class) // ->profiles
						 .path(message.getAuthor()) //-> {authorName} - retrieved from Message bean
						 .build();
		return uri.toString();
	}


	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()  // -> http://localhost:8080/MessengerApp/webapi/
						.path(MessageResource.class) //This will be @Path annotation value at class level!! -> /messages
						.path(Long.toString(message.getId())) //{messageId}
						.build()
						.toString();
		return uri;
	}
	
	//Creating a POST request
	/*
	 * Use @Consumes to specify the expected request body format
	   basically Content-header type should have the correct format set for POST request
		
		example:
		Header_type     header_value
		Content-Type    application/json
	 */
	
	/*
	 * Check below for updated addMessage method!!!
	@POST
	//@Consumes(MediaType.APPLICATION_JSON) //This indicates that this post request consumes content of json format
	//@Produces(MediaType.APPLICATION_JSON)
	public Message addMessage(Message message) //Here jersey is gonna automatically convert the json object passed to Message instance
	{
		return service.addMessage(message);
	}
	
	*/
	
	//This adds the message and also sends the status code and the location headerss
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo){
		
		
		//This is version 1.0
		/*return Response.status(Status.CREATED) //sets the status code to 201
				       .entity(message) //pass the object
				       .build();*/
		
		Message newMessage = service.addMessage(message);
		String id = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri) //Best practice as it sets the status code to 201 and also the location header!! this is awesome! 
			       .entity(message)
			       .build();
	}
	
	
	//Update operation
	@PUT
	@Path("/{messageId}")
	//@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id, Message message){
		message.setId(id);
		return service.udateMessage(message);
	}
	
	//Delete operation
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id){
		service.removeMessage(id);
	}
	
	//Delegating call s to comment resource
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
}
