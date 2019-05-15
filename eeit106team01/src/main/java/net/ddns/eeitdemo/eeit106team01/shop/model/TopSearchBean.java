package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class TopSearchBean implements Serializable{

	private static final long serialVersionUID = 4059835639841966792L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String keyWord;
	private Integer searchTimes;
	
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public java.util.Date getTime() {
		return time;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	public Integer getSearchTimes() {
		return searchTimes;
	}
	public void setSearchTimes(Integer searchTimes) {
		this.searchTimes = searchTimes;
	}
}
