package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDAOImplTest {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private MemberDAO memberDAO;

	@Test
	public void testInsertOrder() {
		// Member
//		MemberBeanShop memberBean = new MemberBeanShop();
//		memberBean.setAccount("");
//		memberDAO.insert(memberBean);

		// Product
//		ProductBean productBean = new ProductBean();
//		productBean.setBrand("");
//		productBean.setCreateTime();
//		HashMap<Integer, String> imageLink = new HashMap<Integer, String>();
//		imageLink.put(1, "");
//		productBean.setImageLink(imageLink);
//		HashMap<String, String> information = new HashMap<String, String>();
//		information.put("", "");
//		productBean.setInformation(information);
//		HashMap<Integer, String> informationImageLink = new HashMap<Integer, String>();
//		informationImageLink.put(1, "");
//		productBean.setInformationImageLink(informationImageLink);
//		productBean.setName("");
//		productBean.setPrice(0);
//		productBean.setStock(0);
//		productBean.setTotalSold(0);
//		productBean.setType("");
//		productBean.setUpdatedTime();
//		productDAO.insertProduct(productBean);

		// OrderDetail
//		List<OrderDetailBean> list = new ArrayList<OrderDetailBean>();
//		OrderDetailBean orderDetailBean = new OrderDetailBean();
//		orderDetailBean.setPrice(0);
//		orderDetailBean.setProductBean(productDAO.findProductByPrimaryKey(1L));
//		orderDetailBean.setSerialNumber("");
//		list.add(orderDAO.findOrderDetailByOrderDetailId(1L));

		// Test Equals
//		OrderBean orderBean1 = new OrderBean();
//		orderBean1.setCreateTime();
//		orderBean1.setUpdatedTime();
//		orderBean1.setDeliverPrice(0);
//		orderBean1.setDeliverStatus("dStatus1");
//		orderBean1.setDeliverType("type1");
//		orderBean1.setPayStatus("pStatus1");
//		orderBean1.setProductTotalPrice(0);
//		orderBean1.setMemberBeanTest(memberDAO.findByMemberID(1L));
//		HashMap<String, String> hashMap = new HashMap<String, String>();
//		hashMap.put("map1", "map1");
//		orderBean1.setReceiverInformation(hashMap);
//		OrderBean test1 = orderDAO.insertOrder(orderBean1);
//		String actual = "dStatus1";
//		assertEquals(test1.getDeliverStatus(), actual);
		
		System.out.println(orderDAO.findOrderByOrderId(1L).toString());
		
//		// Test Null
//		OrderBean orderBean3 = new OrderBean();
//		orderBean3.setCreateTime();
//		OrderBean test3 = orderDAO.insertOrder(orderBean2);
//		assertNull(test3);
	}

//	@Test
	public void testUpdateOrder() {
	}

//	@Test
	public void testFindOrderByOrderId() {
	}

}
