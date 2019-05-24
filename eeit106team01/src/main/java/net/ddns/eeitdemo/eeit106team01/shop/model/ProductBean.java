package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 冒竣瑋 - Entity for Product.
 */
@Entity
@Table(name = "SHOP_Product")
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 8349717092729742091L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private java.util.Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private java.util.Date updatedTime;

	@Column(nullable = false, unique = true, columnDefinition = "nvarchar(max)")
	private String name;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private Integer stock;

	@Column(nullable = false)
	private Integer totalSold;

	@Column(nullable = false, columnDefinition = "varbinary(max)")
	private HashMap<String, String> information;

	@Column(nullable = false, columnDefinition = "varbinary(max)")
	private HashMap<Integer, String> informationImageLink;

	@Column(nullable = false, columnDefinition = "varbinary(max)")
	private HashMap<Integer, String> imageLink;

	@Override
	public String toString() {
		return "ProductBean [id=" + id + ", createTime=" + createTime + ", updatedTime=" + updatedTime + ", name="
				+ name + ", type=" + type + ", brand=" + brand + ", price=" + price + ", stock=" + stock
				+ ", totalSold=" + totalSold + ", informationImageLink=" + informationImageLink + ", information="
				+ information + ", imageLink=" + imageLink + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime() {
		this.createTime = new Date(System.currentTimeMillis());
	}

	public java.util.Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime() {
		this.updatedTime = new Date(System.currentTimeMillis());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getTotalSold() {
		return totalSold;
	}

	public void setTotalSold(Integer totalSold) {
		this.totalSold = totalSold;
	}

	public HashMap<Integer, String> getInformationImageLink() {
		return informationImageLink;
	}

	public void setInformationImageLink(HashMap<Integer, String> informationImageLink) {
		this.informationImageLink = informationImageLink;
	}

	public HashMap<String, String> getInformation() {
		return information;
	}

	public void setInformation(HashMap<String, String> information) {
		this.information = information;
	}

	public HashMap<Integer, String> getImageLink() {
		return imageLink;
	}

	public void setImageLink(HashMap<Integer, String> imageLink) {
		this.imageLink = imageLink;
	}

}
