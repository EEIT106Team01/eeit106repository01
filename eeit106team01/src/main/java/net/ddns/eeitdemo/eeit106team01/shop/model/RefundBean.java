package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SHOP_Refund")
public class RefundBean implements Serializable {

	private static final long serialVersionUID = -5659890739956492348L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private java.util.Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private java.util.Date updatedTime;

	@Column(nullable = false, columnDefinition = "nvarchar(max)")
	private String comment;

	@Column(nullable = false)
	private String processStatus;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Member_Id", columnDefinition = "bigint")
	private MemberBeanTest memberBeanTest;

	@OneToMany(mappedBy = "refundBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RefundDetailBean> refundDetailBeans = new ArrayList<RefundDetailBean>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime() {
		this.createTime = new Date(System.currentTimeMillis());
	}

	public java.util.Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime() {
		this.updatedTime = new Date(System.currentTimeMillis());
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

}
