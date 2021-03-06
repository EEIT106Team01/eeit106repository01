package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "video")
public class VideoBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id")
	private MemberTempBean memberBean;
	private java.util.Date uploadTime;
	@Column(columnDefinition = "nvarchar(255)")
	private String videoStatus;
	@Column(columnDefinition = "nvarchar(255)")
	private String videoURI;
	@Column(columnDefinition = "nvarchar(255)")
	private String thumbnailURI;
	@Column(columnDefinition = "nvarchar(255)")
	private String gifURI;
	private Double videoLength;
	@Column(columnDefinition = "nvarchar(255)")
	private String updateMessage;
	private String vttURI;
	private String originResolution;

	public VideoBean() {
		super();
	}

	public VideoBean(MemberTempBean memberBean, Date uploadTime, String videoStatus, String videoURI, String thumbnailURI,
			String gifURI, Double videoLength, String updateMessage) {
		super();
		this.memberBean = memberBean;
		this.uploadTime = uploadTime;
		this.videoStatus = videoStatus;
		this.videoURI = videoURI;
		this.thumbnailURI = thumbnailURI;
		this.gifURI = gifURI;
		this.videoLength = videoLength;
		this.updateMessage = updateMessage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MemberTempBean getMemberBean() {
		return memberBean;
	}

	public void setMemberBean(MemberTempBean memberBean) {
		this.memberBean = memberBean;
	}

	public java.util.Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(java.util.Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(String videoStatus) {
		this.videoStatus = videoStatus;
	}

	public String getVideoURI() {
		return videoURI;
	}

	public void setVideoURI(String videoURI) {
		this.videoURI = videoURI;
	}

	public String getThumbnailURI() {
		return thumbnailURI;
	}

	public void setThumbnailURI(String thumbnailURI) {
		this.thumbnailURI = thumbnailURI;
	}

	public String getGifURI() {
		return gifURI;
	}

	public void setGifURI(String gifURI) {
		this.gifURI = gifURI;
	}

	public Double getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(Double videoLength) {
		this.videoLength = videoLength;
	}

	public String getUpdateMessage() {
		return updateMessage;
	}

	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}


	/**
	 * @return the vttURI
	 */
	public String getVttURI() {
		return vttURI;
	}

	/**
	 * @param vttURI the vttURI to set
	 */
	public void setVttURI(String vttURI) {
		this.vttURI = vttURI;
	}

	/**
	 * @return the originResolution
	 */
	public String getOriginResolution() {
		return originResolution;
	}

	/**
	 * @param originResolution the originResolution to set
	 */
	public void setOriginResolution(String originResolution) {
		this.originResolution = originResolution;
	}
}
