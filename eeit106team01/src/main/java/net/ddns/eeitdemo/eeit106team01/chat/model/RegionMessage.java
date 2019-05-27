package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.io.Serializable;

public class RegionMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4916822208524075783L;
	private Integer id;
	private String name;
	private String message;
	private java.util.Date sendTime;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.util.Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}
}
