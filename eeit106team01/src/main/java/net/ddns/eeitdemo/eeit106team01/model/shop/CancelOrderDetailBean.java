package net.ddns.eeitdemo.eeit106team01.model.shop;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CancelOrderDetail")
public class CancelOrderDetailBean {
	
	private int CancelOrderId;
	private String ItemSerialNumber;
	private int ProductId;
	private int Price;
	private int DeliverTypeId;
	
	public int getCancelOrderId() {
		return CancelOrderId;
	}
	public void setCancelOrderId(int cancelOrderId) {
		CancelOrderId = cancelOrderId;
	}
	public String getItemSerialNumber() {
		return ItemSerialNumber;
	}
	public void setItemSerialNumber(String itemSerialNumber) {
		ItemSerialNumber = itemSerialNumber;
	}
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int getDeliverTypeId() {
		return DeliverTypeId;
	}
	public void setDeliverTypeId(int deliverTypeId) {
		DeliverTypeId = deliverTypeId;
	}
}
