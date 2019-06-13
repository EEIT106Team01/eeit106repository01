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
import javax.sql.rowset.serial.SerialException;

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberTempBean;

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
	private MemberTempBean memberId;

	@OneToOne
	@JoinColumn(name = "PurchaseListId", columnDefinition = "bigint", nullable = false, updatable = false, unique = true)
	private PurchaseListBean purchaseListId;

	@ManyToOne
	@JoinColumn(name = "ProductID", columnDefinition = "bigint", nullable = false, updatable = false)
	private ProductBean productId;

	private Byte[] imageBase64;

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
	public ReviewBean(Date createTime, Date updatedTime, Double rating, String comment, MemberTempBean memberId,
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
				+ purchaseListId + ", productId=" + productId + ", imageBase64=" + imageBase64 + "]";
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

	public byte[] getImage() {
		SerialBlob serialBlob = image;
		byte[] byteArray = null;
		try {
			if (serialBlob != null) {
				byteArray = serialBlob.getBytes(1, (int) serialBlob.length());
			} else {
				return null;
			}
		} catch (SerialException e) {
			e.printStackTrace();
		}
		return byteArray;
	}

	public void setImage(SerialBlob image) {
		this.image = image;
	}

	public MemberTempBean getMemberId() {
		return memberId;
	}

	public void setMemberId(MemberTempBean memberId) {
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

	public Byte[] getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(Byte[] imageBase64) {
		this.imageBase64 = imageBase64;
	}

}
