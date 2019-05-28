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
import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMessageService;
import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMsg;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageService;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMsg;

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
	private PrivateMessageService privateMessageService;

	@Autowired
	public ChatController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public List<RegionMsg> getOldRegionMessage(String region) {
		List<RegionMessageBean> results = regionMessageService.findAllByRegion(region);
		List<RegionMsg> messageBeans = new ArrayList<RegionMsg>();
		if (results.size() != 0) {
			if (results.size() >= 2) {
				messageBeans.addAll(results.get(results.size() - 2).getMessage());
			}
			messageBeans.addAll(results.get(results.size() - 1).getMessage());
		}
		return messageBeans;
	}

	private RegionMessageBean northMessageBean = null;

	@MessageMapping("/north")
	@SendTo("/topic/north")
	public RegionMsg northChat(RegionMsg regionMessage) {
		regionMessage.setSendTime(new java.util.Date(System.currentTimeMillis()));
		synchronized (this) {
			if (northMessageBean == null) {
				northMessageBean = regionMessageService.findNewestByRegion("north");
			}
			northMessageBean = regionMessageService.insertMessage(northMessageBean, regionMessage);
		}
		return regionMessage;
	}

	@SubscribeMapping("/topic/north")
	public List<RegionMsg> northChatOld() {
			System.out.println("SubscribeMapping(north)");
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
	public void sendPrivateMessage(PrivateMsg privateMsg, Principal fromUser) {
//		SimpUser toUser = simpUserRegistry.getUser(transferMessageBean.getToUser());
		if (fromUser != null && fromUser.getName() != null && fromUser.getName().trim().length() != 0) {
			// if toUser exist in MemberBean check
			Principal toUser = connectionsHandler.getOnlineUsers().get(privateMsg.getToUser());
			privateMsg.setSendTime(new java.util.Date(System.currentTimeMillis()));
			if (toUser != null) {
				System.out.println(toUser.getName());
				privateMessageService.addMessage(fromUser.getName(), toUser.getName(), privateMsg);
				messagingTemplate.convertAndSendToUser(toUser.getName(), "/topic/msg", privateMsg);
			} else {
				System.out.println("Can not find or not online user : " + privateMsg.getToUser());
				privateMessageService.addOfflineMessage(fromUser.getName(), privateMsg.getToUser(),
						privateMsg.getToUser(), privateMsg);
			}
		}
	}

	@SubscribeMapping("/user/topic/msg")
	public List<PrivateMessageBean> sendAllActivePrivateMessage(Principal user) {
		if (user != null && user.getName() != null && user.getName().trim().length() != 0) {
			System.err.println(user.getName() + " subcribed private message.");
			return privateMessageService.getAllActiveByUser(user.getName());
		}
		return null;
	}

}
