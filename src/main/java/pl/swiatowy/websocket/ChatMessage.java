package pl.swiatowy.websocket;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ChatMessage {
	private String message;
	private String sender;
	private String room;
	private Date received;

	public final String getMessage() {
		return message;
	}

	public final void setMessage(final String message) {
		this.message = message;
	}

	public final String getSender() {
		return sender;
	}

	public final void setSender(final String sender) {
		this.sender = sender;
	}

	public final Date getReceived() {
		return received;
	}

	public final void setReceived(final Date received) {
		this.received = received;
	}

	@Override
	public String toString() {
		return "ChatMessage [message=" + message + ", sender=" + sender
				//+"]";
				+ ", received=" + received + "]";
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	public static ChatMessage decode(String message)  throws JsonParseException, JsonMappingException, IOException{
		ChatMessage msg = new ObjectMapper().readValue(message, ChatMessage.class);
		msg.setReceived(new Date());
		System.out.println("decode to: "+msg);
        return msg;

	}
	
	public static String encode(ChatMessage msg) throws JsonGenerationException, JsonMappingException, IOException{
	    String message = new ObjectMapper().writeValueAsString(msg);
	    System.out.println("encode to: "+message);
        return message;
	    
	}

}
