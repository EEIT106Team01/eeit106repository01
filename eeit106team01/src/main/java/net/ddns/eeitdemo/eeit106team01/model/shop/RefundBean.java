package net.ddns.eeitdemo.eeit106team01.model.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Refund")
public class RefundBean implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;
	private Integer MemberId;
	private String Comment;
	private String ProcessStatus;
	@OneToMany(mappedBy = "RefundDetail", cascade = CascadeType.ALL)
	private List<RefundBean> RefundList = new ArrayList<>();
	
	public RefundBean() {
		super();

	}
	public RefundBean(Integer id, Integer memberId, String comment, String processStatus) {
		super();
		Id = id;
		MemberId = memberId;
		Comment = comment;
		ProcessStatus = processStatus;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getMemberId() {
		return MemberId;
	}
	public void setMemberId(Integer memberId) {
		MemberId = memberId;
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
