package net.ddns.eeitdemo.eeit106team01.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//	yaowei: 0513

@Entity
@Table(name = "ArticleTopicCurrent")
public class ArticleTopicCurrentBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition = "nvarchar(255)")
	private String topicHeader;
	@Column(columnDefinition = "nvarchar(255)")
	private String topicType;
	@Column(columnDefinition = "nvarchar(255)")
	private String topicRegion;
	private Integer topicLikeNum;
	private Integer contentReplyNum;
	private java.util.Date topicCreateTime;
	private java.util.Date topicUpdateTime;
	private String topicStatus;
	private java.util.Date accidentTime;
	@Column(columnDefinition = "nvarchar(255)")
	private String accidentLocation;
	private Double accidentLocationLongitude;
	private Double accidentLocationLatitude;
	@Column(columnDefinition = "nvarchar(255)")
	private String topicContent;
	private java.util.Date topicContentUpdateTime;
	@Column(columnDefinition = "nvarchar(255)")
	private String updateMessage;

	@OneToMany(mappedBy = "articleTopicCurrent", cascade = CascadeType.ALL)
	List<ArticleContentCurrentBean> articleContentCurrentBeanList = new ArrayList<>();
	
//	memberPK
//	vedioPK

	public ArticleTopicCurrentBean() {
		super();
	}

	public ArticleTopicCurrentBean(String topicHeader, String topicType, String topicRegion, Integer topicLikeNum,
		Integer contentReplyNum, Date topicCreateTime, Date topicUpdateTime, String topicStatus, Date accidentTime,
		String accidentLocation, Double accidentLocationLongitude, Double accidentLocationLatitude, String topicContent,
		Date topicContentUpdateTime, String updateMessage) {
		super();
		this.topicHeader = topicHeader;
		this.topicType = topicType;
		this.topicRegion = topicRegion;
		this.topicLikeNum = topicLikeNum;
		this.contentReplyNum = contentReplyNum;
		this.topicCreateTime = topicCreateTime;
		this.topicUpdateTime = topicUpdateTime;
		this.topicStatus = topicStatus;
		this.accidentTime = accidentTime;
		this.accidentLocation = accidentLocation;
		this.accidentLocationLongitude = accidentLocationLongitude;
		this.accidentLocationLatitude = accidentLocationLatitude;
		this.topicContent = topicContent;
		this.topicContentUpdateTime = topicContentUpdateTime;
		this.updateMessage = updateMessage;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTopicHeader() {
		return topicHeader;
	}

	public void setTopicHeader(String topicHeader) {
		this.topicHeader = topicHeader;
	}

	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}

	public String getTopicRegion() {
		return topicRegion;
	}

	public void setTopicRegion(String topicRegion) {
		this.topicRegion = topicRegion;
	}

	public Integer getTopicLikeNum() {
		return topicLikeNum;
	}

	public void setTopicLikeNum(Integer topicLikeNum) {
		this.topicLikeNum = topicLikeNum;
	}

	public Integer getContentReplyNum() {
		return contentReplyNum;
	}

	public void setContentReplyNum(Integer contentReplyNum) {
		this.contentReplyNum = contentReplyNum;
	}

	public java.util.Date getTopicCreateTime() {
		return topicCreateTime;
	}

	public void setTopicCreateTime(java.util.Date topicCreateTime) {
		this.topicCreateTime = topicCreateTime;
	}

	public java.util.Date getTopicUpdateTime() {
		return topicUpdateTime;
	}

	public void setTopicUpdateTime(java.util.Date topicUpdateTime) {
		this.topicUpdateTime = topicUpdateTime;
	}

	public String getTopicStatus() {
		return topicStatus;
	}

	public void setTopicStatus(String topicStatus) {
		this.topicStatus = topicStatus;
	}

	public java.util.Date getAccidentTime() {
		return accidentTime;
	}

	public void setAccidentTime(java.util.Date accidentTime) {
		this.accidentTime = accidentTime;
	}

	public String getAccidentLocation() {
		return accidentLocation;
	}

	public void setAccidentLocation(String accidentLocation) {
		this.accidentLocation = accidentLocation;
	}

	public Double getAccidentLocationLongitude() {
		return accidentLocationLongitude;
	}

	public void setAccidentLocationLongitude(Double accidentLocationLongitude) {
		this.accidentLocationLongitude = accidentLocationLongitude;
	}

	public Double getAccidentLocationLatitude() {
		return accidentLocationLatitude;
	}

	public void setAccidentLocationLatitude(Double accidentLocationLatitude) {
		this.accidentLocationLatitude = accidentLocationLatitude;
	}

	public String getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}

	public java.util.Date getTopicContentUpdateTime() {
		return topicContentUpdateTime;
	}

	public void setTopicContentUpdateTime(java.util.Date topicContentUpdateTime) {
		this.topicContentUpdateTime = topicContentUpdateTime;
	}

	public String getUpdateMessage() {
		return updateMessage;
	}

	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}

	public List<ArticleContentCurrentBean> getArticleContentCurrentBeanList() {
		return articleContentCurrentBeanList;
	}

	public void setArticleContentCurrentBeanList(List<ArticleContentCurrentBean> articleContentCurrentBeanList) {
		this.articleContentCurrentBeanList = articleContentCurrentBeanList;
	}

}
