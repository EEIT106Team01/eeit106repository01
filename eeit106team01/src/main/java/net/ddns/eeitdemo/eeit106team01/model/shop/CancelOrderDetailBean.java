package net.ddns.eeitdemo.eeit106team01.model.shop;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CancelOrderDetail")
public class CancelOrderDetailBean implements Serializable{

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_CancelOrder_Id") 
	private CancelOrderBean cancelOrder;
	private String ItemSerialNumber;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_Product_Id") 
	private ProductBean product;
	private Integer Price;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_DeliverType_Id") 
	private DeliverTypeBean deliverType;
	
	public CancelOrderDetailBean() {
		super();
	}
	
	public CancelOrderDetailBean(CancelOrderBean cancelOrder, String itemSerialNumber, ProductBean product,
			Integer price, DeliverTypeBean deliverType) {
		super();
		this.cancelOrder = cancelOrder;
		ItemSerialNumber = itemSerialNumber;
		this.product = product;
		Price = price;
		this.deliverType = deliverType;
	}
	
	public CancelOrderBean getCancelOrder() {
		return cancelOrder;
	}
	public void setCancelOrder(CancelOrderBean cancelOrder) {
		this.cancelOrder = cancelOrder;
	}
	public String getItemSerialNumber() {
		return ItemSerialNumber;
	}
	public void setItemSerialNumber(String itemSerialNumber) {
		ItemSerialNumber = itemSerialNumber;
	}
	public ProductBean getProduct() {
		return product;
	}
	public void setProduct(ProductBean product) {
		this.product = product;
	}
	public Integer getPrice() {
		return Price;
	}
	public void setPrice(Integer price) {
		Price = price;
	}
	public DeliverTypeBean getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(DeliverTypeBean deliverType) {
		this.deliverType = deliverType;
	}
}
