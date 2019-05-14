package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import net.ddns.eeitdemo.eeit106team01.shop.MemberBeanTest;

@Entity
public class RefundBean implements Serializable {

	private static final long serialVersionUID = -5659890739956492348L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String comment;
	private String processStatus;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_member_Id")
	private MemberBeanTest memberBeanTest;

	@OneToMany(mappedBy = "refundBean", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<RefundDetailBean> refundDetailBeans = new ArrayList<RefundDetailBean>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
