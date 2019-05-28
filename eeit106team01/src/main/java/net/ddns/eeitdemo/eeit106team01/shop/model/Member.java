package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "Shop", name = "Member")
public class Member implements Serializable {

	private static final long serialVersionUID = 6440126604397615997L;

	@Id
	@GeneratedValue
	@Column(name = "MemberID", columnDefinition = "bigint")
	private Long id;

	@OneToMany(mappedBy = "memberId")
	private List<PurchaseBean> purchaseId;

	@OneToMany(mappedBy = "memberId")
	private List<RefundBean> refundId;

	@OneToMany(mappedBy = "memberId")
	private List<ReviewBean> reviewId;

	@Override
	public String toString() {
		return "Member [id=" + id + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
