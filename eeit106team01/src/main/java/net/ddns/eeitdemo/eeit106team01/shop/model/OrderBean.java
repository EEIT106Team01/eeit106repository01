package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import net.ddns.eeitdemo.eeit106team01.shop.MemberBeanTest;

@Entity
@Table(name="Order")
public class OrderBean implements Serializable{

	private static final long serialVersionUID = -2108352266354853778L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_member_Id") 
	private MemberBeanTest memberBeanTest;
	
	private String PayStatus;
	private java.util.Date Time;
	private Integer TotalPrice;
	private String TransationType;
	private String DeliverStatus;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetailBean> orderDetailList = new ArrayList<OrderDetailBean>();
	
	public OrderBean() {
		super();
	}
	
	public OrderBean(Long id, MemberBeanTest memberbeantest, String payStatus, Date time, Integer totalPrice,
			String transationType, String deliverStatus) {
		super();
		Id = id;
		memberBeanTest = memberbeantest;
		PayStatus = payStatus;
		Time = time;
		TotalPrice = totalPrice;
		TransationType = transationType;
		DeliverStatus = deliverStatus;
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
	public void setMemberId(MemberBeanTest memberbeantest) {
		this.memberBeanTest = memberbeantest;
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
	public List<OrderDetailBean> getOrderList() {
		return orderDetailList;
	}
	public void setOrderList(List<OrderDetailBean> OrderDetails) {
		this.orderDetailList = OrderDetails;
	}
}
