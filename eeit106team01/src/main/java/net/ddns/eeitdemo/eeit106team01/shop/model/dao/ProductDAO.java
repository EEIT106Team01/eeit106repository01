package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.Date;
import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.DataBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;

public interface ProductDAO {

	abstract ProductBean insertProduct(ProductBean productBean);

	abstract ProductBean updateProduct(ProductBean productBean);

	abstract ProductBean findProductByProductId(Long id);

	abstract List<ProductBean> findProducts();

	abstract List<ProductBean> findProductsByName(String name);

	abstract List<ProductBean> findProductsByBrand(String brand,String type);

	abstract List<ProductBean> findProductsByStock(String emptyORnotEmpty);

	abstract List<ProductBean> findProductsByType(String type);
	
	abstract List<ProductBean> findProductsSort(String dataName,String queryString,String type,String sort,String brandType);

	abstract List<ProductBean> findProductsByNameBrandTypeAndOrderByPriceBetween(String byNameBrandType,
			String queryString,String type, Integer minPrice, Integer maxPrice);

	abstract List<ProductBean> findProductsByUpdatedTimeDayBetween(String dataName,String queryString,Date startDay, Date endDay,String brandType);
	
	abstract List<ProductBean> findProductsByTypeName(String type,String name);

	abstract SerialNumberBean findSerialNumber(String serialNumber);

	abstract SerialNumberBean updateAvailabilityStatus(SerialNumberBean serialNumberBean);

	abstract List<SerialNumberBean> insertProductSerialNumberByProductId(Long id, Integer stock);

	abstract List<SerialNumberBean> findSerialNumbersAreSold();

	abstract List<SerialNumberBean> findSerialNumbersAreSoldByProductId(Long id);

	abstract List<SerialNumberBean> findSerialNumbersAreAvailable();

	abstract List<SerialNumberBean> findSerialNumbersAreAvailableByProductId(Long id);

	abstract List<SerialNumberBean> findSerialNumbersByAvailabilityStatus(String availabilityStatus);

	abstract List<SerialNumberBean> findSerialNumbersByProductIdAndAvailabilityStatus(Long id,
			String availabilityStatus);

	abstract List<DataBean> findProductData(String dataName, String type);
}
