package net.ddns.eeitdemo.eeit106team01.chat.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
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

//	public List<RegionMsg> getOldRegionMessage(String region) {
//		List<RegionMessageBean> results = regionMessageService.findAllByRegion(region);
//		List<RegionMsg> messageBeans = new ArrayList<RegionMsg>();
//		if (results.size() != 0) {
//			if (results.size() >= 2) {
//				messageBeans.addAll(results.get(results.size() - 2).getMessage());
//			}
//			messageBeans.addAll(results.get(results.size() - 1).getMessage());
//		}
//		return messageBeans;
//	}

	@MessageMapping("/north")
	@SendTo("/topic/north")
	public Object northChat(RegionMsg regionMessage) {
		return this.processRegionChat("north", regionMessage);
	}
	
	@MessageMapping("/middle")
	@SendTo("/topic/middle")
	public Object middleChat(RegionMsg regionMessage) {
		return this.processRegionChat("middle", regionMessage);
	}
	
	@MessageMapping("/south")
	@SendTo("/topic/south")
	public Object sourthChat(RegionMsg regionMessage) {
		return this.processRegionChat("south", regionMessage);
	}
	
	@MessageMapping("/east")
	@SendTo("/topic/east")
	public Object eastChat(RegionMsg regionMessage) {
		return this.processRegionChat("east", regionMessage);
	}

	public Object processRegionChat(String region, RegionMsg regionMessage) {
		if (regionMessage != null & regionMessage.getCommand() != null) {
			// if toUser exist in MemberBean check

			if (regionMessage.getCommand().equalsIgnoreCase("normal")) {
				regionMessage.setSendTime(new java.util.Date(System.currentTimeMillis()));
				regionMessageService.addMessage(region, regionMessage);
			} else if (regionMessage.getCommand().equalsIgnoreCase("getOldByIndex")) {
				int index = Integer.parseInt(regionMessage.getMessage());
				List<RegionMessageBean> oldMessages = new ArrayList<RegionMessageBean>();
				oldMessages.add(regionMessageService.findByRegionAndIndex(region, index));
				return oldMessages;
			}
		}
		return regionMessage;
	}

	@SubscribeMapping("/topic/north")
	public List<RegionMessageBean> sendActiveNorthMessages() {
		return this.sendActiveRegionMessages("north");
	}

	@SubscribeMapping("/topic/middle")
	public List<RegionMessageBean> sendActiveMiddleMessages() {
		return this.sendActiveRegionMessages("middle");
	}

	@SubscribeMapping("/topic/south")
	public List<RegionMessageBean> sendActiveSourthMessages() {
		return this.sendActiveRegionMessages("south");
	}

	@SubscribeMapping("/topic/east")
	public List<RegionMessageBean> sendActiveEastMessages() {
		return this.sendActiveRegionMessages("east");
	}

	public List<RegionMessageBean> sendActiveRegionMessages(String region) {
		List<RegionMessageBean> oldMessages = null;
		if (regionMessageService.findActiveByRegion(region) != null) {
			oldMessages = new ArrayList<RegionMessageBean>();
			oldMessages.add(regionMessageService.findActiveByRegion(region));
		}
		return oldMessages;
	}

//	/**
//	 * 定時發送訊息
//	 */
//	@Scheduled(fixedRate = 1000)
//	public void callback() {
//		// 發送訊息
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		messagingTemplate.convertAndSend("/topic/callback", "定時發送訊息時間: " + df.format(new Date()));
//	}

	@MessageMapping("/msg")
//	@SendTo("/topic/user")
	public void sendPrivateMessage(PrivateMsg privateMsg, Principal fromUser) {
//		SimpUser toUser = simpUserRegistry.getUser(transferMessageBean.getToUser());
		if (fromUser != null && fromUser.getName() != null && fromUser.getName().trim().length() != 0) {
			if (privateMsg != null & privateMsg.getCommand() != null) {
				// if toUser exist in MemberBean check

				if (privateMsg.getCommand().equalsIgnoreCase("normal")) {
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
				} else if (privateMsg.getCommand().equalsIgnoreCase("getOldByIndex")) {
					int index = Integer.parseInt(privateMsg.getMessage());
					List<PrivateMessageBean> oldMessages = new ArrayList<PrivateMessageBean>();
					oldMessages.add(privateMessageService.findByUsersAndIndex(privateMsg.getFromUser(),
							privateMsg.getToUser(), index));
					messagingTemplate.convertAndSendToUser(fromUser.getName(), "/topic/msg", oldMessages);
				}
			}
		}
	}

	@SubscribeMapping("/user/topic/msg")
	public List<PrivateMessageBean> sendAllActivePrivateMessage(Principal user) {
		if (user != null && user.getName() != null && user.getName().trim().length() != 0) {
			System.err.println(user.getName() + " subcribed private message.");
			List<PrivateMessageBean> privateMessages = privateMessageService.findAllActiveByUser(user.getName());
			for (PrivateMessageBean privateMessage : privateMessages) {
				privateMessageService.clearOfflineMessage(privateMessage.getUserOne(), privateMessage.getUserTwo(),
						user.getName());
			}
			return privateMessages;
		}
		return null;
	}

}
