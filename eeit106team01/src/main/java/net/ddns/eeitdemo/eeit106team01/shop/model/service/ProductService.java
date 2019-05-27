package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

@Service
@Transactional
public class ProductService {
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public ProductBean insertProduct(ProductBean productBean) {
		if (productBean != null) {
			Date date = new Date(System.currentTimeMillis());
			productBean.setUpdatedTime(date);
			productBean.setCreateTime(date);
			productBean.setTotalSold(0);
			Query<ProductBean> query = this.getSession().createQuery("from ProductBean where name = :name",
					ProductBean.class);
			query.setParameter("name", productBean.getName());
			List<ProductBean> products = query.getResultList();
			if (products.size() == 0) { // name不能重複
				ProductBean result = productDAO.insertProduct(productBean);
				if (result != null) {
					productDAO.insertProductSerialNumberByProductId(result.getId(), result.getStock());
					return productDAO.findProductByProductId(result.getId());
				}
			}
		}
		return null;
	}

	public ProductBean updateProduct(ProductBean productBean) {
		ProductBean findOne = findProductByPrimaryKey(productBean.getId());
		if (findOne != null) {
			findOne.setBrand(productBean.getBrand());
			Date date = new Date(System.currentTimeMillis());
			findOne.setUpdatedTime(date);
			findOne.setStock(productBean.getStock());
			findOne.setImageLink(productBean.getImageLink());
			findOne.setName(productBean.getName());
			findOne.setPrice(productBean.getPrice());
			findOne.setType(productBean.getType());
			findOne.setTotalSold(productBean.getTotalSold());
			findOne.setImageLink(productBean.getImageLink());
			findOne.setInformationImageLink(productBean.getInformationImageLink());
			return productDAO.updateProduct(findOne);
		}
		return null;
	}

	public ProductBean findProductByPrimaryKey(Long id) {
		return productDAO.findProductByProductId(id);
	}

	public List<ProductBean> findProducts() {
		return productDAO.findProducts();
	}

	public List<ProductBean> recommendProducts(String name) {
		List<ProductBean> temp = productDAO.findProductsByName(name);
		ProductBean thisOne = null;
		for (int i = 0; i <= temp.size(); i++) { //
			thisOne = temp.get(i); // 找出相同名字的bean
			if (thisOne.getName().equalsIgnoreCase(name)) {
				break;
			}
		}
		if (thisOne.getName().equalsIgnoreCase(name)) {
			ProductBean findOne = thisOne;
			if (findOne != null) {
				return this.getSession()
						.createQuery("from ProductBean where type = :type order by totalSold desc", ProductBean.class)
						.setParameter("type", findOne.getType()).setMaxResults(8).getResultList();
			}
		}
		return null;
	}

	public List<ProductBean> findProductsByBrand(String brand) {
		return productDAO.findProductsByBrand(brand);
	}

	public List<ProductBean> findProductsByPrice(Integer minPrice, Integer maxPrice) {
		return productDAO.findProductsByPriceBetween(minPrice, maxPrice);
	}

	public List<ProductBean> findProductsByUpdatedTime(Date startDay, Date endDay) {
		return productDAO.findProductsByUpdatedTimeDayBetween(startDay, endDay);
	}

	public List<ProductBean> findProductsByName(String name) {
		return productDAO.findProductsByName(name);
	}

	public List<ProductBean> findProductsByTotalSold(Integer top) {
		Query<ProductBean> query = this.getSession().createQuery("from ProductBean", ProductBean.class);
		query.setMaxResults(top);
		return query.getResultList();
	}

	public List<ProductBean> findProductsByType(String type) {
		return productDAO.findProductsByType(type);
	}

	public List<SerialNumberBean> insertProductsSN(Long id, Integer stock) {
		ProductBean temp = productDAO.findProductByProductId(id);
		if (temp != null) {
			temp.setStock(stock);
			productDAO.updateProduct(temp);
			return productDAO.insertProductSerialNumberByProductId(id, stock);
		} else {
			return null;
		}
	}

	public List<SerialNumberBean> findProductStatus(Long id, String status) {
		if (id == null) {
			if (status.equalsIgnoreCase("sold")) {
				return productDAO.findSerialNumbersAreSold();
			} else {
				return productDAO.findSerialNumbersAreAvailable();
			}
		} else {
			if (status.equalsIgnoreCase("sold")) {
				return productDAO.findSerialNumbersAreSoldByProductId(id);
			} else {
				return productDAO.findSerialNumbersAreAvailableByProductId(id);
			}
		}
	}

	public SerialNumberBean updateSNStatus(SerialNumberBean serialNumberBean) {
		if (serialNumberBean != null) {
			return productDAO.updateAvailabilityStatus(serialNumberBean);
		}
		return null;
	}

	public List<String> findProductTypes() {
		return productDAO.findProductTypes();
	}
}
