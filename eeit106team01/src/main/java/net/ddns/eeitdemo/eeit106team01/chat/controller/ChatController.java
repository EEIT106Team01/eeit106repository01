package net.ddns.eeitdemo.eeit106team01.chat.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import net.ddns.eeitdemo.eeit106team01.chat.model.RequestMessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.ResponseMessageBean;

@EnableScheduling
@Controller
public class ChatController {
	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

	@MessageMapping("/welcome")
	@SendTo("/topic/say")
	public ResponseMessageBean say(RequestMessageBean message) {
		System.out.println(message.getName());
		return new ResponseMessageBean("welcome," + message.getName() + " !");
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
}
