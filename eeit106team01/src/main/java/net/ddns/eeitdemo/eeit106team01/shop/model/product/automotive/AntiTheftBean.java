package net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.google.gson.JsonObject;

import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.ProductBean;

/**
 * @author 冒竣瑋 - This is an Entity for AntiTheft.
 */
@Entity
public class AntiTheftBean implements Serializable {

	private static final long serialVersionUID = 5338967047050522712L;

	@ManyToOne
	@JoinColumn(name = "ProductBean_Id_FK")
	private ProductBean productbean;

	@OneToOne(mappedBy = "antiTheftBean", cascade = CascadeType.ALL)
	private List<RefundDetailBean> refundDetailBeanList = new ArrayList<RefundDetailBean>();
	
	@OneToOne(mappedBy = "antiTheftBean", cascade = CascadeType.ALL)
	private List<OrderDetailBean> orderDetailBeanList = new ArrayList<OrderDetailBean>();
	
	@Id
	@Column(unique = true, nullable = false, updatable = false)
	private Long serialnumber;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Long price;

	@Column(nullable = false)
	private Integer stock;

	@Column(nullable = false)
	private JsonObject description;

	@Column(nullable = false)
	private JsonObject information;

	@Column(nullable = false)
	private String imagelink;

	@Override
	public String toString() {
		return "CarSeatBean [productbean=" + productbean + ", serialnumber=" + serialnumber + ", brand=" + brand
				+ ", name=" + name + ", price=" + price + ", stock=" + stock + ", description=" + description
				+ ", information=" + information + ", imagelink=" + imagelink + "]";
	}

	public ProductBean getProduct_id() {
		return productbean;
	}

	public void setProduct_id(ProductBean productbean) {
		this.productbean = productbean;
	}

	public Long getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(Long serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public JsonObject getDescription() {
		return description;
	}

	public void setDescription(JsonObject description) {
		this.description = description;
	}

	public JsonObject getInformation() {
		return information;
	}

	public void setInformation(JsonObject information) {
		this.information = information;
	}

	public String getImage() {
		return imagelink;
	}

	public void setImage(String imagelink) {
		this.imagelink = imagelink;
	}

}
