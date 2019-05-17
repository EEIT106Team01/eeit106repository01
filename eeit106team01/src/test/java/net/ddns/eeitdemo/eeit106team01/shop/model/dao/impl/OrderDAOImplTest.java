package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.ddns.eeitdemo.eeit106team01.shop.ShopTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDAOImplTest extends ShopTest {

	@Autowired
	private OrderDAO orderDAO;

//	@Test
	public void testInsertOrderOrderBean() {

		OrderBean orderBean = new OrderBean();
		orderBean.setCreateTime();
		orderBean.setUpdatedTime();
		orderBean.setPayStatus("未付款");
		orderBean.setDeliverStatus("訂單成立");
		orderBean.setDeliverPrice(200);
		orderBean.setDeliverType("超商[7-11]");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("收件人", "Alex");
		jsonObject.addProperty("地址", "台北市大安區");

		@SuppressWarnings("serial")
		HashMap<String, String> jsonMap = new Gson().fromJson(jsonObject.toString(),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());

		orderBean.setReceiverInformation(jsonMap);
		orderBean.setProductTotalPrice(2000);

		orderDAO.insertOrder(orderBean);
	}

//	@Test
	public void testUpdateOrderOrderBean() {
		OrderBean orderBean = new OrderBean();
		orderBean.setId(1L);
		orderBean.setCreateTime();
		orderBean.setUpdatedTime();
		orderBean.setPayStatus("未付款");
		orderBean.setDeliverStatus("訂單成立");
		orderBean.setDeliverPrice(200);
		orderBean.setDeliverType("超商[7-11]");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("收件人", "Alex");
		jsonObject.addProperty("地址", "台北市大安區");

		@SuppressWarnings("serial")
		HashMap<String, String> jsonMap = new Gson().fromJson(jsonObject.toString(),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());

		orderBean.setReceiverInformation(jsonMap);
		orderBean.setProductTotalPrice(2001);

		orderDAO.updateOrder(orderBean);

		Integer actual = 2001;
		assertEquals(orderDAO.findOrderByPrimaryKey(1L).getProductTotalPrice(), actual);
	}

//	@Test
	public void testFindOrderByPrimaryKey() {
		Integer actual = 200;
		assertEquals(orderDAO.findOrderByPrimaryKey(1L).getDeliverPrice(), actual);
	}

//	@Test
	public void testFindOrders() {
		System.out.println(orderDAO.findOrders().toString());
		assertNotNull(orderDAO.findOrders());
	}

//	@Test
	public void testInsertOrderDetail() throws Exception {
		OrderDetailBean orderDetailBean = new OrderDetailBean();
		orderDetailBean.setPrice(2000);
		orderDetailBean.setSerialNumber("A2");
		orderDetailBean.setOrderBean(orderDAO.findOrderByPrimaryKey(1L));

		OrderDetailBean orderDetailBean1 = new OrderDetailBean();
		orderDetailBean1.setPrice(3000);
		orderDetailBean1.setSerialNumber("A5");
		orderDetailBean1.setOrderBean(orderDAO.findOrderByPrimaryKey(1L));

		ArrayList<OrderDetailBean> orderDetailBeans = new ArrayList<OrderDetailBean>();
		orderDetailBeans.add(orderDetailBean);
		orderDetailBeans.add(orderDetailBean1);

		Iterator<OrderDetailBean> iterator = orderDetailBeans.iterator();
		while (iterator.hasNext()) {
			orderDAO.insertOrderDetail((OrderDetailBean) iterator.next());
		}

		Long actual = 1L;
		assertEquals(orderDAO.findOrderDetailByPrimaryKey(2L).getOrderBean().getId(), actual);
		System.out.println(orderDAO.findOrderDetails().toString());
	}

//	@Test
	public void testUpdateOrderDetail() throws Exception {
		OrderDetailBean orderDetailBean = orderDAO.findOrderDetailByPrimaryKey(1L);
		orderDetailBean.setPrice(3000);
		orderDAO.updateOrderDetail(orderDetailBean);

		Integer actual = 3000;
		assertEquals(orderDAO.findOrderDetailByPrimaryKey(1L).getPrice(), actual);

	}

//	@Test
	public void testFindOrderDetailByPrimaryKey() throws Exception {
		assertNotNull(orderDAO.findOrderDetailByPrimaryKey(1L));
	}

//	@Test
	public void testFindOrderDetails() throws Exception {
		assertNotNull(orderDAO.findOrderDetails());
	}

//	@Test
	public void testInsertReview() throws Exception {
		ReviewBean reviewBean = new ReviewBean();
		reviewBean.setCreateTime();
		reviewBean.setUpdatedTime();
		reviewBean.setRating(9d);
		reviewBean.setComment("很不錯喔! 好用。");
		orderDAO.insertReview(reviewBean);

		Double actual = 9d;
		assertEquals(orderDAO.findReviewByPrimaryKey(1L).getRating(), actual);
	}

//	@Test
	public void testUpdateReview() throws Exception {
		ReviewBean reviewBean = orderDAO.findReviewByPrimaryKey(1L);
		reviewBean.setRating(10d);
		orderDAO.updateReview(reviewBean);

		Double actual = 10d;
		assertEquals(orderDAO.findReviewByPrimaryKey(1L).getRating(), actual);
	}

//	@Test
	public void testFindReviewByPrimaryKey() throws Exception {
		assertNotNull(orderDAO.findReviewByPrimaryKey(2L));
		System.out.println(orderDAO.findReviewByPrimaryKey(2L).toString());
	}

//	@Test
	public void testFindReviews() throws Exception {
		assertNotNull(orderDAO.findReviews());
		System.out.println(orderDAO.findReviews());
	}

}
