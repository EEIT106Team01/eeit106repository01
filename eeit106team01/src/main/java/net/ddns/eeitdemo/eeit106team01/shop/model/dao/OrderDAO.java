package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;

public interface OrderDAO {

	// Order
	abstract OrderBean insertOrder(OrderBean orderBean);

	abstract OrderBean updateOrder(OrderBean orderBean);

	abstract OrderBean findOrderByPrimaryKey(Long id);
	
	abstract List<OrderBean> findOrderByMemberId(Long id);

	abstract List<OrderBean> findOrders();

	// Order Detail
	abstract OrderDetailBean insertOrderDetail(OrderDetailBean orderDetailBeans);

	abstract OrderDetailBean updateOrderDetail(OrderDetailBean orderDetailBean);

	abstract OrderDetailBean findOrderDetailByPrimaryKey(Long id);
	
	abstract List<OrderDetailBean> findOrderDetailsByOrderId(Long id);

	abstract List<OrderDetailBean> findOrderDetails();

	// Review
	abstract ReviewBean insertReview(ReviewBean reviewBean);

	abstract ReviewBean updateReview(ReviewBean reviewBean);

	abstract ReviewBean findReviewByPrimaryKey(Long id);

	abstract List<ReviewBean> findReviews();

}
