package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberTempBean;

@Entity
@Table(schema = "Shop", name = "Refund")
public class RefundBean implements Serializable {

	private static final long serialVersionUID = -5659890739956492348L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RefundID", columnDefinition = "bigint")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateTime", nullable = false, updatable = false)
	private java.util.Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdatedTime", nullable = false)
	private java.util.Date updatedTime;

	@Column(name = "Comment", columnDefinition = "nvarchar(max)", nullable = false, updatable = false)
	private String comment;

	@Column(name = "ProcessStatus", nullable = false)
	private String processStatus;

	@ManyToOne
	@JoinColumn(name = "MemberID", nullable = false, updatable = false)
	private MemberTempBean memberId;

	@OneToMany(mappedBy = "refundId")
	private List<RefundListBean> refundListId;

	public RefundBean() {
		super();
	}

	/**
	 * @param createTime
	 * @param updatedTime
	 * @param comment
	 * @param processStatus
	 * @param memberId
	 */
	public RefundBean(Date createTime, Date updatedTime, String comment, String processStatus, MemberTempBean memberId) {
		super();
		this.createTime = createTime;
		this.updatedTime = updatedTime;
		this.comment = comment;
		this.processStatus = processStatus;
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "RefundBean [id=" + id + ", createTime=" + createTime + ", updatedTime=" + updatedTime + ", comment="
				+ comment + ", processStatus=" + processStatus + ", memberId=" + memberId + "]";
	}

	/**
	 * Check createTime, updatedTime, comment, processStatus ,memberId, null or not.
	 * 
	 * @return true if not null, or NullPointerException if it is.
	 */
	public Boolean isNotNull() {
		this.createTime = Objects.requireNonNull(createTime, "createTime must not be null");
		this.updatedTime = Objects.requireNonNull(updatedTime, "updatedTime must not be null");
		this.comment = Objects.requireNonNull(comment, "comment must not be null");
		this.processStatus = Objects.requireNonNull(processStatus, "processStatus must not be null");
		this.memberId = Objects.requireNonNull(memberId, "memberId must not be null");
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(java.util.Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public MemberTempBean getMemberId() {
		return memberId;
	}

	public void setMemberId(MemberTempBean memberId) {
		this.memberId = memberId;
	}

}
