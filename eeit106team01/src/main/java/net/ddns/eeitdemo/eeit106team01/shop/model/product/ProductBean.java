package net.ddns.eeitdemo.eeit106team01.shop.model.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate() {
		this.date = new Date(System.currentTimeMillis());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<ReviewBean> reviewBeans = new ArrayList<ReviewBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<OrderDetailBean> orderDetailBeans = new ArrayList<OrderDetailBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<RefundDetailBean> refundDetailBeans = new ArrayList<RefundDetailBean>();

	// Product - Automotive
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<AntiTheftBean> antiTheftBeans = new ArrayList<AntiTheftBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<CarCareBean> carCareBeans = new ArrayList<CarCareBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<CarGpsBean> carGpsBeans = new ArrayList<CarGpsBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<CarRecorderBean> carRecorderBeans = new ArrayList<CarRecorderBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<CarSeatBean> carSeatBeans = new ArrayList<CarSeatBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<EmergencyToolBean> emergencyToolBeans = new ArrayList<EmergencyToolBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<HelmetRecorderBean> helmetRecorderBeans = new ArrayList<HelmetRecorderBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<MotorcycleRecorderBean> motorcycleRecorderBeans = new ArrayList<MotorcycleRecorderBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<MotorcycleGpsBean> motorcycleGpsBeans = new ArrayList<MotorcycleGpsBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productBean")
//	private List<TireGaugeBean> tireGaugeBeans = new ArrayList<TireGaugeBean>();

}
