package edu.sraikar.messengerapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import edu.sraikar.messengerapp.database.DatabaseClass;
import edu.sraikar.messengerapp.model.Comment;
import edu.sraikar.messengerapp.model.Message;

public class CommentService {

	private Map<Long,Message> messages = DatabaseClass.getMessages() ;
	
	public List<Comment> getAllComments(long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId,long commentId){
		Message message = messages.get(messageId);
		if(message==null){
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(messageId);
		if(comment==null){
			throw new NotFoundException();
		}
		//return comments.get(commentId);
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment){
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size()+1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId()<=0){
			return null;
		}
		comments.put(comment.getId(),comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
