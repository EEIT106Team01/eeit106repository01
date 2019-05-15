package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SHOP_Order")
public class OrderBean implements Serializable {

	private static final long serialVersionUID = -2108352266354853778L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String payStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private java.util.Date time;

	@Column(nullable = false)
	private Integer productTotalPrice;

	@Column(nullable = false)
	private String deliverStatus;

	@Column(nullable = false)
	private String deliverType;

	@Column(nullable = false)
	private Integer deliverPrice;

	@Column(nullable = false, columnDefinition = "nvarchar(max)")
	private String receiverInformation;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Member_Id")
	private MemberBeanTest memberBeanTest;

	@OneToMany(mappedBy = "orderBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetailBean> orderDetailBeans = new ArrayList<OrderDetailBean>();

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

	public void setTime() {
		this.time = new Date(System.currentTimeMillis());
	}

	public Integer getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(Integer productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public Integer getDeliverPrice() {
		return deliverPrice;
	}

	public void setDeliverPrice(Integer deliverPrice) {
		this.deliverPrice = deliverPrice;
	}

	public String getReceiverInformation() {
		return receiverInformation;
	}

	public void setReceiverInformation(String receiverInformation) {
		this.receiverInformation = receiverInformation;
	}

	public MemberBeanTest getMemberBeanTest() {
		return memberBeanTest;
	}

	public void setMemberBeanTest(MemberBeanTest memberBeanTest) {
		this.memberBeanTest = memberBeanTest;
	}

	public List<OrderDetailBean> getOrderDetailBeans() {
		return orderDetailBeans;
	}

	public void setOrderDetailBeans(List<OrderDetailBean> orderDetailBeans) {
		this.orderDetailBeans = orderDetailBeans;
	}

}
