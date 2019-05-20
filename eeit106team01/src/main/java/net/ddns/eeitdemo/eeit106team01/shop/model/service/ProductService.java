package net.ddns.eeitdemo.eeit106team01.shop.model.service;

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
			productBean.setUpdatedTime();
			productBean.setCreateTime();
			productBean.setTotalSold(0);
			ProductBean result = productDAO.insertProduct(productBean);
			ProductBean temp = productDAO.findProductByPrimaryKey(result.getId());
			if (temp != null) {
				productDAO.insertProductsSN(temp.getId(), temp.getStock());
				return result;
			} else {
				return null;
			}
		}
		return null;
	};

	public ProductBean updateProduct(ProductBean productBean) {
		ProductBean findOne = findProductByPrimaryKey(productBean.getId());
		if (findOne != null) {
			findOne.setBrand(productBean.getBrand());
			findOne.setUpdatedTime();
			findOne.setDescription(productBean.getDescription());
			findOne.setStock(productBean.getStock());
			findOne.setImageLink(productBean.getImageLink());
			findOne.setName(productBean.getName());
			findOne.setPrice(productBean.getPrice());
			findOne.setType(productBean.getType());
			findOne.setTotalSold(productBean.getTotalSold());
			findOne.setDescription(productBean.getDescription());
			findOne.setImageLink(productBean.getImageLink());
			return productDAO.updateProduct(findOne);
		}
		return null;
	}

	public ProductBean findProductByPrimaryKey(Long id) {
		return productDAO.findProductByPrimaryKey(id);
	};

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
				Query query = this.getSession()
						.createQuery("from ProductBean where type = :type order by totalSold desc", ProductBean.class);
				query.setParameter("type", findOne.getType());
				query.setMaxResults(5);
				return query.getResultList();
			}
		}
		return null;
	}

	public List<ProductBean> findProductsByBrand(String brand) {
		return productDAO.findProductsByBrand(brand);
	}

	public List<ProductBean> findProductsByPrice(Integer minPrice, Integer maxPrice) {
		return productDAO.findProductsByPrice(minPrice, maxPrice);
	}

	public List<ProductBean> findProductsByUpdatedTime(Integer day) {
		return productDAO.findProductsByUpdatedTime(day);
	}

	public List<ProductBean> findProductsByName(String name) {
		return productDAO.findProductsByName(name);
	}

	@SuppressWarnings("unchecked")
	public List<ProductBean> findProductsByTotalSold(Integer top) {
		Query query = this.getSession().createQuery("from ProductBean", ProductBean.class);
		query.setMaxResults(top);
		return query.getResultList();
	}

	public List<SerialNumberBean> insertProductsSN(Long id, Integer stock) {
		ProductBean temp = productDAO.findProductByPrimaryKey(id);
		if (temp != null) {
			return productDAO.insertProductsSN(id, stock);
		} else {
			return null;
		}
	}

	public List<SerialNumberBean> findProductStatus(Long id, String status) {
		if (id == null) {
			if (status.equalsIgnoreCase("sold")) {
				return productDAO.findsoldProducts();
			} else {
				return productDAO.findavailableProducts();
			}
		} else {
			if (status.equalsIgnoreCase("sold")) {
				return productDAO.findsoldProduct(id);
			} else {
				return productDAO.findavailableProduct(id);
			}
		}
	}

	public SerialNumberBean updateSNStatus(SerialNumberBean serialNumberBean) {
		if (serialNumberBean != null) {
			return productDAO.updateSNStatus(serialNumberBean);
		}
		return null;
	}
}
