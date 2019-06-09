package net.ddns.eeitdemo.eeit106team01.websocket.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import net.ddns.eeitdemo.eeit106team01.websocket.model.ConnectionsHandler;
import net.ddns.eeitdemo.eeit106team01.websocket.model.NotificationBean;
import net.ddns.eeitdemo.eeit106team01.websocket.model.NotificationMsg;
import net.ddns.eeitdemo.eeit106team01.websocket.model.NotificationService;

@Controller
public class NotificationController {
	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	private ConnectionsHandler connectionsHandler;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	public NotificationController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public void sendNotificationToUser(String username, NotificationMsg notificationMsg) {
		Principal toUser = connectionsHandler.getOnlineUsers().get(username);
		notificationMsg.setSendTime(new Date(System.currentTimeMillis()));
		if (toUser != null) {
			notificationService.addMessage(username, notificationMsg);
			messagingTemplate.convertAndSendToUser(toUser.getName(), "/topic/notificationMsg", notificationMsg);
		} else {
			notificationService.addOfflineMessage(username, notificationMsg);
		}
	}

	@MessageMapping("getActiveNotification")
	public void getActiveNotification(Principal user) {
		NotificationBean notificationBean = notificationService.findActiveByUser(user.getName());
		if (notificationBean != null) {
			messagingTemplate.convertAndSendToUser(user.getName(), "/topic/getActiveNotification", notificationBean);
		}
	}

	@MessageMapping("clearOfflineNotification")
	public void clearOfflineNotification(Principal user) {
		notificationService.clearOfflineMessage(user.getName());
	}

}
