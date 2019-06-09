package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.io.Serializable;

public class RegionMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4916822208524075783L;
	private String name;
	private String message;
	private java.util.Date sendTime;
	private String command;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
