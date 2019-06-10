package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDAOImplTest {

	@Autowired
	private ProductDAO productDAO;

	private Date date = new Date(System.currentTimeMillis());
	private static HashMap<Integer, String> isHashMap = new HashMap<Integer, String>();
	private static HashMap<String, String> ssHashMap = new HashMap<String, String>();
	private ProductBean productBean = new ProductBean(date, date, "", "", "", 0, 0, 0, ssHashMap, isHashMap, isHashMap);

	@BeforeClass
	public static void beforeClass() {
		isHashMap.put(0, "");
		ssHashMap.put("", "");
	}

	
	@Test
	public void name() {
		
	}
	
	public void testInsertProduct() {
		productBean.setName("test1");
		productBean.setBrand("brand1");
		productBean.setType("testType");
		productBean.setStock(5);
		assertNotNull(productDAO.insertProduct(productBean));
	}

	public void testUpdateProduct() {
		productBean = productDAO.findProductByProductId(1L);
		productBean.setPrice(789);
		productBean.setUpdatedTime(date);
		productBean.setStock(2);
		assertEquals(productDAO.updateProduct(productBean).getPrice(), new Integer(789));
	}

	public void testFindProductByProductId() {
		assertNull(productDAO.findProductByProductId(-1L));
		assertNotNull(productDAO.findProductByProductId(1L));
	}

	public void testFindProducts() {
		assertNull(productDAO.findProducts());
	}

	public void testFindProductsByName() {
		assertNotNull(productDAO.findProductsByName("test"));
		assertNull(productDAO.findProductsByName("0"));
	}

	public void testFindProductsByBrand() {
		assertNotNull(productDAO.findProductsByBrand("brand1","行車紀錄器"));
		assertNull(productDAO.findProductsByBrand("0",""));
	}

	public void testFindProductsByStock() {
		assertNotNull(productDAO.findProductsByStock("empty"));
		assertNull(productDAO.findProductsByStock("notEmpty"));
	}

	public void testFindProductsByType() {
		assertNotNull(productDAO.findProductsByType("testType"));
		assertNull(productDAO.findProductsByType("1"));
	}

	public void testFindProductsByNameBrandTypeAndOrderByPriceBetween() {
		assertEquals(
				new Integer(
						productDAO.findProductsByNameBrandTypeAndOrderByPriceBetween("name", "行車王","行車紀錄器", 1000, 2000).size()),
				new Integer(29));
		assertEquals(
				new Integer(productDAO
						.findProductsByNameBrandTypeAndOrderByPriceBetween("brand", "CARSCAM行車王","行車紀錄器", 1000, 2000).size()),
				new Integer(28));
		assertEquals(new Integer(
				productDAO.findProductsByNameBrandTypeAndOrderByPriceBetween("type", "行車紀錄器","行車紀錄器", 1000, 2000).size()),
				new Integer(178));
	}

	public void testFindProductsByTime() throws ParseException {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		assertNotNull(productDAO.findProductsByUpdatedTimeDayBetween(simpleDateFormat.parse("2019-05-24"),
//				simpleDateFormat.parse("2019-05-25")));
//		assertNull(productDAO.findProductsByUpdatedTimeDayBetween(simpleDateFormat.parse("2019-05-23"),
//				simpleDateFormat.parse("2019-05-24")));
//		assertNull(productDAO.findProductsByUpdatedTimeDayBetween(simpleDateFormat.parse("2019-05-24"),
//				simpleDateFormat.parse("2019-05-23")));
//		assertNull(productDAO.findProductsByUpdatedTimeDayBetween(simpleDateFormat.parse("2019-05-24"),
//				simpleDateFormat.parse("2019-05-24")));
	}

	public void testInsertProductSerialNumberByProductId() throws Exception {
		assertNotNull(productDAO.insertProductSerialNumberByProductId(1L, 5));
	}

	public void testFindSerialNumbersAreSold() throws Exception {
		assertNull(productDAO.findSerialNumbersAreSold());
	}

	public void testFindSerialNumbersAreSoldByProductId() throws Exception {
		assertNull(productDAO.findSerialNumbersAreSoldByProductId(1L));
	}

	public void testFindSerialNumbersAreAvailable() throws Exception {
		assertNotNull(productDAO.findSerialNumbersAreAvailable());
	}

	public void testFindSerialNumbersAreAvailableByProductId() throws Exception {
		assertNotNull(productDAO.findSerialNumbersAreAvailableByProductId(1L));
		System.out.println(productDAO.findSerialNumbersAreAvailableByProductId(1L).toString());
	}

	public void testFindSerialNumber() throws Exception {
		assertNotNull(productDAO.findSerialNumber("RKjDE5ChHeRhrc3oeGOL"));
	}

	public void testUpdateAvailabilityStatus() throws Exception {
		SerialNumberBean serialNumberBean = productDAO.findSerialNumber("RKjDE5ChHeRhrc3oeGOL");
		serialNumberBean.setAvailabilityStatus("sold");
		assertNotNull(productDAO.updateAvailabilityStatus(serialNumberBean));
	}

	public void testFindSerialNumbersByAvailabilityStatus() throws Exception {
		assertNotNull(productDAO.findSerialNumbersByAvailabilityStatus("available"));
		assertNotNull(productDAO.findSerialNumbersByAvailabilityStatus("sold"));
		assertNull(productDAO.findSerialNumbersByAvailabilityStatus(""));
	}

	public void testFindSerialNumbersByProductIdAndAvailabilityStatus() throws Exception {
		assertNotNull(productDAO.findSerialNumbersByProductIdAndAvailabilityStatus(1L, "sold"));
		assertNull(productDAO.findSerialNumbersByProductIdAndAvailabilityStatus(2L, "sold"));
	}

	
	public void testFindProductData() throws Exception {
		assertEquals(new Integer(productDAO.findProductData("type", null).size()), new Integer(6));
		assertEquals(new Integer(productDAO.findProductData("brand", "行車紀錄器").size()), new Integer(162));
	}
	
	public void testFindProductsByTypeName() throws Exception{
		assertNotNull(productDAO.findProductsByTypeName("行車紀錄器", "快譯通"));
		assertNull(productDAO.findProductsByTypeName("", ""));
	}
//	@Test
	public void testFindProductsSort() throws Exception{
		assertNotNull(productDAO.findProductsSort("type", "行車紀錄器","totalSold","desc",""));
		assertNotNull(productDAO.findProductsSort("type", "行車紀錄器","price","asc",""));
		assertNotNull(productDAO.findProductsSort("name", "行車紀錄器","totalSold","desc",""));
		assertNotNull(productDAO.findProductsSort("name", "行車紀錄器","price","asc",""));
		assertNotNull(productDAO.findProductsSort("brand", "Abee快譯通","totalSold","desc","行車紀錄器"));
		assertNotNull(productDAO.findProductsSort("brand", "Abee快譯通","price","asc","行車紀錄器"));
	}

}
