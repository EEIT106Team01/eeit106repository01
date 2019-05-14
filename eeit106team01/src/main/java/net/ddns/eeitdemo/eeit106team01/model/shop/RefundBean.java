package net.ddns.eeitdemo.eeit106team01.model.shop;

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
	private Integer Id;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_member_Id")
	private MemberBeanTest memberBeanTest;
	private String Comment;
	private String ProcessStatus;
	@OneToMany(mappedBy = "refund", cascade = CascadeType.ALL)
	private List<RefundBean> RefundList = new ArrayList<>();
	
	public RefundBean() {
		super();

	}
	public RefundBean(Integer id, MemberBeanTest memberbeantest, String comment, String processStatus) {
		super();
		Id = id;
		memberBeanTest = memberbeantest;
		Comment = comment;
		ProcessStatus = processStatus;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
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
	public List<RefundBean> getRefundList() {
		return RefundList;
	}
	public void setRefundList(List<RefundBean> refundList) {
		RefundList = refundList;
	}
}
