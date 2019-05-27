package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PrivateMessage")
public class PrivateMessageBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String userOne;
	private String userTwo;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<PrivateMessage> messages;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<PrivateMessage> userOneOfflineMessages;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<PrivateMessage> userTwoOfflineMessages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserOne() {
		return userOne;
	}

	public void setUserOne(String userOne) {
		this.userOne = userOne;
	}

	public String getUserTwo() {
		return userTwo;
	}

	public void setUserTwo(String userTwo) {
		this.userTwo = userTwo;
	}

	public ArrayList<PrivateMessage> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<PrivateMessage> messages) {
		this.messages = messages;
	}

	public ArrayList<PrivateMessage> getUserOneUnreadMessages() {
		return userOneOfflineMessages;
	}

	public void setUserOneUnreadMessages(ArrayList<PrivateMessage> userOneUnreadMessages) {
		this.userOneOfflineMessages = userOneUnreadMessages;
	}

	public ArrayList<PrivateMessage> getUserTwoUnreadMessages() {
		return userTwoOfflineMessages;
	}

	public void setUserTwoUnreadMessages(ArrayList<PrivateMessage> userTwoUnreadMessages) {
		this.userTwoOfflineMessages = userTwoUnreadMessages;
	}

}
