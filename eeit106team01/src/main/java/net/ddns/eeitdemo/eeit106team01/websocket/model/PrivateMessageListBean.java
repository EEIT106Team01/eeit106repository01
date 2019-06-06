package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PrivateMessageList")
public class PrivateMessageListBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(columnDefinition = "varbinary(max)")
	private ArrayList<String> contactList;
	
	public PrivateMessageListBean() {
		this.contactList = new ArrayList<String>();
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

	public ArrayList<String> getContactList() {
		return contactList;
	}

	public void setContactList(ArrayList<String> contactList) {
		this.contactList = contactList;
	}
}
