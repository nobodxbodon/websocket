package pl.swiatowy.websocket;

public class ExtendedChatMessage extends ChatMessage {

	public ExtendedChatMessage(ChatMessage msg){
		this.setMessage(msg.getMessage());
		this.setSender(msg.getSender());
		this.setReceived(msg.getReceived());
		this.setRoom(msg.getRoom());
	}
	
	private int userNum;

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
}
