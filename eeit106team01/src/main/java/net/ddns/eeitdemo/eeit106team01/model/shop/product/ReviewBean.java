package net.ddns.eeitdemo.eeit106team01.model.shop.product;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.ddns.eeitdemo.eeit106team01.model.ProductBean;

/**
 * @author 冒竣瑋 - This is an Entity for Review.
 */
@Entity
public class ReviewBean implements Serializable {

	private static final long serialVersionUID = 2337078640768450244L;

	@ManyToOne
	@JoinColumn(name = "MemberBean_Id_FK")
	private Long memberbean;
	
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
		return "ReviewBean [memberbean=" + memberbean + ", date=" + date + ", rating=" + rating + ", comment=" + comment
				+ ", image=" + image + ", product_id=" + productbean + "]";
	}

	public Long getMember_id() {
		return memberbean;
	}

	public void setMember_id(Long memberbean) {
		this.memberbean = memberbean;
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
