package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class CancelOrderDetailBean implements Serializable {

	private static final long serialVersionUID = 3873757388653464094L;
	
	private CancelOrderBean cancelOrder;
	private String itemSerialNumber;
	private Long productId;
	private Integer price;
	private Long deliverTypeId;
	
	public CancelOrderBean getCancelOrder() {
		return cancelOrder;
	}
	public void setCancelOrder(CancelOrderBean cancelOrder) {
		this.cancelOrder = cancelOrder;
	}
	public String getItemSerialNumber() {
		return itemSerialNumber;
	}
	public void setItemSerialNumber(String itemSerialNumber) {
		this.itemSerialNumber = itemSerialNumber;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Long getDeliverTypeId() {
		return deliverTypeId;
	}
	public void setDeliverTypeId(Long deliverTypeId) {
		this.deliverTypeId = deliverTypeId;
	}

}
