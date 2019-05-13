package net.ddns.eeitdemo.eeit106team01.model.shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CancelOrder")
public class CancelOrderBean {
	
	private int Id;
	private int MemberId;
	private String PayStatus;
	private java.util.Date Time;
	private int TotalPrice;
	private String TransationType;
	
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
	public int getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		TotalPrice = totalPrice;
	}
	public String getTransationType() {
		return TransationType;
	}
	public void setTransationType(String transationType) {
		TransationType = transationType;
	}

}
