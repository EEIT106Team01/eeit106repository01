package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.MemberBeanTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;

@Service
@Transactional
public class OrderService {

	@Autowired
//	private OrderDAO orderDAO;

	// Create a Order, Order Details
	public OrderBean createOrder(ArrayList<ProductBean> products, Long memberId) {
		if (products != null && memberId != null) {
			MemberBeanTest member = new MemberBeanTest();
			
			
			
			
//			orderDAO.insertOrder(order);
//			orderDAO.insertOrderDetail();
		}

		return null;
	}

	// Update a Order, Order Details

	// Query a Order

	// Query Orders, Order Details

}
