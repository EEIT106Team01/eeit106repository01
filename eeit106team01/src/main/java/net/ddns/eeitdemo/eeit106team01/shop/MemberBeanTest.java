package net.ddns.eeitdemo.eeit106team01.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.ReviewBean;

@Entity
public class MemberBeanTest implements Serializable {

	private static final long serialVersionUID = 6440126604397615997L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany(mappedBy = "memberbeantest")
	List<ReviewBean> reviews = new ArrayList<ReviewBean>();
	@OneToMany(mappedBy = "memberBeanTest")
	List<OrderBean> OrderBean = new ArrayList<OrderBean>();
	@OneToMany(mappedBy = "memberBeanTest")
	List<RefundBean> RefundBean = new ArrayList<RefundBean>();

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

}
