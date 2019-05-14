package net.ddns.eeitdemo.eeit106team01.shop.model.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.AntiTheftBean;

/**
 * @author 冒竣瑋 - This is an Entity for Product.
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
	private String Name;

	// Reviews
	@OneToMany(mappedBy = "productbean")
	List<ReviewBean> reviews = new ArrayList<ReviewBean>();

	// Automotive
	@OneToMany(mappedBy = "productbean")
	List<AntiTheftBean> antiTheftBeans = new ArrayList<AntiTheftBean>();

	@OneToMany(mappedBy = "product")
	List<OrderDetailBean> orderDetailBean = new ArrayList<OrderDetailBean>();
	
//	 Automotive - Car
//	@OneToMany(mappedBy = "productbean")
//	List<CarCareBean> carcares = new ArrayList<CarCareBean>();
//	@OneToMany(mappedBy = "productBean")
//	List<CarGpsBean> cargpss = new ArrayList<CarGpsBean>();
//	@OneToMany(mappedBy = "productBean")
//	List<CarRecorderBean> carrecorders = new ArrayList<CarRecorderBean>();
//	@OneToMany(mappedBy = "productBean")
//	List<CarSeatBean> carseats = new ArrayList<CarSeatBean>();
//
//	// Automotive - Motorcycle
//	@OneToMany(mappedBy = "productBean")
//	List<HelmetRecorderBean> helmetrecorders = new ArrayList<HelmetRecorderBean>();
//	@OneToMany(mappedBy = "productBean")
//	List<MotorcycleRecorderBean> motorcyclerecorders = new ArrayList<MotorcycleRecorderBean>();
//	@OneToMany(mappedBy = "productBean")
//	List<MotorcycleGpsBean> motorcyclegpss = new ArrayList<MotorcycleGpsBean>();
//
//	@OneToMany(mappedBy = "productbean")
//	List<EmergencyToolBean> emergencytools = new ArrayList<EmergencyToolBean>();
//	@OneToMany(mappedBy = "productbean")
//	List<TireGaugeBean> tiregauges = new ArrayList<TireGaugeBean>();

	public ProductBean() {
		super();
	}

	public ProductBean(Long id) {
		super();
		this.id = id;
	}

	public ProductBean(Long id, String name) {
		super();
		this.id = id;
		Name = name;
	}

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
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
