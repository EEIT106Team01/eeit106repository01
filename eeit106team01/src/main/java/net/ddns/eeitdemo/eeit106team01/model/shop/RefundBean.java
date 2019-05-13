package net.ddns.eeitdemo.eeit106team01.model.shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Refund")
public class RefundBean {
	
	private int Id;
	private int MemberId;
	private String Comment;
	private String ProcessStatus;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getMemberId() {
		return MemberId;
	}
	public void setMemberId(int memberId) {
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

}
