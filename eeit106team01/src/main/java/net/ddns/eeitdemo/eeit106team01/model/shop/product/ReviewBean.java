package net.ddns.eeitdemo.eeit106team01.model.shop.product;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.ddns.eeitdemo.eeit106team01.model.MemberBeanTest;

/**
 * @author 冒竣瑋 - This is an Entity for Review.
 */
@Entity
public class ReviewBean implements Serializable {

	private static final long serialVersionUID = 2337078640768450244L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "MemberBeanTest_Id_FK")
	private MemberBeanTest memberbeantest;
	
	@Temporal(TemporalType.DATE)
	private java.util.Date date;
	private Integer rating;
	private String comment;
	private Blob image;
	
	@ManyToOne
	@JoinColumn(name = "ProductBean_Id_FK")
	private ProductBean productbean;

	@Override
	public String toString() {
		return "ReviewBean [memberbeantest=" + memberbeantest + ", date=" + date + ", rating=" + rating + ", comment=" + comment
				+ ", image=" + image + ", product_id=" + productbean + "]";
	}

	public MemberBeanTest getMember_id() {
		return memberbeantest;
	}

	public void setMember_id(MemberBeanTest memberbeantest) {
		this.memberbeantest = memberbeantest;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
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

	public ProductBean getProduct_id() {
		return productbean;
	}

	public void setProduct_id(ProductBean productbean) {
		this.productbean = productbean;
	}

}
