package net.ddns.eeitdemo.eeit106team01.model.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CancelOrder")
public class CancelOrderBean implements Serializable{

	private static final long serialVersionUID = 6911694576480865027L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;
	private Integer MemberId;
	private String PayStatus;
	private java.util.Date Time;
	private Integer TotalPrice;
	private String TransationType;

	private List<CancelOrderBean> cancelOrderList = new ArrayList<>();
	
	public CancelOrderBean() {
		super();
	}
	
	public CancelOrderBean(Integer id, Integer memberId, String payStatus, Date time, Integer totalPrice,
			String transationType) {
		super();
		Id = id;
		MemberId = memberId;
		PayStatus = payStatus;
		Time = time;
		TotalPrice = totalPrice;
		TransationType = transationType;
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

	public List<CancelOrderBean> getCancelOrderList() {
		return cancelOrderList;
	}

	public void setCancelOrderList(List<CancelOrderBean> cancelOrderList) {
		this.cancelOrderList = cancelOrderList;
	}
}
