package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
	private static final long serialVersionUID = 376832387746371279L;

	private String message;
	private String url;
	private String imgUrl;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
}
