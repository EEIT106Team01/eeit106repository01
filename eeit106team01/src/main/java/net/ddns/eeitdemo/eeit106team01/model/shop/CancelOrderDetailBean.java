package net.ddns.eeitdemo.eeit106team01.model.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CancelOrderDetail")
public class CancelOrderDetailBean {
	
	@Column(name="CancelOrder_Id")
	private Integer CancelOrderId;
	@Column(name="Item_SerialNumber")
	private String ItemSerialNumber;
	@Column(name="Product_Id")
	private Integer ProductId;
	private Integer Price;
	@Column(name="DeliverType_Id")
	private Integer DeliverTypeId;
	
	
	public Integer getCancelOrderId() {
		return CancelOrderId;
	}
	public void setCancelOrderId(Integer cancelOrderId) {
		CancelOrderId = cancelOrderId;
	}
	public String getItemSerialNumber() {
		return ItemSerialNumber;
	}
	public void setItemSerialNumber(String itemSerialNumber) {
		ItemSerialNumber = itemSerialNumber;
	}
	public Integer getProductId() {
		return ProductId;
	}
	public void setProductId(Integer productId) {
		ProductId = productId;
	}
	public Integer getPrice() {
		return Price;
	}
	public void setPrice(Integer price) {
		Price = price;
	}
	public Integer getDeliverTypeId() {
		return DeliverTypeId;
	}
	public void setDeliverTypeId(Integer deliverTypeId) {
		DeliverTypeId = deliverTypeId;
	}

}
