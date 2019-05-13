package net.ddns.eeitdemo.eeit106team01.model.shop;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="RefundDetail")
public class RefundDetailBean {
	
	private int Refund_Id;
	private int Product_Id;
	private String ItemSerialNumber;
	
	public int getRefund_Id() {
		return Refund_Id;
	}
	public void setRefund_Id(int refund_Id) {
		Refund_Id = refund_Id;
	}
	public int getProduct_Id() {
		return Product_Id;
	}
	public void setProduct_Id(int product_Id) {
		Product_Id = product_Id;
	}
	public String getItemSerialNumber() {
		return ItemSerialNumber;
	}
	public void setItemSerialNumber(String itemSerialNumber) {
		ItemSerialNumber = itemSerialNumber;
	}

}
