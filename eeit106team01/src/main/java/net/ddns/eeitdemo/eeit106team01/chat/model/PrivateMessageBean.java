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
	private ArrayList<PrivateMsg> messages;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<PrivateMsg> userOneOfflineMessages;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<PrivateMsg> userTwoOfflineMessages;
	@Column(name = "indexs")
	private Integer index;
	private String status;
	
	public PrivateMessageBean() {
		this.messages = new ArrayList<PrivateMsg>();
		this.userOneOfflineMessages = new ArrayList<PrivateMsg>();
		this.userTwoOfflineMessages = new ArrayList<PrivateMsg>();
		this.status = "active";
	}

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

	public ArrayList<PrivateMsg> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<PrivateMsg> messages) {
		this.messages = messages;
	}

	public ArrayList<PrivateMsg> getUserOneOfflineMessages() {
		return userOneOfflineMessages;
	}

	public void setUserOneOfflineMessages(ArrayList<PrivateMsg> userOneOfflineMessages) {
		this.userOneOfflineMessages = userOneOfflineMessages;
	}

	public ArrayList<PrivateMsg> getUserTwoOfflineMessages() {
		return userTwoOfflineMessages;
	}

	public void setUserTwoOfflineMessages(ArrayList<PrivateMsg> userTwoOfflineMessages) {
		this.userTwoOfflineMessages = userTwoOfflineMessages;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
