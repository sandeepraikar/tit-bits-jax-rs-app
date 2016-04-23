package edu.sraikar.messengerapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import edu.sraikar.messengerapp.database.DatabaseClass;
import edu.sraikar.messengerapp.exception.DataNotFoundException;
import edu.sraikar.messengerapp.model.Message;

public class MessageService {
	
	private Map<Long,Message> messages = DatabaseClass.getMessages() ;
	
	public MessageService(){
		messages.put(1L, new Message(1,"Hello World!","Sandeep"));
		messages.put(2L, new Message(2,"Hello Jersey!!","Raikar"));
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<>(messages.values());
	}
	
	public Message getMessage(long id){
		Message message = messages.get(id);
		if(message == null){
			throw new DataNotFoundException("Message with id "+ id + " not found");
		}
		return message;
	}
	
	public Message addMessage(Message message){
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message udateMessage(Message message){
		if(message.getId()<=0){
			return null;
		}
		messages.put(message.getId(),message);
		return message;
	}
	
	public Message removeMessage(long id){
		return messages.remove(id);
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR)==year){
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start,int size){
		List<Message> paginatedList = new ArrayList<>(messages.values());
		if(start+size>paginatedList.size()) return new ArrayList<Message>();
		return paginatedList.subList(start, size);
	}
	
	
	/*public List<Message> getAllMessages(){
		Message m1 = new Message(1L,"Hello World!","Sandeep");
		Message m2 = new Message(2L,"Hello Jersey!!","Raikar");
		List<Message> list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		return list;
	}*/

}
