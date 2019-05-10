package net.ddns.eeitdemo.eeit106team01.controller;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.model.ShopBean;

@RestController
public class ShopRestController {
	@Autowired
	private SessionFactory sessionFactory;

	@GetMapping(value = { "/products" })
	@SuppressWarnings("unchecked")
	public List<ProductBean> findAll() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ProductBean", ProductBean.class);
		List<ProductBean> result = query.getResultList();
		return result;
	}

	@GetMapping(value = { "/items" })
	@SuppressWarnings("unchecked")
	public List<ShopBean> findAllItem() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ShopBean", ShopBean.class);
		List<ShopBean> result = query.getResultList();
		return result;
	}

	@PostMapping(path = { "/items" }, consumes = { "application/x-www-form-urlencoded", "application/json" })
	public List<ShopBean> createItem(@RequestBody ShopBean bean) {
		Session session = sessionFactory.openSession();
		ShopBean newBean = new ShopBean();
		newBean.setId(bean.getId());
		newBean.setName(bean.getName());
		newBean.setPrice(String.valueOf(bean.getPrice()));
		newBean.setImgLink(bean.getImgLink());
		newBean.setImgSetLink(bean.getImgSetLink());
		newBean.setPlatformName(bean.getPlatformName());
		newBean.setPlatformLink(bean.getPlatformLink());
		newBean.setTotalCount(String.valueOf(bean.getTotalCount()));
		newBean.setTotalPage(String.valueOf(bean.getTotalPage()));
		session.save(newBean);
		session.beginTransaction().commit();
		return findAllItem();
	}

}
