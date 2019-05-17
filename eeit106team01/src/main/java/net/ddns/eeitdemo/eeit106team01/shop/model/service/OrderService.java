package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberTestDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

@Service
@Transactional
public class OrderService {

	@Autowired
	private MemberTestDAO memberTestDAO;

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private ProductDAO productDAO;

	// Create a Order, Order Details
	public OrderBean createOrder(ArrayList<Long> productIds, Long memberId, OrderBean order) {
		if (productIds != null && memberId != null) {
			// List for productBeans, get all product details which include in this purchase
			ArrayList<ProductBean> products = new ArrayList<ProductBean>();
			Iterator<Long> iterator = productIds.iterator();
			while (iterator.hasNext()) {
				products.add(productDAO.findProductByPrimaryKey((Long) iterator.next()));
			}
			// Order
			order.setCreateTime();
			order.setUpdatedTime();
			order.setMemberBeanTest(memberTestDAO.findByPrimaryKey(memberId));
			// Order Details
			OrderDetailBean orderDetail = new OrderDetailBean();
			orderDetail.setOrderBean(order);
			orderDetail.setSerialNumber("");
			orderDetail.setPrice(1);
			orderDAO.insertOrderDetail(orderDetail);
			return orderDAO.insertOrder(order);
		}
		return null;
	}

	// Update a Order, Order Details

	// Query a Order

	// Query Orders, Order Details

	// Create Review

}
