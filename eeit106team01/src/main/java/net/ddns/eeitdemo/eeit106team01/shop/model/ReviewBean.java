package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.sql.rowset.serial.SerialBlob;

@Entity
@Table(schema = "Shop", name = "Review")
public class ReviewBean implements Serializable {

	private static final long serialVersionUID = 2337078640768450244L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewID", columnDefinition = "bigint")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateTime", nullable = false, updatable = false)
	private java.util.Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdatedTime", nullable = false)
	private java.util.Date updatedTime;

	@Column(name = "Rating", nullable = false)
	private Double rating;

	@Column(name = "Comment", columnDefinition = "nvarchar(max)", nullable = false)
	private String comment;

	@Column(name = "Image", columnDefinition = "image")
	private SerialBlob image;

	@ManyToOne
	@JoinColumn(name = "MemberID", columnDefinition = "bigint", nullable = false, updatable = false)
	private Member memberId;

	@OneToOne
	@JoinColumn(name = "PurchaseListId", columnDefinition = "bigint", nullable = false, updatable = false, unique = true)
	private PurchaseListBean purchaseListId;

	@ManyToOne
	@JoinColumn(name = "ProductID", columnDefinition = "bigint", nullable = false, updatable = false)
	private ProductBean productId;

	private String imageString;

	public ReviewBean() {
		super();
	}

	/**
	 * @param createTime
	 * @param updatedTime
	 * @param rating
	 * @param comment
	 * @param memberId
	 * @param purchaseListId
	 * @param productId
	 */
	public ReviewBean(Date createTime, Date updatedTime, Double rating, String comment, Member memberId,
			PurchaseListBean purchaseListId, ProductBean productId) {
		super();
		this.createTime = createTime;
		this.updatedTime = updatedTime;
		this.rating = rating;
		this.comment = comment;
		this.memberId = memberId;
		this.purchaseListId = purchaseListId;
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "ReviewBean [id=" + id + ", createTime=" + createTime + ", updatedTime=" + updatedTime + ", rating="
				+ rating + ", comment=" + comment + ", image=" + image + ", memberId=" + memberId + ", purchaseListId="
				+ purchaseListId + ", productId=" + productId + "]";
	}

	/**
	 * Check createTime, updatedTime, comment, rating ,memberId, purchaseListId,
	 * productId null or not.
	 * 
	 * @return true if not null, or NullPointerException if it is.
	 */
	public Boolean isNotNull() {
		this.createTime = Objects.requireNonNull(createTime, "createTime must not be null");
		this.updatedTime = Objects.requireNonNull(updatedTime, "updatedTime must not be null");
		this.comment = Objects.requireNonNull(comment, "comment must not be null");
		this.rating = Objects.requireNonNull(rating, "rating must not be null");
		this.memberId = Objects.requireNonNull(memberId, "memberId must not be null");
		this.purchaseListId = Objects.requireNonNull(purchaseListId, "purchaseListId must not be null");
		this.productId = Objects.requireNonNull(productId, "productId must not be null");
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

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(java.util.Date updatedTime) {
		this.updatedTime = updatedTime;
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

	public SerialBlob getImage() {
		return image;
	}

	public void setImage(SerialBlob image) {
		this.image = image;
	}

	public Member getMemberId() {
		return memberId;
	}

	public void setMemberId(Member memberId) {
		this.memberId = memberId;
	}

	public PurchaseListBean getPurchaseListId() {
		return purchaseListId;
	}

	public void setPurchaseListId(PurchaseListBean purchaseListId) {
		this.purchaseListId = purchaseListId;
	}

	public ProductBean getProductId() {
		return productId;
	}

	public void setProductId(ProductBean productId) {
		this.productId = productId;
	}

	public String getImageString() {
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

}
