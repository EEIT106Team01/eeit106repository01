package net.ddns.eeitdemo.eeit106team01.shop;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.ddns.eeitdemo.eeit106team01.shop.model.MemberBeanTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertData extends ShopTest {

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void member() {

		Session session = sessionFactory.openSession();

		MemberBeanTest beanTest1 = new MemberBeanTest();
		beanTest1.setAccount("alex123");

		MemberBeanTest beanTest2 = new MemberBeanTest();
		beanTest2.setAccount("babby456");

		MemberBeanTest beanTest3 = new MemberBeanTest();
		beanTest3.setAccount("cat159");

		MemberBeanTest beanTest4 = new MemberBeanTest();
		beanTest4.setAccount("dick7889");

		MemberBeanTest beanTest5 = new MemberBeanTest();
		beanTest5.setAccount("fanny999");

		session.save(beanTest1);
		session.save(beanTest2);
		session.save(beanTest3);
		session.save(beanTest4);
		session.save(beanTest5);

		session.beginTransaction().commit();
		session.close();
	}

//	@Test
	public void product() {

		Session session = sessionFactory.openSession();

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

		@SuppressWarnings("serial")
		HashMap<String, String> jsonMap1 = new Gson().fromJson(jsonObject1.toString(),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());

		productBean1.setInformation(jsonMap1);
		productBean1.setImageLink("https://www.amazon.com/dp/B07GFF7NLB/ref=emc_b_5_i");

		ProductBean productBean2 = new ProductBean();
		productBean2.setName(
				"Dash Cam 1080P FHD DVR Car Driving Recorder 3\" LCD Screen 170°Wide Angle, G-Sensor, WDR, Parking Monitor, Loop Recording, Motion Detection");
		productBean2.setCreateTime();
		productBean2.setUpdatedTime();
		productBean2.setType("行車紀錄器");
		productBean2.setBrand("APEMAN");
		productBean2.setPrice(44);
		productBean2.setStock(4);
		productBean2.setDescription(
				"【1080P FULL HD DASH CAM】Simultaneous recording with Super High Resolution 1080P FHD Lens, and 3” large LCD Screen deliver clearer videos&images and replay the key moment even when high speed driving.\r\n"
						+ "【170° SUPER WIDE ANGLE】This car driving recorder employs 170°super wide angle lens. Ultra-wide field of view reduces the blind spots and captures more details, to reserve the real scene.\r\n"
						+ "【BUILT IN G-SENSOR】With built-in G-sensor, apeman dashboard camera can automatically detect a sudden shake/collision and lock the footage to prevent the video from being overwritten even in loop recording. Accident scene can be truly restored.\r\n"
						+ "【SUPER NIGHT VISION】The combination of F1.8 large aperture, WDR, HDR ensures the clarity of images at night. Eliminates the need for an auxiliary light source even in low-light environments, making it easy to obtain sharp, color-accurate images.\r\n"
						+ "【EASY TO SET UP & USE】 Motion Detection, Seamless Loop Recording, Parking Monitor satisfy your need for daily driving. Simple installation and operation, no need to worry even for a first time user.");
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("Model Number", "C450");
		jsonObject2.addProperty("Camera Type", "Single");

		@SuppressWarnings("serial")
		HashMap<String, String> jsonMap2 = new Gson().fromJson(jsonObject2.toString(),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());

		productBean2.setInformation(jsonMap2);
		productBean2.setImageLink(
				"https://m.media-amazon.com/images/S/aplus-seller-content-images-us-east-1/ATVPDKIKX0DER/A1FYYQ65E5PX24/323102fa-8523-4996-83c4-698b4764e4a9._CR0,0,150,300_PT0_SX150__.jpg");

		session.save(productBean1);
		session.save(productBean2);

		session.beginTransaction().commit();
		session.close();
	}

//	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void serialNumber() {
		Session session = sessionFactory.openSession();

		Query query1 = session.createQuery("From ProductBean where id= :productId");
		query1.setParameter("productId", 3L);
		List<ProductBean> productBeans1 = query1.getResultList();

		ProductBean productBean1 = productBeans1.get(0);

		SerialNumberBean serialNumber = null;
		for (int count = 1; count <= productBean1.getStock(); count++) {
			serialNumber = new SerialNumberBean();
			serialNumber.setSerialNumber("A" + count);
			serialNumber.setProductBean(productBean1);
			serialNumber.setAvailabilityStatus("available");
			session.save(serialNumber);
		}

		Query query2 = session.createQuery("From ProductBean where id= :productId");
		query2.setParameter("productId", 4L);
		List<ProductBean> productBeans2 = query2.getResultList();

		ProductBean productBean2 = productBeans2.get(0);

		for (int count = 1; count <= productBean2.getStock(); count++) {
			serialNumber = new SerialNumberBean();
			serialNumber.setSerialNumber("B" + count);
			serialNumber.setProductBean(productBean2);
			serialNumber.setAvailabilityStatus("available");
			session.save(serialNumber);
		}

		session.beginTransaction().commit();
		session.close();
	}

