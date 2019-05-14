package net.ddns.eeitdemo.eeit106team01.model.shop;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.ddns.eeitdemo.eeit106team01.model.shop.product.ProductBean;
import net.ddns.eeitdemo.eeit106team01.model.shop.product.automotive.car.CarCareBean;
import net.ddns.eeitdemo.eeit106team01.model.shop.product.automotive.car.CarGpsBean;
import net.ddns.eeitdemo.eeit106team01.model.shop.product.automotive.car.CarRecorderBean;
import net.ddns.eeitdemo.eeit106team01.model.shop.product.automotive.car.CarSeatBean;

@Entity
@Table(name = "RefundDetail")
public class RefundDetailBean implements Serializable {

	private static final long serialVersionUID = -2001180171846295649L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_Product_Id")
	private ProductBean product;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_carCare_SerialNumber")
	private CarCareBean carCareBean;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_carGps_SerialNumber")
	private CarGpsBean carGpsBean;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_carRecorder_SerialNumber")
	private CarRecorderBean carRecorderBean;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_carSeat_SerialNumber")
	private CarSeatBean carSeatBean;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_Refund_Id")
	private RefundBean refund;
	
	public RefundDetailBean() {
		super();
	}

	public RefundDetailBean(RefundBean refund, ProductBean product, CarCareBean carCareBean, CarGpsBean carGpsBean,
			CarRecorderBean carRecorderBean, CarSeatBean carSeatBean) {
		super();
		this.refund = refund;
		this.product = product;
		this.carCareBean = carCareBean;
		this.carGpsBean = carGpsBean;
		this.carRecorderBean = carRecorderBean;
		this.carSeatBean = carSeatBean;
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

	public CarCareBean getCarCareBean() {
		return carCareBean;
	}

	public void setCarCareBean(CarCareBean carCareBean) {
		this.carCareBean = carCareBean;
	}

	public CarGpsBean getCarGpsBean() {
		return carGpsBean;
	}

	public void setCarGpsBean(CarGpsBean carGpsBean) {
		this.carGpsBean = carGpsBean;
	}

	public CarRecorderBean getCarRecorderBean() {
		return carRecorderBean;
	}

	public void setCarRecorderBean(CarRecorderBean carRecorderBean) {
		this.carRecorderBean = carRecorderBean;
	}

	public CarSeatBean getCarSeatBean() {
		return carSeatBean;
	}

	public void setCarSeatBean(CarSeatBean carSeatBean) {
		this.carSeatBean = carSeatBean;
	}

	

	
}
