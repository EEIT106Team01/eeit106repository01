package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(schema = "Shop", name = "TopSearch")
public class TopSearchBean implements Serializable {

	private static final long serialVersionUID = 4059835639841966792L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TopSearchID", columnDefinition = "bigint")
	private Long id;

	@Column(name = "Keyword", columnDefinition = "nvarchar(max)", nullable = false, updatable = false)
	private String keyword;

	@Column(name = "SearchCount", nullable = false)
	private Integer searchCount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdatedTime", nullable = false)
	private java.util.Date updatedTime;

	public TopSearchBean() {
		super();
	}

	/**
	 * @param keyword
	 * @param searchCount
	 * @param updatedTime
	 */
	public TopSearchBean(String keyword, Integer searchCount, Date updatedTime) {
		super();
		this.keyword = keyword;
		this.searchCount = searchCount;
		this.updatedTime = updatedTime;
	}

	@Override
	public String toString() {
		return "TopSearchBean [id=" + id + ", keyword=" + keyword + ", searchCount=" + searchCount + ", updatedTime="
				+ updatedTime + "]";
	}

	/**
	 * Check keyword, updatedTime, searchCount null or not.
	 * 
	 * @return true if not null, or NullPointerException if it is.
	 */
	public Boolean isNotNull() {
		this.keyword = Objects.requireNonNull(keyword, "keyword must not be null");
		this.updatedTime = Objects.requireNonNull(updatedTime, "updatedTime must not be null");
		this.searchCount = Objects.requireNonNull(searchCount, "searchCount must not be null");
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(Integer searchCount) {
		this.searchCount = searchCount;
	}

	public java.util.Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(java.util.Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}
