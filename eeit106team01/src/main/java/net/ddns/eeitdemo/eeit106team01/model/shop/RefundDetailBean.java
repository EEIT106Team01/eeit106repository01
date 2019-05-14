package net.ddns.eeitdemo.eeit106team01.model.shop;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RefundDetail")
public class RefundDetailBean implements Serializable{
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_Refund_Id") 
	private RefundBean refund;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_Product_Id") 
	private ProductBean product;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_Item_SerialNumber") 
	private DrivingRecorderBean drivingRecorder;
	
	public RefundDetailBean() {
		super();
	}
	
	public RefundDetailBean(RefundBean refund, ProductBean product, DrivingRecorderBean drivingRecorder) {
		super();
		this.refund = refund;
		this.product = product;
		this.drivingRecorder = drivingRecorder;
	}
	
	public RefundBean getRefund() {
		return refund;
	}
	public void setRefund(RefundBean refund) {
		this.refund = refund;
	}
	public ProductBean getProduct() {
		return product;
	}
	public void setProduct(ProductBean product) {
		this.product = product;
	}
	public DrivingRecorderBean getDrivingRecorder() {
		return drivingRecorder;
	}
	public void setDrivingRecorder(DrivingRecorderBean drivingRecorder) {
		this.drivingRecorder = drivingRecorder;
	}
}
