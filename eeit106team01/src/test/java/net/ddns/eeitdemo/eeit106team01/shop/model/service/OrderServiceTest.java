package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.ddns.eeitdemo.eeit106team01.shop.ShopTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest extends ShopTest {

	@Autowired
	OrderService orderService;

//	@Test
	public void testCreateOrder() {
		ArrayList<Long> productIds = new ArrayList<Long>();
		productIds.add(7L);
		productIds.add(8L);
		productIds.add(8L);

		OrderBean order = new OrderBean();
		order.setDeliverPrice(90);
		order.setDeliverType("7-77");
		order.setPayStatus("未付款");

		JsonObject json = new JsonObject();
		json.addProperty("收件人", "Wang XX");
		json.addProperty("地址", "台北市大安區");
		HashMap<String, String> map = new Gson().fromJson(json.toString(), new TypeToken<HashMap<String, String>>() {
			private static final long serialVersionUID = 2540483419609797968L;
		}.getType());
		order.setReceiverInformation(map);

		orderService.createOrder(productIds, 5L, order);
	}

//	@Test
	public void testFindOrdersByMemberId() throws Exception {
		System.out.println(orderService.findOrdersByMemberId(2L).toString());
	}

//	@Test
	public void testCreateReview() throws Exception {
		List<ReviewBean>reviews = new ArrayList<ReviewBean>();
		
		ReviewBean review1 = new ReviewBean();
		review1.setMemberBeanTest(null);
		review1.setProductBean(null);
		review1.setComment("1st review");
		review1.setRating(2.0);
		
		ReviewBean review2 = new ReviewBean();
		review2.setMemberBeanTest(null);
		review2.setProductBean(null);
		review2.setComment("2nd review");
		review2.setRating(5.0);
		
		reviews.add(review1);
		reviews.add(review2);
		
		System.out.println(orderService.createReview(reviews).size());
	}

	@Test
	public void testUpdateOrderStatus() throws Exception {
		orderService.updateOrderStatus(orderService.findOrdersByMemberId(2L).get(0), "recieved", "paid");
	}

}
