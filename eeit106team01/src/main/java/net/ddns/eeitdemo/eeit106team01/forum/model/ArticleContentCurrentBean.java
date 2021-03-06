package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.Date;
import java.util.HashMap;

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
@Table(name = "ArticleContentCurrent")
public class ArticleContentCurrentBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer contentLikeNum;
	private HashMap<Integer, String> contentLikeWho;
	private Integer contentReplyNum;
	private java.util.Date contentCreateTime;
	private java.util.Date contentUpdateTime;
	private String contentStatus;
	@Column(columnDefinition = "nvarchar(max)")
	private String contentContent;
	@Column(columnDefinition = "nvarchar(255)")
	private String updateMessage;

//	articleTopicPK;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "articleTopicCurrent_id")
	private ArticleTopicCurrentBean articleTopicCurrent;

//	articleContentPK;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reply_id")
	private ArticleContentCurrentBean reply;

//	memberPK
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id")
	private MemberTempBean memberBean;

//	vedioPK
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "video_id")
	private VideoBean videoBean;

	public ArticleContentCurrentBean() {
		super();
	}

	public ArticleContentCurrentBean(Integer contentLikeNum, Integer contentReplyNum, Date contentCreateTime,
			Date contentUpdateTime, String contentStatus, String contentContent, String updateMessage,
			ArticleTopicCurrentBean articleTopicCurrent, ArticleContentCurrentBean reply, MemberTempBean memberBean,
			VideoBean videoBean) {
		super();
		this.contentLikeNum = contentLikeNum;
		this.contentReplyNum = contentReplyNum;
		this.contentCreateTime = contentCreateTime;
		this.contentUpdateTime = contentUpdateTime;
		this.contentStatus = contentStatus;
		this.contentContent = contentContent;
		this.updateMessage = updateMessage;
		this.articleTopicCurrent = articleTopicCurrent;
		this.reply = reply;
		this.memberBean = memberBean;
		this.videoBean = videoBean;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContentLikeNum() {
		return contentLikeNum;
	}

	public void setContentLikeNum(Integer contentLikeNum) {
		this.contentLikeNum = contentLikeNum;
	}

	public HashMap<Integer, String> getContentLikeWho() {
		return contentLikeWho;
	}

	public void setContentLikeWho(HashMap<Integer, String> contentLikeWho) {
		this.contentLikeWho = contentLikeWho;
	}

	public Integer getContentReplyNum() {
		return contentReplyNum;
	}

	public void setContentReplyNum(Integer contentReplyNum) {
		this.contentReplyNum = contentReplyNum;
	}

	public java.util.Date getContentCreateTime() {
		return contentCreateTime;
	}

	public void setContentCreateTime(java.util.Date contentCreateTime) {
		this.contentCreateTime = contentCreateTime;
	}

	public java.util.Date getContentUpdateTime() {
		return contentUpdateTime;
	}

	public void setContentUpdateTime(java.util.Date contentUpdateTime) {
		this.contentUpdateTime = contentUpdateTime;
	}

	public String getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}

	public String getContentContent() {
		return contentContent;
	}

	public void setContentContent(String contentContent) {
		this.contentContent = contentContent;
	}

	public String getUpdateMessage() {
		return updateMessage;
	}

	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}

	public ArticleTopicCurrentBean getArticleTopicCurrent() {
		return articleTopicCurrent;
	}

	public void setArticleTopicCurrent(ArticleTopicCurrentBean articleTopicCurrent) {
		this.articleTopicCurrent = articleTopicCurrent;
	}

	public ArticleContentCurrentBean getReply() {
		return reply;
	}

	public void setReply(ArticleContentCurrentBean reply) {
		this.reply = reply;
	}

	public MemberTempBean getMemberBean() {
		return memberBean;
	}

	public void setMemberBean(MemberTempBean memberBean) {
		this.memberBean = memberBean;
	}

	public VideoBean getVideoBean() {
		return videoBean;
	}

	public void setVideoBean(VideoBean videoBean) {
		this.videoBean = videoBean;
	}
}
