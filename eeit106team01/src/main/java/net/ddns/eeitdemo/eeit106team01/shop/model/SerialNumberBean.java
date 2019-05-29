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
@Table(schema = "Shop", name = "SerialNumber")
public class SerialNumberBean implements Serializable {

	private static final long serialVersionUID = 7534238043511035113L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SerialNumberID", columnDefinition = "bigint")
	private Long id;

	@Column(name = "SerialNumber", nullable = false, unique = true, updatable = false)
	private String serialNumber;

	@Column(name = "AvailabilityStatus", nullable = false)
	private String availabilityStatus;

	@ManyToOne
	@JoinColumn(name = "ProductID", columnDefinition = "bigint", nullable = false)
	private ProductBean productId;

	public SerialNumberBean() {
		super();
	}

	/**
	 * @param serialNumber
	 * @param availabilityStatus
	 * @param productId
	 */
	public SerialNumberBean(String serialNumber, String availabilityStatus, ProductBean productId) {
		super();
		this.serialNumber = serialNumber;
		this.availabilityStatus = availabilityStatus;
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "SerialNumberBean [id=" + id + ", serialNumber=" + serialNumber + ", availabilityStatus="
				+ availabilityStatus + ", productId=" + productId + "]";
	}

	/**
	 * Check serialNumber, availabilityStatus, productId null or not.
	 * 
	 * @return true if not null, or NullPointerException if it is.
	 */
	public Boolean isNotNull() {
		this.serialNumber = Objects.requireNonNull(serialNumber, "serialNumber must not be null");
		this.availabilityStatus = Objects.requireNonNull(availabilityStatus, "availabilityStatus must not be null");
		this.productId = Objects.requireNonNull(productId, "productId must not be null");
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public ProductBean getProductId() {
		return productId;
	}

	public void setProductId(ProductBean productId) {
		this.productId = productId;
	}

}
