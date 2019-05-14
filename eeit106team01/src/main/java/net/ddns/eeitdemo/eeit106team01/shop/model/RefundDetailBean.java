package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.ddns.eeitdemo.eeit106team01.shop.model.product.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.AntiTheftBean;

@Entity
public class RefundDetailBean implements Serializable {

	private static final long serialVersionUID = -2001180171846295649L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_Product_Id")
	private ProductBean product;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_antiTheftBean_SerialNumber")
	private AntiTheftBean antiTheftBean;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_Refund_Id")
	private RefundBean refund;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "FK_carCare_SerialNumber")
//	@OneToOne(mappedBy = "refunddetailbean")
//	private CarCareBean carCareBean;
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "FK_carGps_SerialNumber")
//	private CarGpsBean carGpsBean;
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "FK_carRecorder_SerialNumber")
//	private CarRecorderBean carRecorderBean;
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "FK_carSeat_SerialNumber")
//	private CarSeatBean carSeatBean;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductBean getProduct() {
		return product;
	}

	public void setProduct(ProductBean product) {
		this.product = product;
	}

	public AntiTheftBean getAntiTheftBean() {
		return antiTheftBean;
	}

	public void setAntiTheftBean(AntiTheftBean antiTheftBean) {
		this.antiTheftBean = antiTheftBean;
	}

	public RefundBean getRefund() {
		return refund;
	}

	public void setRefund(RefundBean refund) {
		this.refund = refund;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
