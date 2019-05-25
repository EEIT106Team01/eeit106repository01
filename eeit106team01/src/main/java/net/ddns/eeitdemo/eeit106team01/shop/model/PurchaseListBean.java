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
import javax.persistence.Table;

@Entity
@Table(schema = "Shop", name = "PurchaseList")
public class PurchaseListBean implements Serializable {

	private static final long serialVersionUID = 7412479144382182019L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PurchaseListID", columnDefinition = "bigint")
	private Long id;

	@Column(name = "Price", nullable = false, updatable = false)
	private Integer price;

	@Column(name = "SerialNumber", nullable = false, updatable = false)
	private String serialNumber;

	@ManyToOne
	@JoinColumn(name = "PurchaseID", columnDefinition = "bigint", nullable = false, updatable = false)
	private PurchaseBean purchaseId;

	@ManyToOne
	@JoinColumn(name = "ProductID", columnDefinition = "bigint", nullable = false, updatable = false)
	private ProductBean productId;

	public PurchaseListBean() {
		super();
	}

	/**
	 * @param price
	 * @param serialNumber
	 * @param purchaseId
	 * @param productId
	 */
	public PurchaseListBean(Integer price, String serialNumber, PurchaseBean purchaseId, ProductBean productId) {
		super();
		this.price = price;
		this.serialNumber = serialNumber;
		this.purchaseId = purchaseId;
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "PurchaseListBean [id=" + id + ", price=" + price + ", serialNumber=" + serialNumber + ", purchaseId="
				+ purchaseId + ", productId=" + productId + "]";
	}

	/**
	 * Check price, serialNumber, purchaseId, productId null or not.
	 * 
	 * @return true if not null, or NullPointerException if it is.
	 */
	public Boolean isNotNull() {
		this.price = Objects.requireNonNull(price, "price must not be null");
		this.serialNumber = Objects.requireNonNull(serialNumber, "serialNumber must not be null");
		this.purchaseId = Objects.requireNonNull(purchaseId, "purchaseId must not be null");
		this.productId = Objects.requireNonNull(productId, "productId must not be null");
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public PurchaseBean getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(PurchaseBean purchaseId) {
		this.purchaseId = purchaseId;
	}

	public ProductBean getProductId() {
		return productId;
	}

	public void setProductId(ProductBean productId) {
		this.productId = productId;
	}

}
