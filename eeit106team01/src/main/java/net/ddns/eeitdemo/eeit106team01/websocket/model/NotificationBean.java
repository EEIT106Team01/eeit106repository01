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
	private ArrayList<NotificationMsg> messages;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<NotificationMsg> offlineMessages;
	@Column(name = "indexs")
	private Integer index;
	private String status;

	public NotificationBean() {
		this.messages = new ArrayList<NotificationMsg>();
		this.offlineMessages = new ArrayList<NotificationMsg>();
		this.status = "active";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<NotificationMsg> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<NotificationMsg> messages) {
		this.messages = messages;
	}

	public ArrayList<NotificationMsg> getOfflineMessages() {
		return offlineMessages;
	}

	public void setOfflineMessages(ArrayList<NotificationMsg> offlineMessages) {
		this.offlineMessages = offlineMessages;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
