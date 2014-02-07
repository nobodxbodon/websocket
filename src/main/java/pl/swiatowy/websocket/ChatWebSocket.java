package pl.swiatowy.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ChatWebSocket {

	static Logger log = Logger.getLogger(ChatWebSocket.class.getName());
    private static List<ChatWebSocket> users = new CopyOnWriteArrayList<>();
    private static int usersVisited = 0;
    private RemoteEndpoint conn;
    /**
     * keep room info for user. restrict one room for one user
     */
    private static Map<ChatWebSocket, String> rooms = new HashMap<ChatWebSocket, String>();

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.conn = session.getRemote();
        users.add(this);
        usersVisited++;
    }

    @OnWebSocketClose
    public void onClose(int closeCode, String reason) {
        users.remove(this);
        rooms.remove(this);
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) {
        log.info("Message received:" + message);
        String room = "";
        ChatMessage msg = null;
        ExtendedChatMessage emsg = null;
        try {
        	msg = ChatMessage.decode(message);
			room = msg.getRoom();
			if(!rooms.containsKey(this)){
				log.info(msg.getSender()+" joins room: "+room);
				rooms.put(this, room);
			}
			emsg = new ExtendedChatMessage(msg);
			emsg.setUserNum(users.size());
		} catch (IOException e1) {
			log.error(e1);
		}
        List<ChatWebSocket> usersInRoom = new ArrayList<ChatWebSocket>();
        for (ChatWebSocket user : users) {
        	if(rooms.containsKey(user) && rooms.get(user).equals(room)){
    			//get users in the room
        		usersInRoom.add(user);
        	}
        }
        emsg.setUserInRoom(usersInRoom.size());
        emsg.setUserVisited(usersVisited);
        for(ChatWebSocket userInRoom : usersInRoom){
            try {
            	userInRoom.conn.sendString(ChatMessage.encode(emsg));
            } catch (IOException e) {
    			log.error(e);
            }
        }
    }
}
