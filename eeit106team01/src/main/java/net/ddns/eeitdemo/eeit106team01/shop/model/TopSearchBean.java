package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SHOP_TopSearch")
public class TopSearchBean implements Serializable {

	private static final long serialVersionUID = 4059835639841966792L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint")
	private Long id;

	@Column(nullable = false, columnDefinition = "nvarchar(max)")
	private String keyWord;

	@Column(nullable = false)
	private Integer searchTimes;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date time;

	@Override
	public String toString() {
		return "TopSearchBean [id=" + id + ", keyWord=" + keyWord + ", searchTimes=" + searchTimes + ", time=" + time
				+ "]";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public void setTime() {
		this.time = new Date(System.currentTimeMillis());
	}

	public Integer getSearchTimes() {
		return searchTimes;
	}

	public void setSearchTimes(Integer searchTimes) {
		this.searchTimes = searchTimes;
	}
}
