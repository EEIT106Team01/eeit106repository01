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

import net.ddns.eeitdemo.eeit106team01.shop.MemberBeanTest;

@Entity
public class OrderBean implements Serializable {

	private static final long serialVersionUID = -2108352266354853778L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String payStatus;
	private java.util.Date time;
	private Integer totalPrice;
	private String transationType;
	private String deliverStatus;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_member_Id")
	private MemberBeanTest memberBeanTest;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetailBean> orderDetailList = new ArrayList<OrderDetailBean>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public java.util.Date getTime() {
		return time;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public String getTransationType() {
		return transationType;
	}

	public void setTransationType(String transationType) {
		this.transationType = transationType;
	}

	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
}
