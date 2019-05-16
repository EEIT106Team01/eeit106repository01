package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 冒竣瑋 - Entity for Review.
 */
@Entity
@Table(name = "SHOP_Review")
public class ReviewBean implements Serializable {

	private static final long serialVersionUID = 2337078640768450244L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private java.util.Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private java.util.Date updatedTime;

	@Column(nullable = false)
	private Double rating;

	@Column(nullable = false, columnDefinition = "nvarchar(max)")
	private String comment;

	@Column(nullable = true)
	private Blob image;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Member_Id")
	private MemberBeanTest memberBeanTest;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Product_Id")
	private ProductBean productBean;

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

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public MemberBeanTest getMemberBeanTest() {
		return memberBeanTest;
	}

	public void setMemberBeanTest(MemberBeanTest memberBeanTest) {
		this.memberBeanTest = memberBeanTest;
	}

	public ProductBean getProductBean() {
		return productBean;
	}

	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

}
