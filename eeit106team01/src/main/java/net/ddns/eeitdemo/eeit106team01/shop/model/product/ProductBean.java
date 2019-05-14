package net.ddns.eeitdemo.eeit106team01.shop.model.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundDetailBean;
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

/**
 * @author 冒竣瑋 - Entity for Product.
 */
@Entity
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 8349717092729742091L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private java.util.Date date;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "productBean")
	List<ReviewBean> reviewBeans = new ArrayList<ReviewBean>();

	@OneToMany(mappedBy = "productBean")
	List<OrderDetailBean> orderDetailBean = new ArrayList<OrderDetailBean>();

	@OneToMany(mappedBy = "productBean")
	List<RefundDetailBean> refundDetailBeans = new ArrayList<RefundDetailBean>();

	// Product - Automotive
	@OneToMany(mappedBy = "productBean")
	List<AntiTheftBean> antiTheftBeans = new ArrayList<AntiTheftBean>();

	@OneToMany(mappedBy = "productBean")
	List<CarCareBean> carCareBeans = new ArrayList<CarCareBean>();

	@OneToMany(mappedBy = "productBean")
	List<CarGpsBean> carGpsBeans = new ArrayList<CarGpsBean>();

	@OneToMany(mappedBy = "productBean")
	List<CarRecorderBean> carRecorderBeans = new ArrayList<CarRecorderBean>();

	@OneToMany(mappedBy = "productBean")
	List<CarSeatBean> carSeatBeans = new ArrayList<CarSeatBean>();

	@OneToMany(mappedBy = "productBean")
	List<EmergencyToolBean> emergencyToolBeans = new ArrayList<EmergencyToolBean>();

	@OneToMany(mappedBy = "productBean")
	List<HelmetRecorderBean> helmetRecorderBeans = new ArrayList<HelmetRecorderBean>();

	@OneToMany(mappedBy = "productBean")
	List<MotorcycleRecorderBean> motorcycleRecorderBeans = new ArrayList<MotorcycleRecorderBean>();

	@OneToMany(mappedBy = "productBean")
	List<MotorcycleGpsBean> motorcycleGpsBeans = new ArrayList<MotorcycleGpsBean>();

	@OneToMany(mappedBy = "productBean")
	List<TireGaugeBean> tireGaugeBeans = new ArrayList<TireGaugeBean>();

}
