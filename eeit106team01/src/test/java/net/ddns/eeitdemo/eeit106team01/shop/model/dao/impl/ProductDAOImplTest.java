package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.gson.JsonObject;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDAOImplTest {

@Autowired
private ProductDAO productDAO;
	
//	@Test
	public void testInsertProduct() {
		ProductBean productBean1 = new ProductBean();
		productBean1.setName("Dash Cam 1080P 全高清迷你汽車行駛記錄儀 170° 廣角，運動檢測，G-Sensor，循環記錄，夜視功能");
		productBean1.setCreateTime();
		productBean1.setUpdatedTime();
		productBean1.setType("行車紀錄器");
		productBean1.setBrand("APEMAN");
		productBean1.setPrice(49);
		productBean1.setStock(3);
		productBean1.setDescription(
				"[1080P 全高清 & 170°廣角角度] 卓越的 1080P 全高清分辨率，170° 廣角鏡頭提供您出色的畫質，視野範圍更廣，可有效減少百葉窗點、每個駕駛流程的真實記錄。\r\n"
						+ "[SUPER NIGHT VISION] 配備 Advanced Sensor 和 F1.8 寬光圈，收集更多光源，消除即使在低光環境下的輔助光源需求，輕鬆獲得銳利、顏色準確的圖像，為您帶來更明亮的夜晚，而不會產生更明亮的光線。 即使是在晚上，也可以確保圖像的清晰度。\r\n"
						+ "[G-SENSOR & EMERGENCY ACCIDENT LOCK] 內建 G 感應器技術可在碰撞或碰撞發生時自動保存緊急活動的鏡頭，防止真實影片源被刪除。 您可以放心，您的中控台凸輪將捕捉關鍵細節 - 無論是駕駛或讓它監控您的停車車輛。\r\n"
						+ "[循環錄製和動態測試] 貼身功能可確保 SD 卡永不填滿，您將始終能夠捕捉最新影片、自動錄製並鎖定影片文件，若發現碰撞或晃動車輛動作，這會消除手動刪除文件並節省保險索賠中的重要證據。\r\n"
						+ "[迷你尺寸 & 易於使用] C420 中控台凸輪配備 2.0 吋 TFT-LCD 螢幕，緊湊的按鈕設計使操作更簡單，較小的尺寸更實際也更舒適，同時也避免在駕駛時透過擋風玻璃和後視鏡阻擋視線。 安裝時間約為 2 分鐘，從盒子到窗戶。");
		JsonObject jsonObject1 = new JsonObject();
		jsonObject1.addProperty("Model Number", "C420");
		jsonObject1.addProperty("Camera Type", "Single");
		productBean1.setInformation(jsonObject1.toString());
		productBean1.setImageLink("https://www.amazon.com/dp/B07GFF7NLB/ref=emc_b_5_i");
		
		productDAO.insertProduct(productBean1);
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
		productBean2.setDescription(
				"[1080P 全高清 & 170°廣角角度] ");
		JsonObject jsonObject1 = new JsonObject();
		jsonObject1.addProperty("Model Number", "A123");
		jsonObject1.addProperty("Camera Type", "Single");
		productBean2.setInformation(jsonObject1.toString());
		productBean2.setImageLink("https://www.amazon.com/dp/B07GFF7NLB/ref=emc_b_5_i");
		
		productDAO.updateProduct(productBean2);
	}

//	@Test
	public void testFindProductByPrimaryKey() {
		
		ProductBean ProductBean3 = productDAO.findProductByPrimaryKey(1L);
		System.out.println("------testFindProductByPrimaryKey--------");
		System.out.println(ProductBean3.getId());
		System.out.println(ProductBean3.getBrand());
		System.out.println(ProductBean3.getDescription());
		System.out.println(ProductBean3.getImageLink());
		System.out.println(ProductBean3.getInformation());
		System.out.println(ProductBean3.getName());
		System.out.println(ProductBean3.getType());
		System.out.println(ProductBean3.getStock());
		System.out.println(ProductBean3.getPrice());
		System.out.println(ProductBean3.getCreateTime());
		System.out.println(ProductBean3.getUpdatedTime());
		System.out.println("------------------------------------------");
	}

//	@Test
	public void testFindProductBySerialNumber() {
		
	}

//	@Test
	public void testFindProducts() {
		System.out.println(productDAO.findProducts());
			System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByName() {
		System.out.println(productDAO.findProductsByName("Dash"));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByBrand() {
		System.out.println(productDAO.findProductsByBrand("ABC"));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByStock() {
		System.out.println(productDAO.findProductsByStock("notEmpty"));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByType() {
		System.out.println(productDAO.findProductsByType("行車紀錄器"));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByPrice() {
		System.out.println(productDAO.findProductsByPrice(0, 1000));
		System.out.println("========================================");
	}

//	@Test
	public void testFindProductsByTime() {
		System.out.println(productDAO.findProductsByTime(-30));
		System.out.println("========================================");
	}
}
