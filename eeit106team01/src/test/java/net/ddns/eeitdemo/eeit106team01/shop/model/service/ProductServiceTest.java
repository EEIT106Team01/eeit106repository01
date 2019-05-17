package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import static org.junit.Assert.fail;

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

	@Autowired private ProductService productService;
	
//	@Test
	public void testInsertProduct() {
		ProductBean productBean1 = new ProductBean();
		productBean1.setName("Dash Cam 1080P 高清無碼");
		productBean1.setCreateTime();
		productBean1.setUpdatedTime();
		productBean1.setType("行車紀錄器");
		productBean1.setBrand("ABC");
		productBean1.setPrice(49);
		productBean1.setStock(3);
//		productBean1.setDescription(
//				"[1080P 全高清 & 170°廣角角度] 卓越的 1080P 全高清分辨率，170° 廣角鏡頭提供您出色的畫質，視野範圍更廣，可有效減少百葉窗點、每個駕駛流程的真實記錄。\r\n"
//						+ "[SUPER NIGHT VISION] 配備 Advanced Sensor 和 F1.8 寬光圈，收集更多光源，消除即使在低光環境下的輔助光源需求，輕鬆獲得銳利、顏色準確的圖像，為您帶來更明亮的夜晚，而不會產生更明亮的光線。 即使是在晚上，也可以確保圖像的清晰度。\r\n"
//						+ "[G-SENSOR & EMERGENCY ACCIDENT LOCK] 內建 G 感應器技術可在碰撞或碰撞發生時自動保存緊急活動的鏡頭，防止真實影片源被刪除。 您可以放心，您的中控台凸輪將捕捉關鍵細節 - 無論是駕駛或讓它監控您的停車車輛。\r\n"
//						+ "[循環錄製和動態測試] 貼身功能可確保 SD 卡永不填滿，您將始終能夠捕捉最新影片、自動錄製並鎖定影片文件，若發現碰撞或晃動車輛動作，這會消除手動刪除文件並節省保險索賠中的重要證據。\r\n"
//						+ "[迷你尺寸 & 易於使用] C420 中控台凸輪配備 2.0 吋 TFT-LCD 螢幕，緊湊的按鈕設計使操作更簡單，較小的尺寸更實際也更舒適，同時也避免在駕駛時透過擋風玻璃和後視鏡阻擋視線。 安裝時間約為 2 分鐘，從盒子到窗戶。");
		JsonObject jsonObject1 = new JsonObject();
		jsonObject1.addProperty("Model Number", "C420");
		jsonObject1.addProperty("Camera Type", "Single");
		
		@SuppressWarnings("serial")
		HashMap<String, String> jsonMap1 = new Gson().fromJson(jsonObject1.toString(),
				new TypeToken<HashMap<String, String>>() {
				}.getType());

//		productBean1.setInformation(jsonMap1);
//		productBean1.setImageLink("https://www.amazon.com/dp/B07GFF7NLB/ref=emc_b_5_i");

		productService.insertProduct(productBean1);
	}

//	@Test
	public void testUpdateProduct() {
		ProductBean productBean2 = new ProductBean();
		productBean2.setId(1L);
		productBean2.setName("Dash Cam 1080P");
//		productBean2.setCreateTime();  //不用紀錄
		productBean2.setUpdatedTime();
		productBean2.setType("行車紀錄器");
		productBean2.setBrand("ABC");
		productBean2.setPrice(99);
		productBean2.setStock(7);
//		productBean2.setDescription("[1080P 全高清 & 170°廣角角度] ");
		JsonObject jsonObject1 = new JsonObject();
		jsonObject1.addProperty("Model Number", "A123");
		jsonObject1.addProperty("Camera Type", "Single");
		
		@SuppressWarnings("serial")
		HashMap<String, String> jsonMap1 = new Gson().fromJson(jsonObject1.toString(),
				new TypeToken<HashMap<String, String>>() {
				}.getType());
		
//		productBean2.setInformation(jsonMap1);
//		productBean2.setImageLink("https://www.amazon.com/dp/B07GFF7NLB/ref=emc_b_5_i");

		productService.updateProduct(productBean2);
	}

//	@Test
	public void testFindProductByPrimaryKey() {
		ProductBean ProductBean3 = productService.findProductByPrimaryKey(1L);
		System.out.println("------testFindProductByPrimaryKey--------");
		System.out.println(ProductBean3.getId());
		System.out.println(ProductBean3.getBrand());
		System.out.println(ProductBean3.getDescription());
		System.out.println(ProductBean3.getImageLink());
//		System.out.println(ProductBean3.getInformation());
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
		System.out.println(productService.findProductsByBrand("ABC"));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByPrice() {
		System.out.println(productService.findProductsByPrice(0, 1000));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByUpdatedTime() {
		System.out.println(productService.findProductsByUpdatedTime(-30));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByTotalSold() {
		System.out.println(productService.findProductsByTotalSold(1));
		System.out.println("========================================");
	}

//	@Test
	public void testInsertProductsSN() throws Exception {
		System.out.println(productService.insertProductsSN(1L, 10));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductStatus() throws Exception {
		System.out.println(productService.findProductStatus( null, "sold"));
		System.out.println("========================================");
	}
}
