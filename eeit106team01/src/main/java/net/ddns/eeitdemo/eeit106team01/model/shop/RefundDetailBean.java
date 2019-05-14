package net.ddns.eeitdemo.eeit106team01.model.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="RefundDetail")
public class RefundDetailBean {
	
	@Column(name="Refund_Id")
	private Integer RefundId;
	
	@Column(name="Product_Id")
	private Integer ProductId;
	
	@Column(name="Item_SerialNumber")
	private String ItemSerialNumber;
	
	public Integer getRefundId() {
		return RefundId;
	}
	public void setRefundId(Integer refundId) {
		RefundId = refundId;
	}
	public Integer getProductId() {
		return ProductId;
	}
	public void setProductId(Integer productId) {
		ProductId = productId;
	}
	public String getItemSerialNumber() {
		return ItemSerialNumber;
	}
	public void setItemSerialNumber(String itemSerialNumber) {
		ItemSerialNumber = itemSerialNumber;
	}
}
