package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SHOP_Order_Detail")
public class OrderDetailBean implements Serializable {

	private static final long serialVersionUID = 7412479144382182019L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer price;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Product_Id")
	private ProductBean productBean;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Order_Id")
	private OrderBean orderBean;

	@Column(nullable = false)
	private String serialNumber;

	@Override
	public String toString() {
		return "OrderDetailBean [id=" + id + ", price=" + price + ", productBean=" + productBean + ", orderBean="
				+ orderBean + ", serialNumber=" + serialNumber + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public ProductBean getProductBean() {
		return productBean;
	}

	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	public String getSerialnumber() {
		return serialNumber;
	}

	public void setSerialnumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
