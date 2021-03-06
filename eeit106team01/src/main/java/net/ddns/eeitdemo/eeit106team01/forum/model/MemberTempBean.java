package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MemberTempVer2")
public class MemberTempBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String password;
	@Column(columnDefinition = "varchar(max)")
	private String image;
	private String email;
	private java.util.Date birth;
	private String level;
	private java.util.Date levelTime;
	private java.util.Date memberCreateTime;
	private String phone;
	private String address;
	
//	@OneToMany(mappedBy = "memberId")
//	private List<PurchaseBean> purchaseId;
//
//	@OneToMany(mappedBy = "memberId")
//	private List<RefundBean> refundId;
//
//	@OneToMany(mappedBy = "memberId")
//	private List<ReviewBean> reviewId;
	
	@Override
	public String toString() {
		return "MemberTempBean [id=" + id + ", name=" + name + ", password=" + password + ", image=" + image
				+ ", email=" + email + ", birth=" + birth + ", level=" + level + ", levelTime=" + levelTime
				+ ", memberCreateTime=" + memberCreateTime + ", phone=" + phone + ", address=" + address + "]";
	}

	public MemberTempBean() {
		super();
	}

	public MemberTempBean(String name, String password, String image, String email, Date birth, String level, Date levelTime,
			Date memberCreateTime) {
		super();
		this.name = name;
		this.password = password;
		this.image = image;
		this.email = email;
		this.birth = birth;
		this.level = level;
		this.levelTime = levelTime;
		this.memberCreateTime = memberCreateTime;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public java.util.Date getBirth() {
		return birth;
	}
	public void setBirth(java.util.Date birth) {
		this.birth = birth;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public java.util.Date getLevelTime() {
		return levelTime;
	}
	public void setLevelTime(java.util.Date levelTime) {
		this.levelTime = levelTime;
	}
	public java.util.Date getMemberCreateTime() {
		return memberCreateTime;
	}
	public void setMemberCreateTime(java.util.Date memberCreateTime) {
		this.memberCreateTime = memberCreateTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
//	public List<PurchaseBean> getPurchaseId() {
//		return purchaseId;
//	}
//
//	public void setPurchaseId(List<PurchaseBean> purchaseId) {
//		this.purchaseId = purchaseId;
//	}
//
//	public List<RefundBean> getRefundId() {
//		return refundId;
//	}
//
//	public void setRefundId(List<RefundBean> refundId) {
//		this.refundId = refundId;
//	}
//
//	public List<ReviewBean> getReviewId() {
//		return reviewId;
//	}
//
//	public void setReviewId(List<ReviewBean> reviewId) {
//		this.reviewId = reviewId;
//	}

}
