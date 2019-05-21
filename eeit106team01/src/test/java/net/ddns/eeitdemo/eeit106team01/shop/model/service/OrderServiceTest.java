package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	@Autowired
	OrderService orderService;

	@Test
	public void testCreateOrder() {
		ArrayList<Long> productIds = new ArrayList<Long>();
		productIds.add(3L);
		productIds.add(4L);
		productIds.add(4L);

		Iterator<Long> iterator = productIds.iterator();
		while (iterator.hasNext()) {
			Long long1 = (Long) iterator.next();
			System.out.println(long1);
		}

//		OrderBean order = new OrderBean();
//		order.setDeliverPrice(90);
//		order.setDeliverType("7-77");
//		order.setPayStatus("未付款");
//		order.setProductTotalPrice(100 + 100 + 50 + order.getDeliverPrice());
//
//		JsonObject json = new JsonObject();
//		json.addProperty("收件人", "Wang XX");
//		json.addProperty("地址", "台北市大安區");
//		HashMap<String, String> map = new Gson().fromJson(json.toString(), new TypeToken<HashMap<String, String>>() {
//			private static final long serialVersionUID = 2540483419609797968L;
//		}.getType());
//		order.setReceiverInformation(map);
//
//		orderService.createOrder(productIds, 3L, order);
	}

}
