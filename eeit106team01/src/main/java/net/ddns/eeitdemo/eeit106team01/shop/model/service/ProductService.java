package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.DataBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

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
//			findOne.setTotalSold(productBean.getTotalSold());
			findOne.setImageLink(productBean.getImageLink());
			findOne.setInformationImageLink(productBean.getInformationImageLink());
			return productDAO.updateProduct(findOne);
		}
		return null;
	}

	public ProductBean findProductByPrimaryKey(Long id) {
		return productDAO.findProductByProductId(id);
	}

	public ProductBean getRandomProduct() {
		Random ran = new Random();
		List<ProductBean> products = productDAO.findProducts();
		if(NullChecker.isEmpty(products) == false) {
			while(true) {
				Integer id = ran.nextInt(products.size())+1;
				Long ID = Long.valueOf(id);
				ProductBean result = productDAO.findProductByProductId(ID);
				if(NullChecker.isEmpty(result) == false) {
					return result;
				}
			}
		}
		return null;
	}
	
	public List<ProductBean> findProducts() {
		return productDAO.findProducts();
	}

	public List<ProductBean> recommendProducts(String type) {
		List<ProductBean> temp = productDAO.findProductsByType(type);
		if(NullChecker.isEmpty(temp) == false) {
			return this.getSession()
					.createQuery("from ProductBean where type = :type order by totalSold desc", ProductBean.class)
					.setParameter("type", type).setMaxResults(10).getResultList();
		}
		return null;
	}

	public List<ProductBean> findProductsByBrand(String brand,String type) {
		return productDAO.findProductsByBrand(brand,type);
	}

	public List<ProductBean> findProductsByNameBrandTypeAndOrderByPriceBetween(String byNameBrandType,
			String queryString,String type, Integer minPrice, Integer maxPrice) {
		return productDAO.findProductsByNameBrandTypeAndOrderByPriceBetween(byNameBrandType, queryString,type, minPrice,
				maxPrice);
	}

	public List<ProductBean> findProductsByUpdatedTime(String dataName,String queryString,Date startDay, Date endDay,String brandType) {
		return productDAO.findProductsByUpdatedTimeDayBetween(dataName,queryString,startDay, endDay,brandType);
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
	
	public List<ProductBean> findProductsByTypeName(String type, String name) {
		return productDAO.findProductsByTypeName(type, name);
	}
	public List<ProductBean> findProductsSort(String dataName,String queryString,String type,String sort,String brandType){
		return productDAO.findProductsSort(dataName, queryString, type, sort,brandType);
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

	public List<DataBean> findProductData(String dataName, String type) {
		List<DataBean> result = productDAO.findProductData(dataName, type);
		if (result != null) {
			return result;
		}
		return null;
	}
}
