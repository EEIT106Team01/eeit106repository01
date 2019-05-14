package net.ddns.eeitdemo.eeit106team01.model.shop;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CancelOrderDetail")
public class CancelOrderDetailBean implements Serializable {

	private static final long serialVersionUID = 3873757388653464094L;
	private CancelOrderBean cancelOrder;
	private String Item_SerialNumber;
	private Integer Product_Id;
	private Integer Price;
	private Integer DeliverType_Id;

	public CancelOrderDetailBean() {
		super();
	}

	public CancelOrderDetailBean(CancelOrderBean cancelOrder, String item_SerialNumber, Integer product_Id,
			Integer price, Integer deliverType_Id) {
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

	public Integer getProduct_Id() {
		return Product_Id;
	}

	public void setProduct_Id(Integer product_Id) {
		Product_Id = product_Id;
	}

	public Integer getPrice() {
		return Price;
	}

	public void setPrice(Integer price) {
		Price = price;
	}

	public Integer getDeliverType_Id() {
		return DeliverType_Id;
	}

	public void setDeliverType_Id(Integer deliverType_Id) {
		DeliverType_Id = deliverType_Id;
	}

}
