package net.ddns.eeitdemo.eeit106team01.chat.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import net.ddns.eeitdemo.eeit106team01.chat.model.ConnectionsHandler;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessage;
import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMessage;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageService;

@EnableScheduling
@Controller
public class ChatController {
	private final SimpMessagingTemplate messagingTemplate;

//	@Autowired
//	private SimpUserRegistry simpUserRegistry;
	@Autowired
	private ConnectionsHandler connectionsHandler;

	@Autowired
	private RegionMessageService regionMessageService;

	@Autowired
	public ChatController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public List<RegionMessage> getOldRegionMessage(String region) {
		List<RegionMessageBean> results = regionMessageService.findAllByRegion(region);
		List<RegionMessage> messageBeans = new ArrayList<RegionMessage>();
		if (results.size() >= 2) {
			messageBeans.addAll(results.get(results.size() - 2).getMessage());
		}
		messageBeans.addAll(results.get(results.size() - 1).getMessage());
		return messageBeans;
	}

	private RegionMessageBean northMessageBean = null;

	@MessageMapping("/northChat")
	@SendTo("/topic/northChat")
	public RegionMessage northChat(RegionMessage regionMessage) {
		regionMessage.setSendTime(new java.util.Date(System.currentTimeMillis()));
		synchronized (this) {
			if (northMessageBean == null) {
				northMessageBean = regionMessageService.findNewestByRegion("north");
			}
			northMessageBean = regionMessageService.insertMessage(northMessageBean, regionMessage);
		}
		return regionMessage;
	}

	@SubscribeMapping("/topic/northChat")
	public List<RegionMessage> northChatOld() {
		System.out.println("SubscribeMapping(\"/northChatOld\")");
		return this.getOldRegionMessage("north");
	}

	/**
	 * 定時發送訊息
	 */
	@Scheduled(fixedRate = 1000)
	public void callback() {
		// 發送訊息
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messagingTemplate.convertAndSend("/topic/callback", "定時發送訊息時間: " + df.format(new Date()));
	}

	@MessageMapping("/msg")
//	@SendTo("/topic/user")
	public void sendToSpecificUser(PrivateMessage privateMessage) {
//		SimpUser toUser = simpUserRegistry.getUser(transferMessageBean.getToUser());
		Principal toUser = connectionsHandler.getOnlineUsers().get(privateMessage.getToUser());
		if (toUser != null) {
			System.out.println(toUser.getName());
			privateMessage.setSendTime(new java.util.Date(System.currentTimeMillis()));
			messagingTemplate.convertAndSendToUser(toUser.getName(), "/topic/msg", privateMessage);
		} else {
			System.out.println("Can not find user of name : " + privateMessage.getToUser());
		}

	}

}
