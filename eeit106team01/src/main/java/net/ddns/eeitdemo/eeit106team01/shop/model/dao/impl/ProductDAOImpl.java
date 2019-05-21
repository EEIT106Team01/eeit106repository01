package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.SerialNumberGenerator;

@Repository
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductBean insertProduct(ProductBean productBean) {
		if (productBean != null) {
			Query query = this.getSession().createQuery("from ProductBean where name = :name", ProductBean.class);
			query.setParameter("name", productBean.getName());
			List<ProductBean> result = query.getResultList();
			if (result.size() == 0) { // name不能重複
				getSession().save(productBean);
				return productBean;
			} else {
				return null;
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
		query.setParameter("name", "%" + name + "%");
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
		if (stockType.equals("notEmpty")) {
			Query query = this.getSession().createQuery("from ProductBean where stock > 0", ProductBean.class);
			return query.getResultList();
		} else {
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
	public List<ProductBean> findProductsByUpdatedTime(Integer day) {
		Query query = this.getSession().createQuery(
				"from ProductBean where updatedTime >= DATEADD(day,:day ,GETDATE()) AND updatedTime <= DATEADD(day,:day1,GETDATE())",
				ProductBean.class);
		query.setParameter("day", day);
		query.setParameter("day1", 0);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumberBean> insertProductsSN(Long id, Integer stock) {
		if (stock != null && id != null) {
			List<SerialNumberBean> serialNumberBeans = new ArrayList<SerialNumberBean>();
			loop: for (int i = 1; i <= stock; i++) {
				SerialNumberGenerator serialNumberGenerator = new SerialNumberGenerator(20);
				String sng = serialNumberGenerator.nextString();
				Query query = this.getSession().createQuery("from SerialNumberBean where serialNumber = :serialNumber",
						SerialNumberBean.class);
				query.setParameter("serialNumber", sng);
				List<SerialNumberBean> result = query.getResultList();

				if (result.size() == 0) {
					SerialNumberBean serialNumberBean = new SerialNumberBean();
					serialNumberBean.setProductBean(this.findProductByPrimaryKey(id));
					serialNumberBean.setAvailabilityStatus("available");
					serialNumberBean.setSerialNumber(sng);
					getSession().save(serialNumberBean);
					serialNumberBeans.add(serialNumberBean);
				} else {
					break loop;
				}
			}
			return serialNumberBeans;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumberBean> findsoldProducts() {
		Query query = this.getSession().createQuery(
				"from SerialNumberBean where availabilityStatus = :availabilityStatus", SerialNumberBean.class);
		query.setParameter("availabilityStatus", "sold");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumberBean> findsoldProduct(Long id) {
		Query query = this.getSession().createQuery(
				"from SerialNumberBean where availabilityStatus = :availabilityStatus and ProductBean_Id = :id",
				SerialNumberBean.class);
		query.setParameter("availabilityStatus", "sold");
		query.setParameter("id", id);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumberBean> findavailableProducts() {
		Query query = this.getSession().createQuery(
				"from SerialNumberBean where availabilityStatus = :availabilityStatus", SerialNumberBean.class);
		query.setParameter("availabilityStatus", "available");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumberBean> findavailableProduct(Long id) {
		Query query = this.getSession().createQuery(
				"from SerialNumberBean where availabilityStatus = :availabilityStatus and ProductBean_Id = :id",
				SerialNumberBean.class);
		query.setParameter("availabilityStatus", "available");
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public SerialNumberBean findSNBeanBySerialNumber(String serialNumber) {
		if (serialNumber != null) {
			Query query = getSession().createQuery("from SerialNumberBean where serialNumber = :serialNumber",
					SerialNumberBean.class);
			query.setParameter("serialNumber", serialNumber);
			SerialNumberBean result = (SerialNumberBean) query.getSingleResult();
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public SerialNumberBean updateSNStatus(SerialNumberBean serialNumberBean) {
		if (serialNumberBean != null) {
			getSession().update(serialNumberBean);
			return findSNBeanBySerialNumber(serialNumberBean.getSerialNumber());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumberBean> findProductsByStatus(String status) {
		Query query = this.getSession().createQuery(
				"from SerialNumberBean where availabilityStatus = :availabilityStatus", SerialNumberBean.class);
		query.setParameter("availabilityStatus", status);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumberBean> findProductByStatus(Long id, String status) {
		Query query = this.getSession().createQuery(
				"from SerialNumberBean where availabilityStatus = :availabilityStatus and ProductBean_Id = :ProductBean_Id", SerialNumberBean.class);
		query.setParameter("availabilityStatus", status);
		query.setParameter("ProductBean_Id", id);
		return query.getResultList();
	}

}
