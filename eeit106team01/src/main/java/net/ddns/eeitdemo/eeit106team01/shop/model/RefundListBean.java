package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "Shop", name = "RefundList")
public class RefundListBean implements Serializable {

	private static final long serialVersionUID = -2001180171846295649L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RefundListID", columnDefinition = "bigint")
	private Long id;

	@OneToOne
	@JoinColumn(name = "PurchaseListID", columnDefinition = "bigint", nullable = false, updatable = false, unique = true)
	private PurchaseListBean purchaseListId;

	@ManyToOne
	@JoinColumn(name = "RefundID", columnDefinition = "bigint", nullable = false, updatable = false)
	private RefundBean refundId;

	public RefundListBean() {
		super();
	}

	/**
	 * @param purchaseListId
	 * @param refundId
	 */
	public RefundListBean(PurchaseListBean purchaseListId, RefundBean refundId) {
		super();
		this.purchaseListId = purchaseListId;
		this.refundId = refundId;
	}

	@Override
	public String toString() {
		return "RefundListBean [id=" + id + ", purchaseListId=" + purchaseListId + ", refundId=" + refundId + "]";
	}

	/**
	 * Check purchaseListId, refundId null or not.
	 * 
	 * @return true if not null, or NullPointerException if it is.
	 */
	public Boolean isNotNull() {
		this.purchaseListId = Objects.requireNonNull(purchaseListId, "purchaseListId must not be null");
		this.refundId = Objects.requireNonNull(refundId, "refundId must not be null");
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PurchaseListBean getPurchaseListId() {
		return purchaseListId;
	}

	public void setPurchaseListId(PurchaseListBean purchaseListId) {
		this.purchaseListId = purchaseListId;
	}

	public RefundBean getRefundId() {
		return refundId;
	}

	public void setRefundId(RefundBean refundId) {
		this.refundId = refundId;
	}

}
