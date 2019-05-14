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
@Table(name="Order")
public class OrderBean implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;
	private Integer MemberId;
	private String PayStatus;
	private java.util.Date Time;
	private Integer TotalPrice;
	private String TransationType;
	private String DeliverStatus;
	@OneToMany(mappedBy = "OrderDetail", cascade = CascadeType.ALL)
	private List<OrderBean> OrderList = new ArrayList<>();
	
	public OrderBean() {
		super();
	}
	
	public OrderBean(Integer id, Integer memberId, String payStatus, Date time, Integer totalPrice,
			String transationType, String deliverStatus) {
		super();
		Id = id;
		MemberId = memberId;
		PayStatus = payStatus;
		Time = time;
		TotalPrice = totalPrice;
		TransationType = transationType;
		DeliverStatus = deliverStatus;
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
	public String getDeliverStatus() {
		return DeliverStatus;
	}
	public void setDeliverStatus(String deliverStatus) {
		DeliverStatus = deliverStatus;
	}
	public List<OrderBean> getOrderList() {
		return OrderList;
	}
	public void setOrderList(List<OrderBean> orderList) {
		OrderList = orderList;
	}
}
