package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Date;

public class NotificationMsg implements Serializable {
	private static final long serialVersionUID = 376832387746371279L;

	private String message;
	private String url;
	private String icon;
	private Color color;
	private Date sendTime;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
