package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.io.Serializable;

public class PrivateMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8108676875238180851L;
	private String fromUser;
	private String toUser;
	private String message;
	private java.util.Date sendTime;
	private String command;
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public java.util.Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}
}
