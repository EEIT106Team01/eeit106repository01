package net.ddns.eeitdemo.eeit106team01.model.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CancelOrder")
public class CancelOrderBean {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;
	@Column(name="Member_Id")
	private Integer MemberId;
	private String PayStatus;
	private java.util.Date Time;
	private Integer TotalPrice;
	private String TransationType;
	
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
	public String getPayStatus() {
		return PayStatus;
	}
	public void setPayStatus(String payStatus) {
		PayStatus = payStatus;
	}
	public java.util.Date getTime() {
		return Time;
	}
	public void setTime(java.util.Date time) {
		Time = time;
	}
	public Integer getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		TotalPrice = totalPrice;
	}
	public String getTransationType() {
		return TransationType;
	}
	public void setTransationType(String transationType) {
		TransationType = transationType;
	}

}
