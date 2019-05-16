package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;

public interface ProductDAO {

	abstract ProductBean insertProduct(ProductBean productBean);

	abstract ProductBean updateProduct(ProductBean productBean);

	// find SN table by P_id and availabilityStatus
	abstract ProductBean findProductByPrimaryKey(Long id);

	abstract ProductBean findProductBySerialNumber(String serialNumber);

	abstract List<ProductBean> findProducts();

	abstract List<ProductBean> findProductsByName(String name);

	abstract List<ProductBean> findProductsByBrand(String brand);

	abstract List<ProductBean> findProductsByStock(Integer stock);

	abstract List<ProductBean> findProductsByType(String type);

	abstract List<ProductBean> findProductsByPrice(Integer minPrice, Integer maxPrice);

	abstract List<ProductBean> findProductsByTime(Integer day);

}
