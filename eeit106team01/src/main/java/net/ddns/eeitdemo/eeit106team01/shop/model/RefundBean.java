package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.ddns.eeitdemo.eeit106team01.model.MemberBeanTest;

@Entity
@Table(name="Refund")
public class RefundBean implements Serializable{

	private static final long serialVersionUID = -5659890739956492348L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_member_Id")
	private MemberBeanTest memberBeanTest;
	
	private String Comment;
	private String ProcessStatus;
	
	@OneToMany(mappedBy = "refund", cascade = CascadeType.ALL)
	private List<RefundDetailBean> RefundList = new ArrayList<RefundDetailBean>();
	
	public RefundBean() {
		super();

	}
	public RefundBean(Long id, MemberBeanTest memberbeantest, String comment, String processStatus) {
		super();
		Id = id;
		memberBeanTest = memberbeantest;
		Comment = comment;
		ProcessStatus = processStatus;
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public MemberBeanTest getMemberId() {
		return memberBeanTest;
	}
	public void setMemberId(MemberBeanTest memberBeanTest) {
		this.memberBeanTest = memberBeanTest;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getProcessStatus() {
		return ProcessStatus;
	}
	public void setProcessStatus(String processStatus) {
		ProcessStatus = processStatus;
	}
	public List<RefundDetailBean> getRefundList() {
		return RefundList;
	}
	public void setRefundList(List<RefundDetailBean> refundList) {
		RefundList = refundList;
	}
}
