package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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

			// find products which have been purchase
			HashMap<Long, ProductBean> products = new HashMap<Long, ProductBean>();

			Iterator<Long> ids = productIds.iterator();
			while (ids.hasNext()) {
				Long id = (Long) ids.next();
				products.put(id, productDAO.findProductByPrimaryKey(id));
			}

			// Order set default value
			order.setCreateTime();
			order.setUpdatedTime();
			order.setDeliverStatus("order created");
			order.setMemberBeanTest(memberTestDAO.findByPrimaryKey(memberId));

			// Order Detail
			ArrayList<OrderDetailBean> orderDetails = new ArrayList<OrderDetailBean>();

			Iterator<Entry<Long, ProductBean>> product = products.entrySet().iterator();
			while (product.hasNext()) {
				Map.Entry<java.lang.Long, ProductBean> entry = (Map.Entry<java.lang.Long, ProductBean>) product.next();
				Long id = entry.getKey();
				ProductBean productBean = entry.getValue();

				OrderDetailBean orderDetail = new OrderDetailBean();
				orderDetail.setOrderBean(order);
				orderDetail.setProductBean(productBean);
				orderDetail.setPrice(productBean.getPrice());
				orderDetail.setSerialNumber(productDAO.findavailableProduct(id).get(0).getSerialNumber());
				OrderDetailBean insertResult = orderDAO.insertOrderDetail(orderDetail);
				
//				productDAO.updateSNStatus();
				orderDetails.add(orderDAO.findOrderDetailByPrimaryKey(insertResult.getId()));
			}

			// Create Order
			return orderDAO.insertOrder(order);
		}
		return null;
	}

	// Update a Order, Order Details

	// Query a Order

	// Query Orders, Order Details

	// Create Review

}
