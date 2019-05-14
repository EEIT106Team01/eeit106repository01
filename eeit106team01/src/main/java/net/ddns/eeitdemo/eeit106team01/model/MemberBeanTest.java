package net.ddns.eeitdemo.eeit106team01.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import net.ddns.eeitdemo.eeit106team01.model.shop.OrderBean;
import net.ddns.eeitdemo.eeit106team01.model.shop.RefundBean;
import net.ddns.eeitdemo.eeit106team01.model.shop.product.ReviewBean;

@Entity
public class MemberBeanTest implements Serializable {

	private static final long serialVersionUID = 6440126604397615997L;

	@Id
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
