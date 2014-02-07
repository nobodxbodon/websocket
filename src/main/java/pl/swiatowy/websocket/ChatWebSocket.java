package pl.swiatowy.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ChatWebSocket {

    private static List<ChatWebSocket> users = new CopyOnWriteArrayList<>();
    private RemoteEndpoint conn;
    /**
     * keep room info for user. restrict one room for one user
     */
    private static Map<ChatWebSocket, String> rooms = new HashMap<ChatWebSocket, String>();

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.conn = session.getRemote();
        users.add(this);
    }

    @OnWebSocketClose
    public void onClose(int closeCode, String reason) {
        users.remove(this);
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) {
        System.out.println("Message received:" + message);
        String room = "";
        ChatMessage msg = null;
        ExtendedChatMessage emsg = null;
        try {
        	msg = ChatMessage.decode(message);
			room = msg.getRoom();
			if(!rooms.containsKey(this)){
				System.out.println(msg.getSender()+" joins room: "+room);
				rooms.put(this, room);
			}
			emsg = new ExtendedChatMessage(msg);
			emsg.setUserNum(users.size());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for (ChatWebSocket user : users) {
            try {
            	if(rooms.containsKey(user) && rooms.get(user).equals(room))
            		user.conn.sendString(ChatMessage.encode(emsg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
