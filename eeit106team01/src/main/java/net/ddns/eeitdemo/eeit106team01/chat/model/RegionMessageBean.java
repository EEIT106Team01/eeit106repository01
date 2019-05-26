package net.ddns.eeitdemo.eeit106team01.chat.model;

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
	private ArrayList<MessageBean> messages;
	@Column(name = "indexs")
	private Integer index;

	public RegionMessageBean generateNewRecord() {
		RegionMessageBean newRecord = null;
		newRecord = new RegionMessageBean();
		if (this.getIndex() != null) {
			newRecord.setIndex(this.getIndex() + 1);
		}
		newRecord.setMessage(new ArrayList<MessageBean>());
		newRecord.setRegion(this.getRegion());
		return newRecord;
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

	public ArrayList<MessageBean> getMessage() {
		return messages;
	}

	public void setMessage(ArrayList<MessageBean> message) {
		this.messages = message;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
