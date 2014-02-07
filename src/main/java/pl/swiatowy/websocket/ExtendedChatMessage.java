package pl.swiatowy.websocket;

public class ExtendedChatMessage extends ChatMessage {

	public ExtendedChatMessage(ChatMessage msg){
		this.setMessage(msg.getMessage());
		this.setSender(msg.getSender());
		this.setReceived(msg.getReceived());
		this.setRoom(msg.getRoom());
	}
	private int userVisited;
	private int userInRoom;
	private int userNum;

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public int getUserVisited() {
		return userVisited;
	}

	public void setUserVisited(int userVisited) {
		this.userVisited = userVisited;
	}

	public int getUserInRoom() {
		return userInRoom;
	}

	public void setUserInRoom(int userInRoom) {
		this.userInRoom = userInRoom;
	}
}
