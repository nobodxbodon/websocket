package pl.swiatowy.websocket;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@WebSocket
public class ChatWebSocket {

    private static List<ChatWebSocket> users = new CopyOnWriteArrayList<>();
    private RemoteEndpoint conn;

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
        for (ChatWebSocket user : users) {
            try {
                user.conn.sendString(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
