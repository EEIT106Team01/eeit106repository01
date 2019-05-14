package net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.ddns.eeitdemo.eeit106team01.shop.model.product.ProductBean;

/**
 * @author 冒竣瑋 - This is an Entity for AntiTheft.
 */
@Entity
public class AntiTheftBean implements Serializable {

	private static final long serialVersionUID = 5338967047050522712L;

	@ManyToOne
	@JoinColumn(name = "FK_ProductBean_Id")
	private ProductBean productbean;

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
	private String description;

	@Column(nullable = false)
	private String information;

	@Column(nullable = false)
	private String imagelink;

	public ProductBean getProductbean() {
		return productbean;
	}

	public void setProductbean(ProductBean productbean) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getImagelink() {
		return imagelink;
	}

	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

}
