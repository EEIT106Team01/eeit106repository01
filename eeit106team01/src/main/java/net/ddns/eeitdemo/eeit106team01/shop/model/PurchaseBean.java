package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(schema = "Shop", name = "Purchase")
public class PurchaseBean implements Serializable {

	private static final long serialVersionUID = -2108352266354853778L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PurchaseID", columnDefinition = "bigint")
	private Long id;

	@Column(name = "PayStatus", nullable = false)
	private String payStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateTime", nullable = false, updatable = false)
	private java.util.Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdatedTime", nullable = false)
	private java.util.Date updatedTime;

	@Column(name = "ProductTotalPrice", nullable = false, updatable = false)
	private Integer productTotalPrice;

	@Column(name = "DeliverStatus", nullable = false)
	private String deliverStatus;

	@Column(name = "DeliverType", nullable = false, updatable = false)
	private String deliverType;

	@Column(name = "DeliverPrice", nullable = false, updatable = false)
	private Integer deliverPrice;

	@Column(name = "ReceiverInformation", columnDefinition = "varbinary(max)", nullable = false, updatable = false)
	private HashMap<String, String> receiverInformation;

	@ManyToOne
	@JoinColumn(name = "MemberID", columnDefinition = "bigint", nullable = false, updatable = false)
	private Member memberId;

	@OneToMany(mappedBy = "purchaseId")
	private List<PurchaseListBean> purchaseListId;

	public PurchaseBean() {
		super();
	}

	/**
	 * @param payStatus
	 * @param createTime
	 * @param updatedTime
	 * @param productTotalPrice
	 * @param deliverStatus
	 * @param deliverType
	 * @param deliverPrice
	 * @param receiverInformation
	 * @param memberId
	 */
	public PurchaseBean(String payStatus, Date createTime, Date updatedTime, Integer productTotalPrice,
			String deliverStatus, String deliverType, Integer deliverPrice, HashMap<String, String> receiverInformation,
			Member memberId) {
		super();
		this.payStatus = payStatus;
		this.createTime = createTime;
		this.updatedTime = updatedTime;
		this.productTotalPrice = productTotalPrice;
		this.deliverStatus = deliverStatus;
		this.deliverType = deliverType;
		this.deliverPrice = deliverPrice;
		this.receiverInformation = receiverInformation;
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "PurchaseBean [id=" + id + ", payStatus=" + payStatus + ", createTime=" + createTime + ", updatedTime="
				+ updatedTime + ", productTotalPrice=" + productTotalPrice + ", deliverStatus=" + deliverStatus
				+ ", deliverType=" + deliverType + ", deliverPrice=" + deliverPrice + ", receiverInformation="
				+ receiverInformation + ", memberId=" + memberId + "]";
	}

	/**
	 * Check createTime, updatedTime, payStatus, productTotalPrice ,deliverStatus,
	 * deliverType, deliverPrice, receiverInformation, memberId null or not.
	 * 
	 * @return true if not null, or NullPointerException if it is.
	 */
	public Boolean isNotNull() {
		this.createTime = Objects.requireNonNull(createTime, "createTime must not be null");
		this.updatedTime = Objects.requireNonNull(updatedTime, "updatedTime must not be null");
		this.payStatus = Objects.requireNonNull(payStatus, "payStatus must not be null");
		this.productTotalPrice = Objects.requireNonNull(productTotalPrice, "productTotalPrice must not be null");
		this.deliverStatus = Objects.requireNonNull(deliverStatus, "deliverStatus must not be null");
		this.deliverType = Objects.requireNonNull(deliverType, "deliverType must not be null");
		this.deliverPrice = Objects.requireNonNull(deliverPrice, "deliverPrice must not be null");
		this.receiverInformation = Objects.requireNonNull(receiverInformation, "receiverInformation must not be null");
		this.memberId = Objects.requireNonNull(memberId, "memberId must not be null");
		return true;
	}

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

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(java.util.Date updatedTime) {
		this.updatedTime = updatedTime;
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

	public HashMap<String, String> getReceiverInformation() {
		return receiverInformation;
	}

	public void setReceiverInformation(HashMap<String, String> receiverInformation) {
		this.receiverInformation = receiverInformation;
	}

	public Member getMemberId() {
		return memberId;
	}

	public void setMemberId(Member memberId) {
		this.memberId = memberId;
	}

}
