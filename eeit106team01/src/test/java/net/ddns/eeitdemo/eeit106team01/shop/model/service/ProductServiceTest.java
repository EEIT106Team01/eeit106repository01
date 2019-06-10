package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	private Date date = new Date(System.currentTimeMillis());

	@Test
	public void name() {
		
	}
	
//	@Test
	public void testInsertProduct() {
		ProductBean productBean1 = new ProductBean();
		productBean1.setName("DashCam");
		productBean1.setCreateTime(date);
		productBean1.setUpdatedTime(date);
		productBean1.setType("行車紀錄器");
		productBean1.setBrand("ABC");
		productBean1.setPrice(49);
		productBean1.setStock(3);

		ArrayList<String> array = new ArrayList<String>();
		array.add("1123567");
		JsonObject jsonObject1 = new JsonObject();
		jsonObject1.addProperty("Model Number", "C420");
		jsonObject1.addProperty("Camera Type", "Single");

		@SuppressWarnings({ "serial", "unused" })
		HashMap<String, String> jsonMap1 = new Gson().fromJson(jsonObject1.toString(),
				new TypeToken<HashMap<String, String>>() {
				}.getType());

		productService.insertProduct(productBean1);
	}

//	@Test
	public void testUpdateProduct() {
		ProductBean productBean2 = new ProductBean();
//		productBean2.setId(1L);
		productBean2.setName("Dash Cam 1080P");
		productBean2.setUpdatedTime(date);
		productBean2.setType("行車紀錄器");
		productBean2.setBrand("ABC");
		productBean2.setPrice(99);
		productBean2.setStock(7);
		JsonObject jsonObject1 = new JsonObject();
		jsonObject1.addProperty("Model Number", "A123");
		jsonObject1.addProperty("Camera Type", "Single");

		@SuppressWarnings({ "serial", "unused" })
		HashMap<String, String> jsonMap1 = new Gson().fromJson(jsonObject1.toString(),
				new TypeToken<HashMap<String, String>>() {
				}.getType());

		productService.updateProduct(productBean2);
	}

//	@Test
	public void testFindProductByPrimaryKey() {
		ProductBean ProductBean3 = productService.findProductByPrimaryKey(1L);
		System.out.println("------testFindProductByPrimaryKey--------");
		System.out.println(ProductBean3.getId());
		System.out.println(ProductBean3.getBrand());
		System.out.println(ProductBean3.getImageLink());
		System.out.println(ProductBean3.getName());
		System.out.println(ProductBean3.getType());
		System.out.println(ProductBean3.getStock());
		System.out.println(ProductBean3.getPrice());
		System.out.println(ProductBean3.getCreateTime());
		System.out.println(ProductBean3.getUpdatedTime());
		System.out.println("------------------------------------------");
	}

//	@Test
	public void testRecommendProducts() {
		System.out.println(productService.recommendProducts("Dash Cam"));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByBrand() {
//		System.out.println(productService.findProductsByBrand("ABC"));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByPrice() {
//		System.out.println(productService.findProductsByPrice(0, 1000));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByUpdatedTime() {
//		List<ProductBean> productList = productService.findProductsByUpdatedTime(-30);
//		for (int i = 0; i <= productList.size(); i++) {
//			System.out.println("id=" + productList.get(i).getId());
//		}
	}

//	@Test
	public void testFindProductsByTotalSold() {
		System.out.println(productService.findProductsByTotalSold(1));
		System.out.println("========================================");
	}

//	@Test
	public void testInsertProductsSN() throws Exception {
//		System.out.println(productService.insertProductsSN(1L, 1));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductStatus() throws Exception {
//		System.out.println(productService.findProductStatus(null, "sold"));
		
//		System.out.println(productService.findProductTypes().toString());
		System.out.println("========================================");
		
		
	}
}
