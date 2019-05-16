package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory = null;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	} 
	
	@Override
	public ProductBean insertProduct(ProductBean productBean) {
		if(productBean!=null) {
			ProductBean result = this.getSession().get(ProductBean.class, productBean.getId());
			if(result==null) {
				this.getSession().save(productBean);
				return productBean;
			}
		}
		return null;
	}

	@Override
	public ProductBean updateProduct(ProductBean productBean) {
		if (productBean != null) {
			getSession().update(productBean);
			return findProductByPrimaryKey(productBean.getId());
		}
		return null;
	}

	@Override
	public ProductBean findProductByPrimaryKey(Long id) {
		return getSession().get(ProductBean.class, id);
	}

	@Override
	public ProductBean findProductBySerialNumber(String serialNumber) {
		return getSession().get(ProductBean.class, serialNumber);
	}

	@Override
	public List<ProductBean> findProducts() {
		return this.getSession().createQuery(
				"from ProductBean", ProductBean.class).getResultList();
	}

	@Override
	public List<ProductBean> findProductsByName(String name) {
		Query query = this.getSession().createQuery(
				"from ProductBean where name :name", ProductBean.class);
		query.setParameter("brand", name);
		return query.getResultList();
	}

	@Override
	public List<ProductBean> findProductsByBrand(String brand) {
		Query query = this.getSession().createQuery(
				"from ProductBean where brand :brand", ProductBean.class);
		query.setParameter("brand", brand);
		return query.getResultList();
	}

	@Override
	public List<ProductBean> findProductsByStock(Integer stock) {
		Query query = this.getSession().createQuery(
				"from ProductBean where stock :stock", ProductBean.class);
		query.setParameter("brand", stock);
		return query.getResultList();
	}

	@Override
	public List<ProductBean> findProductsByType(String type) {
		Query query = this.getSession().createQuery(
				"from ProductBean where type :type", ProductBean.class);
		query.setParameter("brand", type);
		return query.getResultList();
	}

	@Override
	public List<ProductBean> findProductsByPrice(Integer minPrice, Integer maxPrice) {
		Query query = this.getSession().createQuery(
				"from ProductBean where price between :minPrice and :maxPrice", ProductBean.class);
		query.setParameter("minPrice", minPrice);
		query.setParameter("maxPrice", maxPrice);
		return query.getResultList();
	}

	@Override
	public List<ProductBean> findProductsByTime(Integer day) {
		Query query = this.getSession().createQuery(
				"from SHOP_Order where time > DATEADD(day,:day ,GETDATE()) AND time <=  DATEADD(day,0,GETDATE())", ProductBean.class);
		query.setParameter("day", day);
		return query.getResultList();
	}

}
