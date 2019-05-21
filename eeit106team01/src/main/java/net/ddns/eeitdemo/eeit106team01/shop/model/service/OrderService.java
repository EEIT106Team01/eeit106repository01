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
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
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
			HashMap<Integer, ProductBean> products = new HashMap<Integer, ProductBean>();

			Iterator<Long> ids = productIds.iterator();
			Integer count = 0;
			Integer productTotalPrice = 0;
			while (ids.hasNext()) {
				count++;
				Long id = (Long) ids.next();
				ProductBean product = productDAO.findProductByPrimaryKey(id);
				products.put(count, product);
				productTotalPrice += product.getPrice();
			}

			// Order set default value
			order.setCreateTime();
			order.setUpdatedTime();
			order.setDeliverStatus("order created");
			order.setMemberBeanTest(memberTestDAO.findByPrimaryKey(memberId));
			order.setProductTotalPrice(order.getDeliverPrice() + productTotalPrice);

			// Order Detail
			ArrayList<OrderDetailBean> orderDetails = new ArrayList<OrderDetailBean>();

			Iterator<Entry<Integer, ProductBean>> product = products.entrySet().iterator();
			while (product.hasNext()) {
				Map.Entry<Integer, ProductBean> entry = (Map.Entry<Integer, ProductBean>) product.next();
				ProductBean productBean = entry.getValue();

				OrderDetailBean orderDetail = new OrderDetailBean();
				orderDetail.setOrderBean(order);
				orderDetail.setProductBean(productBean);
				orderDetail.setPrice(productBean.getPrice());

				SerialNumberBean serialNumber = productDAO.findavailableProduct(productBean.getId()).get(0);
				orderDetail.setSerialNumber(serialNumber.getSerialNumber());
				serialNumber.setAvailabilityStatus("sold");
				productDAO.updateSNStatus(serialNumber);

				OrderDetailBean insertResult = orderDAO.insertOrderDetail(orderDetail);
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
