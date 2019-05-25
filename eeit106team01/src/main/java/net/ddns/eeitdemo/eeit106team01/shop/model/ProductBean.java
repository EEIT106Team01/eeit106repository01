package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(schema = "Shop", name = "Product")
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 8349717092729742091L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProductID", columnDefinition = "bigint")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateTime", nullable = false, updatable = false)
	private java.util.Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdatedTime", nullable = false)
	private java.util.Date updatedTime;

	@Column(name = "Name", columnDefinition = "nvarchar(max)", nullable = false, unique = true, updatable = false)
	private String name;

	@Column(name = "Type", nullable = false, updatable = false)
	private String type;

	@Column(name = "Brand", nullable = false, updatable = false)
	private String brand;

	@Column(name = "Price", nullable = false)
	private Integer price;

	@Column(name = "Stock", nullable = false)
	private Integer stock;

	@Column(name = "TotalSold", nullable = false)
	private Integer totalSold;

	@Column(name = "Information", columnDefinition = "varbinary(max)", nullable = false)
	private HashMap<String, String> information;

	@Column(name = "InformationImageLink", columnDefinition = "varbinary(max)", nullable = false)
	private HashMap<Integer, String> informationImageLink;

	@Column(name = "ImageLink", columnDefinition = "varbinary(max)", nullable = false)
	private HashMap<Integer, String> imageLink;

	@OneToMany(mappedBy = "productId")
	private List<SerialNumberBean> serialNeumberId;

	@OneToMany(mappedBy = "productId")
	private List<PurchaseListBean> purchaseListId;

	@OneToMany(mappedBy = "productId")
	private List<ReviewBean> reviewId;

	public ProductBean() {
		super();
	}

	/**
	 * @param createTime
	 * @param updatedTime
	 * @param name
	 * @param type
	 * @param brand
	 * @param price
	 * @param stock
	 * @param totalSold
	 * @param information
	 * @param informationImageLink
	 * @param imageLink
	 */
	public ProductBean(Date createTime, Date updatedTime, String name, String type, String brand, Integer price,
			Integer stock, Integer totalSold, HashMap<String, String> information,
			HashMap<Integer, String> informationImageLink, HashMap<Integer, String> imageLink) {
		super();
		this.createTime = createTime;
		this.updatedTime = updatedTime;
		this.name = name;
		this.type = type;
		this.brand = brand;
		this.price = price;
		this.stock = stock;
		this.totalSold = totalSold;
		this.information = information;
		this.informationImageLink = informationImageLink;
		this.imageLink = imageLink;
	}

	@Override
	public String toString() {
		return "ProductBean [id=" + id + ", createTime=" + createTime + ", updatedTime=" + updatedTime + ", name="
				+ name + ", type=" + type + ", brand=" + brand + ", price=" + price + ", stock=" + stock
				+ ", totalSold=" + totalSold + ", information=" + information + ", informationImageLink="
				+ informationImageLink + ", imageLink=" + imageLink + "]";
	}

	/**
	 * Check createTime, updatedTime, name, type ,brand, price, stock, totalSold,
	 * information, informationImageLink, imageLink null or not.
	 * 
	 * @return true if not null, or NullPointerException if it is.
	 */
	public Boolean isNotNull() {
		this.createTime = Objects.requireNonNull(createTime, "createTime must not be null");
		this.updatedTime = Objects.requireNonNull(updatedTime, "updatedTime must not be null");
		this.name = Objects.requireNonNull(name, "name must not be null");
		this.type = Objects.requireNonNull(type, "type must not be null");
		this.brand = Objects.requireNonNull(brand, "brand must not be null");
		this.price = Objects.requireNonNull(price, "price must not be null");
		this.stock = Objects.requireNonNull(stock, "stock must not be null");
		this.totalSold = Objects.requireNonNull(totalSold, "brand must not be null");
		this.information = Objects.requireNonNull(information, "information must not be null");
		this.informationImageLink = Objects.requireNonNull(informationImageLink, "brand must not be null");
		this.imageLink = Objects.requireNonNull(imageLink, "imageLink must not be null");
		return true;
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

	/**
	 * @param createTime required new Date(System.currentTimeMillis())
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * @param updatedTime required new Date(System.currentTimeMillis())
	 */
	public void setUpdatedTime(java.util.Date updatedTime) {
		this.updatedTime = updatedTime;
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
