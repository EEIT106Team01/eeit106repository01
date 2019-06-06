package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RegionMessage")
public class RegionMessageBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String region;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<RegionMsg> messages;
	@Column(name = "indexs")
	private Integer index;
	private String status;
	
	public RegionMessageBean() {
		this.messages = new ArrayList<RegionMsg>();
		this.setStatus("active");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public ArrayList<RegionMsg> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<RegionMsg> message) {
		this.messages = message;
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
