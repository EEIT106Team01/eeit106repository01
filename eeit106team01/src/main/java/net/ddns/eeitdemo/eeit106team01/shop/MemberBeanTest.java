package net.ddns.eeitdemo.eeit106team01.shop;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author 冒竣瑋 - Entity for Member [Test].
 */
@Entity
public class MemberBeanTest implements Serializable {

	private static final long serialVersionUID = 6440126604397615997L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String memberShip;
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "memberBeanTest")
//	List<ReviewBean> reviewBeans = new ArrayList<ReviewBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "memberBeanTest")
//	List<OrderBean> orderBeans = new ArrayList<OrderBean>();
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "memberBeanTest")
//	List<RefundBean> refundBeans = new ArrayList<RefundBean>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getMemberShip() {
		return memberShip;
	}

	public void setMemberShip(String memberShip) {
		this.memberShip = memberShip;
	}
}
