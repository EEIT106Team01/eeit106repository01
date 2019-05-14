package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CancelOrderDetail")
public class CancelOrderDetailBean implements Serializable {

	private static final long serialVersionUID = 3873757388653464094L;
	private CancelOrderBean cancelOrder;
	private String Item_SerialNumber;
	private Long Product_Id;
	private Integer Price;
	private Long DeliverType_Id;

	public CancelOrderDetailBean() {
		super();
	}

	public CancelOrderDetailBean(CancelOrderBean cancelOrder, String item_SerialNumber, Long product_Id,
			Integer price, Long deliverType_Id) {
		super();
		this.cancelOrder = cancelOrder;
		Item_SerialNumber = item_SerialNumber;
		Product_Id = product_Id;
		Price = price;
		DeliverType_Id = deliverType_Id;
	}

	public CancelOrderBean getCancelOrder() {
		return cancelOrder;
	}

	public void setCancelOrder(CancelOrderBean cancelOrder) {
		this.cancelOrder = cancelOrder;
	}

	public String getItem_SerialNumber() {
		return Item_SerialNumber;
	}

	public void setItem_SerialNumber(String item_SerialNumber) {
		Item_SerialNumber = item_SerialNumber;
	}

	public Long getProduct_Id() {
		return Product_Id;
	}

	public void setProduct_Id(Long product_Id) {
		Product_Id = product_Id;
	}

	public Integer getPrice() {
		return Price;
	}

	public void setPrice(Integer price) {
		Price = price;
	}

	public Long getDeliverType_Id() {
		return DeliverType_Id;
	}

	public void setDeliverType_Id(Long deliverType_Id) {
		DeliverType_Id = deliverType_Id;
	}

}
