package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;

public interface OrderDAO {

	// Order
	abstract PurchaseBean insertOrder(PurchaseBean orderBean);

	abstract PurchaseBean updateOrder(PurchaseBean orderBean);

	abstract PurchaseBean findOrderByOrderId(Long id);

	abstract List<PurchaseBean> findOrdersByCreateTime(Integer day);

	abstract List<PurchaseBean> findOrdersByUpdateTime(Integer day);

	abstract List<PurchaseBean> findOrdersByDeliverStatus(String deliverStatus);

	abstract List<PurchaseBean> findOrdersByDeliverType(String deliverType);

	abstract List<PurchaseBean> findOrdersByPayStatus(String payStatus);

	abstract PurchaseBean findOrdersByProductTotalPrice(Integer price);

	abstract List<PurchaseBean> findOrdersByProductTotalPriceLower(Integer price);

	abstract List<PurchaseBean> findOrdersByProductTotalPriceHigher(Integer price);

	abstract List<PurchaseBean> findOrdersByMemberId(Long id);

	abstract List<PurchaseBean> findOrders();

	// Order Detail
	abstract PurchaseListBean insertOrderDetail(PurchaseListBean orderDetailBeans);

	abstract PurchaseListBean updateOrderDetail(PurchaseListBean orderDetailBean);

	abstract PurchaseListBean findOrderDetailByOrderDetailId(Long id);

	abstract PurchaseListBean findOrderDetailBySerialNumber(String serialNumber);

	abstract PurchaseListBean findOrderDetailByPrice(Integer price);

	abstract List<PurchaseListBean> findOrderDetailsByPriceLower(Integer price);

	abstract List<PurchaseListBean> findOrderDetailsByPriceHigher(Integer price);

	abstract List<PurchaseListBean> findOrderDetailsByOrderId(Long id);

	abstract List<PurchaseListBean> findOrderDetailsByProductId(Long id);

	abstract List<PurchaseListBean> findOrderDetails();

	// Review
	abstract ReviewBean insertReview(ReviewBean reviewBean);

	abstract ReviewBean updateReview(ReviewBean reviewBean);

	abstract ReviewBean findReviewByReviewId(Long id);

	abstract List<ReviewBean> findReviewsByCreateTime(Integer day);

	abstract List<ReviewBean> findReviewsByUpdateTime(Integer day);

	abstract List<ReviewBean> findReviewsByImageExistence(Boolean truefalse);

	abstract List<ReviewBean> findReviewsByRating(Double rating);

	abstract List<ReviewBean> findReviewsByRatingLower(Double rating);

	abstract List<ReviewBean> findReviewsByRatingHigher(Double rating);

	abstract List<ReviewBean> findReviewsByMemberId(Long id);

	abstract List<ReviewBean> findReviewsByProductId(Long id);

	abstract List<ReviewBean> findReviews();

}
