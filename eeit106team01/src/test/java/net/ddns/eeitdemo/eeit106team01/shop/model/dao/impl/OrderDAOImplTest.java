package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;

import net.ddns.eeitdemo.eeit106team01.shop.ShopTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;

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
		orderBean.setReceiverInformation(jsonObject.toString());
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
		orderBean.setReceiverInformation(jsonObject.toString());
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

	@Test
	public void testInsertOrderDetail() throws Exception {
		OrderDetailBean orderDetailBean = new OrderDetailBean();
		orderDetailBean.setPrice(2000);
		orderDetailBean.setSerialnumber("A2");
		orderDAO.insertOrderDetail(orderDetailBean);
		Integer actual = 2000;
		assertEquals(orderDAO.findOrderDetailByPrimaryKey(1L).getPrice(), actual);
		System.out.println(orderDAO.findOrderDetailByPrimaryKey(1L).toString());
	}

//	@Test
	public void testUpdateOrderDetail() throws Exception {
	}

//	@Test
	public void testFindOrderDetailByPrimaryKey() throws Exception {
	}

//	@Test
	public void testFindOrderDetails() throws Exception {
	}

}
