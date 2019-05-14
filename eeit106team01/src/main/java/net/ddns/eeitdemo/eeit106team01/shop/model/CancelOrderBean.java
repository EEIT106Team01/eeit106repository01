package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class CancelOrderBean implements Serializable {

	private static final long serialVersionUID = 6911694576480865027L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private Long MemberId;
	private String PayStatus;
	private java.util.Date Time;
	private Integer TotalPrice;
	private String TransationType;

	public CancelOrderBean() {
		super();
	}

	public CancelOrderBean(Long id, Long memberId, String payStatus, Date time, Integer totalPrice,
			String transationType) {
		super();
		Id = id;
		MemberId = memberId;
		PayStatus = payStatus;
		Time = time;
		TotalPrice = totalPrice;
		TransationType = transationType;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getMemberId() {
		return MemberId;
	}

	public void setMemberId(Long memberId) {
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
