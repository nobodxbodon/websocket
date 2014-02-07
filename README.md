websocket
=========

Got UI from <a href="http://www.hascode.com/2013/08/creating-a-chat-application-using-java-ee-7-websockets-and-glassfish-4/">Creating a Chat Application using Java EE 7, Websockets and GlassFish 4</a>

For simple usage Java EE is too heavy weight, thus switch to:

Example chat application using websocket on Jetty 9

### Running chat application
```java  
mvn clean install jetty:run
```
### Use case
1. Open two separate browser windows (distinct websocket sessions)
2. Assign different user names - after *Welcome,* prompt
3. Play with messages from different users and watch "real-time" chat
4. Total current session number shown on top

### Requirements
JDK 7
