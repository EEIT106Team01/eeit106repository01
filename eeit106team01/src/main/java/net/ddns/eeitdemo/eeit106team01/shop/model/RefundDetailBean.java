package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import net.ddns.eeitdemo.eeit106team01.shop.model.product.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.AntiTheftBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.CarCareBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.CarGpsBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.CarRecorderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.CarSeatBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.EmergencyToolBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.HelmetRecorderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.MotorcycleGpsBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.MotorcycleRecorderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.TireGaugeBean;

@Entity
public class RefundDetailBean implements Serializable {

	private static final long serialVersionUID = -2001180171846295649L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_Product_Id")
	private ProductBean productBean;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_Refund_Id")
	private RefundBean refundBean;

	// product
	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<AntiTheftBean> antiTheftBean = new ArrayList<AntiTheftBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CarCareBean> carCareBean = new ArrayList<CarCareBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CarGpsBean> carGpsBean = new ArrayList<CarGpsBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CarRecorderBean> carRecorderBean = new ArrayList<CarRecorderBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CarSeatBean> carSeatBean = new ArrayList<CarSeatBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EmergencyToolBean> emergencyToolBean = new ArrayList<EmergencyToolBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<HelmetRecorderBean> helmetRecorderBean = new ArrayList<HelmetRecorderBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MotorcycleGpsBean> motorcycleGpsBean = new ArrayList<MotorcycleGpsBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MotorcycleRecorderBean> motorcycleRecorderBean = new ArrayList<MotorcycleRecorderBean>();

	@OneToMany(mappedBy = "refundDetailBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TireGaugeBean> tireGaugeBean = new ArrayList<TireGaugeBean>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
