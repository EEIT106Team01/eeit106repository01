package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.Date;
import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;

public interface PurchaseDAO {

	// Purchase
	abstract PurchaseBean insertPurchase(PurchaseBean purchaseBean);

	abstract PurchaseBean updatePurchase(PurchaseBean purchaseBean);

	abstract PurchaseBean findPurchaseByPurchaseId(Long purchaseId);

	abstract List<PurchaseBean> findPurchaseByTimeDayBetween(Date startDay, Date endDay);

	abstract List<PurchaseBean> findPurchaseByDeliverStatus(String deliverStatus);

	abstract List<PurchaseBean> findPurchaseByDeliverType(String deliverType);

	abstract List<PurchaseBean> findPurchaseByPayStatus(String payStatus);

	abstract List<PurchaseBean> findPurchaseByProductTotalPrice(Integer productTotalPrice);

	abstract List<PurchaseBean> findPurchaseByProductTotalPriceLower(Integer productTotalPrice);

	abstract List<PurchaseBean> findPurchaseByProductTotalPriceHigher(Integer productTotalPrice);

	abstract List<PurchaseBean> findPurchaseByMemberId(Long memberId);

	abstract List<PurchaseBean> findAllPurchase();
	
	abstract Integer findPurchaseIdCount();

	// Purchase List
	abstract PurchaseListBean insertPurchaseList(PurchaseListBean purchaseListBean);

	abstract PurchaseListBean findPurchaseListByPurchaseListId(Long purchaseListId);

	abstract PurchaseListBean findPurchaseListBySerialNumber(String serialNumber);

	abstract List<PurchaseListBean> findPurchaseListByPrice(Integer price);

	abstract List<PurchaseListBean> findPurchaseListByPriceLower(Integer price);

	abstract List<PurchaseListBean> findPurchaseListByPriceHigher(Integer price);

	abstract List<PurchaseListBean> findPurchaseListByPurchaseId(Long purchaseId);

	abstract List<PurchaseListBean> findPurchaseListByProductId(Long productId);

	abstract List<PurchaseListBean> findAllPurchaseList();

	// Review
	abstract ReviewBean insertReview(ReviewBean reviewBean);

	abstract ReviewBean updateReview(ReviewBean reviewBean);

	abstract ReviewBean findReviewByReviewId(Long reviewId);

	abstract List<ReviewBean> findReviewsByTimeDayBetween(Date startDay, Date endDay);

	abstract List<ReviewBean> findReviewsByImageExistence(Boolean truefalse);

	abstract List<ReviewBean> findReviewsByRating(Double rating);

	abstract List<ReviewBean> findReviewsByRatingLower(Double rating);

	abstract List<ReviewBean> findReviewsByRatingHigher(Double rating);

	abstract List<ReviewBean> findReviewsByMemberId(Long memberId);

	abstract List<ReviewBean> findReviewsByProductId(Long productId);

	abstract List<ReviewBean> findReviews();

}
