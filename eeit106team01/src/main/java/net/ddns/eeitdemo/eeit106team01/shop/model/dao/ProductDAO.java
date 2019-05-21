package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;

public interface ProductDAO {

	abstract ProductBean insertProduct(ProductBean productBean);

	abstract ProductBean updateProduct(ProductBean productBean);

	abstract ProductBean findProductByPrimaryKey(Long id);

	abstract ProductBean findProductBySerialNumber(String serialNumber);
	
	abstract SerialNumberBean findSNBeanBySerialNumber (String serialNumber);

	abstract SerialNumberBean updateSNStatus(SerialNumberBean serialNumberBean);
	
	abstract List<ProductBean> findProducts();

	abstract List<ProductBean> findProductsByName(String name);

	abstract List<ProductBean> findProductsByBrand(String brand);

	abstract List<ProductBean> findProductsByStock(String stockType);

	abstract List<ProductBean> findProductsByType(String type);

	abstract List<ProductBean> findProductsByPrice(Integer minPrice, Integer maxPrice);

	abstract List<ProductBean> findProductsByUpdatedTime(Integer day);
	
	abstract List<SerialNumberBean> insertProductsSN(Long id,Integer stock);
	
	abstract List<SerialNumberBean> findsoldProducts();
	
	abstract List<SerialNumberBean> findsoldProduct(Long id);
	
	abstract List<SerialNumberBean> findavailableProducts();
	
	abstract List<SerialNumberBean> findavailableProduct(Long id);
	
	
}