//	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void order() {
		Session session = sessionFactory.openSession();

		int buyCount = 2;
		Query ProductBean = session.createQuery("From ProductBean where id= :productId");
		ProductBean.setParameter("productId", 4L);
		List<ProductBean> productBeans = ProductBean.getResultList();
		ProductBean productBean2 = productBeans.get(0);

		Query MemberBeanTest = session.createQuery("From MemberBeanTest where account= :Account");
		MemberBeanTest.setParameter("Account", "alex123");
		List<MemberBeanTest> memberBeanTests = MemberBeanTest.getResultList();
		MemberBeanTest memberBeanTest2 = memberBeanTests.get(0);

		Query SerialNumberBean = session
				.createQuery("From SerialNumberBean where availabilityStatus= :AvailabilityStatus");
		SerialNumberBean.setParameter("AvailabilityStatus", "available");
		List<SerialNumberBean> serialNumber = SerialNumberBean.getResultList();

		OrderBean orderBean = new OrderBean();
		orderBean.setMemberBeanTest(memberBeanTest2);
		orderBean.setCreateTime();
		orderBean.setUpdatedTime();
		orderBean.setPayStatus("未付款");
		orderBean.setDeliverStatus("訂單成立");
		orderBean.setDeliverPrice(200);
		orderBean.setDeliverType("超商[7-11]");
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("收件人", "Alex");
		jsonObject2.addProperty("地址", "台北市大安區");

		@SuppressWarnings("serial")
		HashMap<String, String> jsonMap = new Gson().fromJson(jsonObject2.toString(),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());

		orderBean.setReceiverInformation(jsonMap);
		orderBean.setProductTotalPrice(productBean2.getPrice() * buyCount);

		for (int count = 1; count <= buyCount; count++) {
			OrderDetailBean orderDetailBean1 = new OrderDetailBean();
			orderDetailBean1.setPrice(productBean2.getPrice());
			orderDetailBean1.setProductBean(productBean2);
			orderDetailBean1.setSerialNumber(serialNumber.get(count).getSerialNumber());
			serialNumber.get(count).setAvailabilityStatus("sold");
			orderDetailBean1.setOrderBean(orderBean);
			session.save(orderDetailBean1);
		}

		productBean2.setStock(productBean2.getStock() - buyCount);

		session.beginTransaction().commit();
		session.close();
	}

//	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insertReview() {

		Session session = sessionFactory.openSession();

		Query MemberBeanTest = session.createQuery("From MemberBeanTest where account= :Account");
		MemberBeanTest.setParameter("Account", "alex123");
		List<MemberBeanTest> memberBeanTests = MemberBeanTest.getResultList();
		MemberBeanTest memberBeanTest2 = memberBeanTests.get(0);

		Query ProductBean = session.createQuery("From ProductBean where id= :productId");
		ProductBean.setParameter("productId", 4L);
		List<ProductBean> productBeans = ProductBean.getResultList();
		ProductBean productBean2 = productBeans.get(0);

		ReviewBean reviewBean = new ReviewBean();
		reviewBean.setCreateTime();
		reviewBean.setUpdatedTime();
		reviewBean.setRating(9d);
		reviewBean.setComment("很不錯喔! 好用。");
		reviewBean.setMemberBeanTest(memberBeanTest2);
		reviewBean.setProductBean(productBean2);

		session.save(reviewBean);

		session.beginTransaction().commit();
		session.close();
	}

}
