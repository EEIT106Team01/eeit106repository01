package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NotificationBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<Notification> messages;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<Notification> offlineMessages;
	@Column(name = "indexs")
	private Integer index;
	private String status;
}
