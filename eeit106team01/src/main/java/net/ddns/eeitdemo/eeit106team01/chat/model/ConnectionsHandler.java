package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import net.ddns.eeitdemo.eeit106team01.chat.controller.ChatController;

@Component
public class ConnectionsHandler {
	private Map<String, Principal> onlineUsers = new HashMap<String, Principal>();
	
	@Autowired
	private ChatController chatController;

	@EventListener
	protected void onDisconnectEvent(SessionDisconnectEvent event) {
		Collections.synchronizedMap(onlineUsers).remove(event.getUser().getName());
		System.err.println("Client disconnected : " + event.getUser().getName());
		chatController.getOnlineUsers();
	}

	@EventListener
	protected void onConnectedEvent(SessionConnectedEvent event) {
		Collections.synchronizedMap(onlineUsers).put(event.getUser().getName(), event.getUser());
		System.err.println("Client connected : " + event.getUser().getName());
		chatController.getOnlineUsers();
	}

	public Map<String, Principal> getOnlineUsers() {
		return onlineUsers;
	}
}
