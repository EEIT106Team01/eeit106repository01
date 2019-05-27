package net.ddns.eeitdemo.eeit106team01.chat.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import net.ddns.eeitdemo.eeit106team01.chat.model.MessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageService;
import net.ddns.eeitdemo.eeit106team01.chat.model.TransferMessageBean;

@EnableScheduling
@Controller
public class ChatController {
	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	private SimpUserRegistry simpUserRegistry;
	
	@Autowired
	private RegionMessageService regionMessageService;

	@Autowired
	public ChatController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	
	private RegionMessageBean northMessageBean = null;
	
	@MessageMapping("/northChat")
	@SendTo("/topic/northChat")
	public MessageBean northChat(MessageBean message) {
		synchronized (this) {
			if (northMessageBean == null) {
				northMessageBean = regionMessageService.findNewestByRegion("north");
			}
			northMessageBean = regionMessageService.insertMessage(northMessageBean, message);
		}
		return message;
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
	public void sendToSpecificUser(TransferMessageBean transferMessageBean) {
		SimpUser toUser = simpUserRegistry.getUser(transferMessageBean.getToUser());
		if (toUser != null) {
			System.out.println(toUser.getName());
			messagingTemplate.convertAndSendToUser(toUser.getName(), "/topic/msg", transferMessageBean);
		} else {
			System.out.println("Can not find user of name : " + transferMessageBean.getToUser());
		}

	}
}
