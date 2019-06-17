package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.DataBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;
import net.ddns.eeitdemo.eeit106team01.shop.util.SerialNumberGenerator;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private List<ProductBean> productsResutlt = new ArrayList<ProductBean>();
	private List<SerialNumberBean> serialNumbersResult = new ArrayList<SerialNumberBean>();

	@Override
	public ProductBean insertProduct(ProductBean productBean) {
		if (productBean.isNotNull()) {
			try {
				this.getSession().save(productBean);
				Long id = productBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findProductByProductId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public ProductBean updateProduct(ProductBean productBean) {
		if (productBean.isNotNull()) {
			try {
				this.getSession().update(productBean);
				Long id = productBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findProductByProductId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public ProductBean findProductByProductId(Long id) {
		if (id != null && id.longValue() > 0L) {
			try {
				ProductBean result = this.getSession().get(ProductBean.class, id);
				if (result != null) {
					return result;
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<ProductBean> findProducts() {
		try {
			this.productsResutlt = this.getSession().createQuery("from ProductBean order by totalSold desc", ProductBean.class).getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
			return this.productsResutlt;
		} else {
			return null;
		}
	}

	@Override
	public List<ProductBean> findProductsByName(String name) {
		if (NullChecker.isEmpty(name) == false) {
			try {
				this.productsResutlt = this.getSession()
						.createQuery("from ProductBean where name like :name", ProductBean.class)
						.setParameter("name", "%" + name + "%").getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
				return this.productsResutlt;
			}
		}
		return null;
	}

	@Override
	public List<ProductBean> findProductsByBrand(String brand,String type) {
		if (NullChecker.isEmpty(brand) == false) {
			try {
				if(NullChecker.isEmpty(type) == true && NullChecker.isEmpty(brand) == false) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where brand =:brand", ProductBean.class)
							.setParameter("brand", brand).getResultList();
				}
				else if(NullChecker.isEmpty(type) == false && NullChecker.isEmpty(brand) == false) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where brand =:brand and type= :type", ProductBean.class)
							.setParameter("brand", brand)
							.setParameter("type", type)
							.getResultList();
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
				return this.productsResutlt;
			}
		}
		return null;
	}

	@Override
	public List<ProductBean> findProductsByStock(String emptyORnotEmpty) {
		if (NullChecker.isEmpty(emptyORnotEmpty) == false) {
			try {
				if (emptyORnotEmpty.equals("notEmpty")) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where stock > 0", ProductBean.class).getResultList();
				} else if (emptyORnotEmpty.equals("empty")) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where stock = 0", ProductBean.class).getResultList();
				} else {
					throw new IllegalArgumentException(
							"Unexpected input accept empty OR notEmpty only with case sensitive");
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
				return this.productsResutlt;
			}
		}
		return null;
	}

	@Override
	public List<ProductBean> findProductsByType(String type) {
		if (NullChecker.isEmpty(type) == false) {
			try {
				this.productsResutlt = this.getSession()
						.createQuery("from ProductBean where type =:type", ProductBean.class).setParameter("type", type)
						.getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
				return this.productsResutlt;
			}
		}
		return null;
	}

	@Override
	public List<ProductBean> findProductsByNameBrandTypeAndOrderByPriceBetween(String byNameBrandType,
			String queryString,String type, Integer minPrice, Integer maxPrice) {
		if (NullChecker.isEmpty(byNameBrandType) == false && NullChecker.isEmpty(queryString) == false
				&& minPrice != null && minPrice >= 0 && maxPrice != null && maxPrice >= minPrice) {
			try {
				if (byNameBrandType.equalsIgnoreCase("name") && NullChecker.isEmpty(type) == true) {
					this.productsResutlt = this.getSession()
							.createQuery(
									"from ProductBean where name like :name and price between :minPrice and :maxPrice",
									ProductBean.class)
							.setParameter("name", "%" + queryString + "%").setParameter("minPrice", minPrice)
							.setParameter("maxPrice", maxPrice).getResultList();
				}else if (byNameBrandType.equalsIgnoreCase("name") && NullChecker.isEmpty(type) == false) {
					this.productsResutlt = this.getSession()
							.createQuery(
									"from ProductBean where name like :name and type = :type price between :minPrice and :maxPrice",
									ProductBean.class)
							.setParameter("name", "%" + queryString + "%").setParameter("type",type)
							.setParameter("minPrice", minPrice).setParameter("maxPrice", maxPrice).getResultList();
				} else if (byNameBrandType.equalsIgnoreCase("brand") && NullChecker.isEmpty(type) == false) {
					this.productsResutlt = this.getSession()
							.createQuery(
									"from ProductBean where brand= :brand and type = :type and price between :minPrice and :maxPrice",
									ProductBean.class)
							.setParameter("brand", queryString).setParameter("minPrice", minPrice)
							.setParameter("maxPrice", maxPrice).setParameter("type", type).getResultList();
				} else if (byNameBrandType.equalsIgnoreCase("brand") && NullChecker.isEmpty(type) == true) {
					this.productsResutlt = this.getSession()
							.createQuery(
									"from ProductBean where brand= :brand and price between :minPrice and :maxPrice",
									ProductBean.class)
							.setParameter("brand", queryString).setParameter("minPrice", minPrice)
							.setParameter("maxPrice", maxPrice).getResultList();
				}else if (byNameBrandType.equalsIgnoreCase("type")) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where type= :type and price between :minPrice and :maxPrice",
									ProductBean.class)
							.setParameter("type", queryString).setParameter("minPrice", minPrice)
							.setParameter("maxPrice", maxPrice).getResultList();
				} else {
					throw new IllegalArgumentException("enter name, brand or type");
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
				return this.productsResutlt;
			}
		}
		return null;
	}

	/**
	 * @param startDay = day start
	 * @param endDay   = day end
	 * @return Between startDay and endDay
	 */
	@Override
	public List<ProductBean> findProductsByUpdatedTimeDayBetween(String dataName,String queryString,Date startDay, Date endDay,String brandType) {
		if (startDay != null && endDay != null && startDay.equals(endDay) == false && startDay.compareTo(endDay) < 0) {
			try {
				if(dataName.equalsIgnoreCase("type")) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where type = :type and updatedTime BETWEEN :startDay AND :endDay",
									ProductBean.class)
							.setParameter("type",queryString)
							.setParameter("startDay", startDay).setParameter("endDay", endDay).getResultList();				
				}else if(dataName.equalsIgnoreCase("brand")) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where brand = :brand and type = :type and updatedTime BETWEEN :startDay AND :endDay",
									ProductBean.class)
							.setParameter("brand",queryString).setParameter("type",brandType)
							.setParameter("startDay", startDay).setParameter("endDay", endDay).getResultList();			
				}else if(dataName.equalsIgnoreCase("name") &&  NullChecker.isEmpty(brandType) == true) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where name like :name and updatedTime BETWEEN :startDay AND :endDay",
									ProductBean.class)
							.setParameter("name","%"+queryString+"%")
							.setParameter("startDay", startDay).setParameter("endDay", endDay).getResultList();			
				}else if(dataName.equalsIgnoreCase("name") && NullChecker.isEmpty(brandType) == false) {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where name like :name and type = :type and updatedTime BETWEEN :startDay AND :endDay",
									ProductBean.class)
							.setParameter("name","%"+queryString+"%").setParameter("type",brandType)
							.setParameter("startDay", startDay).setParameter("endDay", endDay).getResultList();	
				}else {
					this.productsResutlt = this.getSession()
							.createQuery("from ProductBean where updatedTime BETWEEN :startDay AND :endDay order by convert(datetime, UpdatedTime, 121) DESC",
									ProductBean.class)
							.setParameter("startDay", startDay).setParameter("endDay", endDay).getResultList();			
				}
				
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
				return this.productsResutlt;
			}
		}
		return null;
	}

	@Override
	public List<SerialNumberBean> insertProductSerialNumberByProductId(Long id, Integer stock) {
		if (id != null && id.longValue() > 0L && stock != null && stock.intValue() > 0) {
			List<SerialNumberBean> result = new ArrayList<SerialNumberBean>();
			try {
				flag: for (int count = 1; count <= stock; count++) {
					SerialNumberGenerator serialNumberGenerator = new SerialNumberGenerator(20);
					String serialNumber = serialNumberGenerator.nextString();
					this.serialNumbersResult = this.getSession()
							.createQuery("from SerialNumberBean where serialNumber = :serialNumber",
									SerialNumberBean.class)
							.setParameter("serialNumber", serialNumber).getResultList();
					if (this.serialNumbersResult.size() == 0) {
						SerialNumberBean serialNumberBean = new SerialNumberBean();
						serialNumberBean.setProductId(this.findProductByProductId(id));
						serialNumberBean.setAvailabilityStatus("available");
						serialNumberBean.setSerialNumber(serialNumber);
						this.getSession().save(serialNumberBean);
						result.add(serialNumberBean);
					} else {
						break flag;
					}
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (result != null && result.size() > 0) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<SerialNumberBean> findSerialNumbersAreSold() {
		try {
			this.serialNumbersResult = this.getSession()
					.createQuery("from SerialNumberBean where availabilityStatus = :availabilityStatus",
							SerialNumberBean.class)
					.setParameter("availabilityStatus", "sold").getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.serialNumbersResult != null && this.serialNumbersResult.size() > 0) {
			return this.serialNumbersResult;
		}
		return null;
	}

	@Override
	public List<SerialNumberBean> findSerialNumbersAreSoldByProductId(Long id) {
		if (id != null && id.longValue() > 0L) {
			try {
				this.serialNumbersResult = this.getSession().createQuery(
						"from SerialNumberBean where availabilityStatus = :availabilityStatus and productId = :id",
						SerialNumberBean.class).setParameter("availabilityStatus", "sold").setParameter("id", id)
						.getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.serialNumbersResult != null && this.serialNumbersResult.size() > 0) {
				return this.serialNumbersResult;
			}
		}
		return null;
	}

	@Override
	public List<SerialNumberBean> findSerialNumbersAreAvailable() {
		try {
			this.serialNumbersResult = this.getSession()
					.createQuery("from SerialNumberBean where availabilityStatus = :availabilityStatus",
							SerialNumberBean.class)
					.setParameter("availabilityStatus", "available").getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.serialNumbersResult != null && this.serialNumbersResult.size() > 0) {
			return this.serialNumbersResult;
		}
		return null;
	}

	@Override
	public List<SerialNumberBean> findSerialNumbersAreAvailableByProductId(Long id) {
		if (id != null && id.longValue() > 0) {
			try {
				this.serialNumbersResult = this.getSession().createQuery(
						"from SerialNumberBean where availabilityStatus = :availabilityStatus and ProductID = :id",
						SerialNumberBean.class).setParameter("availabilityStatus", "available").setParameter("id", id)
						.getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.serialNumbersResult != null && this.serialNumbersResult.size() > 0) {
				return this.serialNumbersResult;
			}
		}
		return null;
	}

	@Override
	public SerialNumberBean findSerialNumber(String serialNumber) {
		if (NullChecker.isEmpty(serialNumber) == false) {
			SerialNumberBean result = new SerialNumberBean();
			try {
				result = this.getSession()
						.createQuery("from SerialNumberBean where serialNumber = :serialNumber", SerialNumberBean.class)
						.setParameter("serialNumber", serialNumber).getSingleResult();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public SerialNumberBean updateAvailabilityStatus(SerialNumberBean serialNumberBean) {
		SerialNumberBean result = new SerialNumberBean();
		if (serialNumberBean.isNotNull()) {
			try {
				this.getSession().update(serialNumberBean);
				result = findSerialNumber(serialNumberBean.getSerialNumber());
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<SerialNumberBean> findSerialNumbersByAvailabilityStatus(String status) {
		if (NullChecker.isEmpty(status) == false) {
			try {
				this.serialNumbersResult = this.getSession()
						.createQuery("from SerialNumberBean where availabilityStatus = :availabilityStatus",
								SerialNumberBean.class)
						.setParameter("availabilityStatus", status).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.serialNumbersResult != null && this.serialNumbersResult.size() > 0) {
				return this.serialNumbersResult;
			}
		}
		return null;
	}

	@Override
	public List<SerialNumberBean> findSerialNumbersByProductIdAndAvailabilityStatus(Long id, String status) {
		if (id != null && id.longValue() > 0L && NullChecker.isEmpty(status) == false) {
			try {
				this.serialNumbersResult = this.getSession().createQuery(
						"from SerialNumberBean where availabilityStatus = :availabilityStatus and ProductID = :ProductBean_Id",
						SerialNumberBean.class).setParameter("availabilityStatus", status)
						.setParameter("ProductBean_Id", id).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.serialNumbersResult != null && this.serialNumbersResult.size() > 0) {
				return this.serialNumbersResult;
			}
		}
		return null;
	}

	@Override
	public List<DataBean> findProductData(String dataName, String type) {
		if (NullChecker.isEmpty(dataName) == false) {
			List<DataBean> result = new ArrayList<DataBean>();
			List<?> list;
			try {
				if (dataName.equalsIgnoreCase("type")) {
					list = this.getSession().createQuery("select distinct type from ProductBean").getResultList();
				} else if (dataName.equalsIgnoreCase("brand") && NullChecker.isEmpty(type) == false) {
					list = this.getSession().createQuery("select distinct brand from ProductBean where type= :type")
							.setParameter("type", type).getResultList();
				} else {
					throw new IllegalArgumentException("enter type or brand, and type is a must in brand search");
				}
				if (list != null && list.size() > 0) {
					Iterator<?> iterator = list.iterator();
					while (iterator.hasNext()) {
						DataBean dataBean = new DataBean();
						dataBean.setData((String) iterator.next());
						result.add(dataBean);
					}
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (result != null && result.size() > 0) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<ProductBean> findProductsByTypeName(String type, String name) {
		if (NullChecker.isEmpty(name) == false && (NullChecker.isEmpty(type) == false)){
			try {
				this.productsResutlt = this.getSession()
						.createQuery("from ProductBean where name like :name and type= :type", ProductBean.class)
						.setParameter("name", "%" + name + "%")
						.setParameter("type", type)
						.getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
				return this.productsResutlt;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> findProductsSort(String dataName,String queryString,String type,String sort,String brandType) {
		try {
			if (sort.equalsIgnoreCase("desc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("type") && type.equalsIgnoreCase("price")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where type = :queryString order by price desc")
						.setParameter("queryString", queryString)
						.getResultList();
			}else if(sort.equalsIgnoreCase("desc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("type") && type.equalsIgnoreCase("totalSold")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where type = :queryString order by totalSold desc")
						.setParameter("queryString", queryString)
						.getResultList();
			}else if(sort.equalsIgnoreCase("desc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("brand") && type.equalsIgnoreCase("price")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where brand = :queryString and type = :brandType order by price desc")
						.setParameter("queryString", queryString)
						.setParameter("brandType", brandType)
						.getResultList();
			}else if(sort.equalsIgnoreCase("desc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("brand") && type.equalsIgnoreCase("totalSold")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where brand = :queryString and type = :brandType order by totalSold desc")
						.setParameter("queryString", queryString)
						.setParameter("brandType", brandType)
						.getResultList();
			}else if(sort.equalsIgnoreCase("desc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("name") && type.equalsIgnoreCase("price")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where name like :queryString order by price desc")
						.setParameter("queryString", "%"+queryString+"%")
						.getResultList();
			}else if(sort.equalsIgnoreCase("desc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("name") && type.equalsIgnoreCase("totalSold")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where name like :queryString order by totalSold desc")
						.setParameter("queryString", "%"+queryString+"%")
						.getResultList();
			}else if (sort.equalsIgnoreCase("asc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("type") && type.equalsIgnoreCase("price")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where type = :queryString order by price")
						.setParameter("queryString", queryString)
						.getResultList();
			}else if(sort.equalsIgnoreCase("asc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("type") && type.equalsIgnoreCase("totalSold")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where type = :queryString order by totalSold")
						.setParameter("queryString", queryString)
						.getResultList();
			}else if(sort.equalsIgnoreCase("asc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("brand") && type.equalsIgnoreCase("price")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where brand = :queryString and type = :brandType order by price")
						.setParameter("queryString", queryString)
						.setParameter("brandType", brandType)
						.getResultList();
			}else if(sort.equalsIgnoreCase("asc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("brand") && type.equalsIgnoreCase("totalSold")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where brand = :queryString and type = :brandType order by totalSold")
						.setParameter("queryString", queryString)
						.setParameter("brandType", brandType)
						.getResultList();
			}else if(sort.equalsIgnoreCase("asc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("name") && type.equalsIgnoreCase("price")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where name like :queryString order by price")
						.setParameter("queryString", "%"+queryString+"%")
						.getResultList();
			}else if(sort.equalsIgnoreCase("asc") && NullChecker.isEmpty(queryString) == false
					&& dataName.equalsIgnoreCase("name") && type.equalsIgnoreCase("totalSold")) {
				this.productsResutlt = this.getSession().createQuery("from ProductBean where name like :queryString order by totalSold")
						.setParameter("queryString", "%"+queryString+"%")
						.getResultList();
			}else {
				throw new IllegalArgumentException("format error");
			}
			if (this.productsResutlt != null && this.productsResutlt.size() > 0) {
					return this.productsResutlt;
			}
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
	return null;
	}
}
