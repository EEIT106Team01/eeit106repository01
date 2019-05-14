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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_Product_Id")
	private ProductBean productBean;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_Refund_Id")
	private RefundBean refundBean;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_AntiTheftBean_SerialNumber")
	private AntiTheftBean antiTheftBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_CarCareBean_SerialNumber")
	private CarCareBean carCareBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_CarGpsBean_SerialNumber")
	private CarGpsBean carGpsBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_CarRecorderBean_SerialNumber")
	private CarRecorderBean carRecorderBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_CarSeatBean_SerialNumber")
	private CarSeatBean carSeatBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_EmergencyToolBean_SerialNumber")
	private EmergencyToolBean emergencyToolBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_HelmetRecorderBean_SerialNumber")
	private HelmetRecorderBean helmetRecorderBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_MotorcycleGpsBean_SerialNumber")
	private MotorcycleGpsBean motorcycleGpsBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_MotorcycleRecorderBean_SerialNumber")
	private MotorcycleRecorderBean motorcycleRecorderBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_TireGaugeBean_SerialNumber")
	private TireGaugeBean tireGaugeBean;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
