package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ProductBean insertProduct(ProductBean productBean) {
		if (productBean != null) {
			Query query = this.getSession().createQuery("from ProductBean where name = :name", ProductBean.class);
			query.setParameter("name", productBean.getName());
			ProductBean result=(ProductBean) query.getSingleResult();
			if (result == null) { //name不能重複
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
		return this.getSession().createQuery("from ProductBean", ProductBean.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> findProductsByName(String name) {
		Query query = this.getSession().createQuery("from ProductBean where name like :name", ProductBean.class);
		query.setParameter("name", "%"+name+"%");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> findProductsByBrand(String brand) {
		Query query = this.getSession().createQuery("from ProductBean where brand =:brand", ProductBean.class);
		query.setParameter("brand", brand);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> findProductsByStock(String stockType) {
		if(stockType.equals("notEmpty")) {
			Query query = this.getSession().createQuery("from ProductBean where stock > 0", ProductBean.class);
			return query.getResultList();
		}else {  //stockType.equals("Empty")
			Query query = this.getSession().createQuery("from ProductBean where stock = 0", ProductBean.class);
			return query.getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> findProductsByType(String type) {
		Query query = this.getSession().createQuery("from ProductBean where type =:type", ProductBean.class);
		query.setParameter("type", type);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> findProductsByPrice(Integer minPrice, Integer maxPrice) {
		Query query = this.getSession().createQuery("from ProductBean where price between :minPrice and :maxPrice",
				ProductBean.class);
		query.setParameter("minPrice", minPrice);
		query.setParameter("maxPrice", maxPrice);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> findProductsByTime(Integer day) {
		Query query = this.getSession().createQuery(
				"from ProductBean where updatedTime > DATEADD(day,:day ,GETDATE()) AND updatedTime <=  DATEADD(day,0,GETDATE())",
				ProductBean.class);
		query.setParameter("day", day);
		return query.getResultList();
	}
}
