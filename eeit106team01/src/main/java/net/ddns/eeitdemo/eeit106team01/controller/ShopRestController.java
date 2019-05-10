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
	public List<ShopBean> createItem(@RequestBody MultiValueMap<String, String> body) {
		Session session = sessionFactory.openSession();
		ShopBean bean = new ShopBean();
		bean.setId(Integer.parseInt(body.get("id").get(0)));
		bean.setName(body.get("name").get(0));
		bean.setPrice(body.get("price").get(0));
		bean.setImgLink(body.get("imgLink").get(0));
		bean.setImgSetLink(body.get("imgSetLink").get(0));
		bean.setPlatformName(body.get("platformName").get(0));
		bean.setPlatformLink(body.get("platformLink").get(0));
		bean.setTotalCount(body.get("totalCount").get(0));
		bean.setTotalPage(body.get("totalPage").get(0));
		session.save(bean);
		session.beginTransaction().commit();
		return findAllItem();
	}

}
