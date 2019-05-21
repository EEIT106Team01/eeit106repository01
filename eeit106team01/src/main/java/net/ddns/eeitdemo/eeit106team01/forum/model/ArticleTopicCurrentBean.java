package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	private HashMap<Integer, String> topicLikeWho;
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
	private Integer pageViews;
	@Column(columnDefinition = "nvarchar(255)")
	private String updateMessage;

//	memberPK
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id")
	private MemberBean memberBean;

//	vedioPK
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "video_id")
	private VideoBean videoBean;

	public ArticleTopicCurrentBean() {
		super();
	}

	public ArticleTopicCurrentBean(String topicHeader, String topicType, String topicRegion, Integer topicLikeNum,
			HashMap<Integer, String> topicLikeWho, Integer contentReplyNum, Date topicCreateTime, Date topicUpdateTime,
			String topicStatus, Date accidentTime, String accidentLocation, Double accidentLocationLongitude,
			Double accidentLocationLatitude, String topicContent, Date topicContentUpdateTime, Integer pageViews,
			String updateMessage, MemberBean memberBean, VideoBean videoBean) {
		super();
		this.topicHeader = topicHeader;
		this.topicType = topicType;
		this.topicRegion = topicRegion;
		this.topicLikeNum = topicLikeNum;
		this.topicLikeWho = topicLikeWho;
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
		this.pageViews = pageViews;
		this.updateMessage = updateMessage;
		this.memberBean = memberBean;
		this.videoBean = videoBean;
	}

	@Override
	public String toString() {
		return "ArticleTopicCurrentBean [id=" + id + ", topicHeader=" + topicHeader + ", topicType=" + topicType
				+ ", topicRegion=" + topicRegion + ", topicLikeNum=" + topicLikeNum + ", topicLikeWho=" + topicLikeWho
				+ ", contentReplyNum=" + contentReplyNum + ", topicCreateTime=" + topicCreateTime + ", topicUpdateTime="
				+ topicUpdateTime + ", topicStatus=" + topicStatus + ", accidentTime=" + accidentTime
				+ ", accidentLocation=" + accidentLocation + ", accidentLocationLongitude=" + accidentLocationLongitude
				+ ", accidentLocationLatitude=" + accidentLocationLatitude + ", topicContent=" + topicContent
				+ ", topicContentUpdateTime=" + topicContentUpdateTime + ", pageViews=" + pageViews + ", updateMessage="
				+ updateMessage + ", memberBean=" + memberBean + ", videoBean=" + videoBean + "]";
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

	public HashMap<Integer, String> getTopicLikeWho() {
		return topicLikeWho;
	}

	public void setTopicLikeWho(HashMap<Integer, String> topicLikeWho) {
		this.topicLikeWho = topicLikeWho;
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

	public Integer getPageViews() {
		return pageViews;
	}

	public void setPageViews(Integer pageViews) {
		this.pageViews = pageViews;
	}

	public String getUpdateMessage() {
		return updateMessage;
	}

	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}

	public MemberBean getMemberBean() {
		return memberBean;
	}

	public void setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
	}

	public VideoBean getVideoBean() {
		return videoBean;
	}

	public void setVideoBean(VideoBean videoBean) {
		this.videoBean = videoBean;
	}

}
