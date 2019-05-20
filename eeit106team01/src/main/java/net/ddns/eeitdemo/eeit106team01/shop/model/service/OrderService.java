package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
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
			// Order
			order.setCreateTime();
			order.setUpdatedTime();
			order.setMemberBeanTest(memberTestDAO.findByPrimaryKey(memberId));

			// List for SerialNumberBeans, get all available SerialNumbers which need for
			// this purchase
			List<SerialNumberBean> serialNumbers = new ArrayList<SerialNumberBean>();
			List<OrderDetailBean> orderDetailBeans = new ArrayList<OrderDetailBean>();

			Iterator<Long> iteratorLong = productIds.iterator();
			while (iteratorLong.hasNext()) {
				serialNumbers = (productDAO.findavailableProduct((Long) iteratorLong.next()));
			}

			Iterator<SerialNumberBean> iteratorSNB = serialNumbers.iterator();
			while (iteratorSNB.hasNext()) {
				SerialNumberBean serialNumberBean = iteratorSNB.next();
				// Order Details
				OrderDetailBean orderDetail = new OrderDetailBean();
				orderDetail.setOrderBean(order);
				orderDetail.setSerialNumber(serialNumberBean.getSerialNumber());
				serialNumberBean.setAvailabilityStatus("sold");
				productDAO.updateSNStatus(serialNumberBean);
				orderDetail.setPrice(serialNumberBean.getProductBean().getPrice());
				OrderDetailBean orderDetailBean = orderDAO.insertOrderDetail(orderDetail);
				orderDetailBeans.add(orderDetailBean);
			}
			order.setOrderDetailBeans(orderDetailBeans);

			return orderDAO.insertOrder(order);
		}
		return null;
	}

	// Update a Order, Order Details

	// Query a Order

	// Query Orders, Order Details

	// Create Review

}
