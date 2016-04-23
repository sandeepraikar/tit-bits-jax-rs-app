package edu.sraikar.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.sraikar.messengerapp.model.Message;

public class RestClient {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		// Type1
		/*
		 * Response response =
		 * client.target("http://localhost:8080/advanced-jaxrs-01/webapi/messages/1"
		 * ) .request(MediaType.APPLICATION_JSON) .get(); Message message =
		 * response.readEntity(Message.class);
		 */

		// Type2
		/*
		 * Message message =
		 * client.target("http://localhost:8080/advanced-jaxrs-01/webapi/messages/2"
		 * ) .request(MediaType.APPLICATION_JSON) .get(Message.class); //Message
		 * message = response.readEntity(Message.class);
		 */

		// Type3 - mostly for debugging!!
		/*
		 * String message =
		 * client.target("http://localhost:8080/advanced-jaxrs-01/webapi/messages/2"
		 * ) .request(MediaType.APPLICATION_JSON) .get(String.class);
		 * System.out.println(message);
		 */

		// Best practice!!

		WebTarget baseTarget = client
				.target("http://localhost:8080/advanced-jaxrs-01/webapi/");
		WebTarget messagesTarget = baseTarget.path("messages");
		WebTarget singleMessageTarget = messagesTarget.path("{messageId}");

		Message message1 = singleMessageTarget
				.resolveTemplate("messageId", "1")
				.request(MediaType.APPLICATION_JSON).get(Message.class);

		Message message2 = singleMessageTarget
				.resolveTemplate("messageId", "2")
				.request(MediaType.APPLICATION_JSON).get(Message.class);

		System.out.println(message1.getMessage());
		System.out.println(message2.getMessage());

		/******************************************************************/

		// Creating a POST request and PUT is going to be similar to this (use singleMessageTarget and rest same as POST

		Message newMessage = new Message(4, "New message from JAX-RS client",
				"Sandeep Raikar");

		Response postResponse = messagesTarget
				.request()
				.post(Entity.json(newMessage));
		
		if(postResponse.getStatus()==201){
			Message retrievedMessage = postResponse.readEntity(Message.class);
			System.out.println(retrievedMessage.getMessage());
		}

	}

}
