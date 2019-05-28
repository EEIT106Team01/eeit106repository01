package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class ConnectionsHandler {
	private Map<String, Principal> onlineUsers = new HashMap<String, Principal>();

	@EventListener
	protected void onDisconnectEvent(SessionDisconnectEvent event) {
		Collections.synchronizedMap(onlineUsers).remove(event.getUser().getName());
		System.out.println("Client disconnected : " + event.getUser().getName());
	}

	@EventListener
	protected void onConnectedEvent(SessionConnectedEvent event) {
		Collections.synchronizedMap(onlineUsers).put(event.getUser().getName(), event.getUser());
		System.out.println("Client connected : " + event.getUser().getName());
	}

	public Map<String, Principal> getOnlineUsers() {
		return onlineUsers;
	}
}
