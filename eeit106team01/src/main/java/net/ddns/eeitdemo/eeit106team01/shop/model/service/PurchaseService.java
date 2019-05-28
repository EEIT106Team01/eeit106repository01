package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.PurchaseDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@Service
@Transactional
public class PurchaseService {

	@Autowired
	private PurchaseDAO purchaseDAO;

	@Autowired
	private ProductDAO productDAO;

	// Create a Purchase and Purchase List
	public PurchaseBean newPurchase(ArrayList<Integer> productIdList, Long memberId, PurchaseBean purchaseBean) {
		if (productIdList != null && productIdList.size() > 0L && memberId != null && memberId.longValue() > 0L && purchaseBean.isNotNull()) {

			// insert purchase

			// insert purchase lists
			// get available SN, change SN status
			// change stock, total sold

		}
		return null;
	}

	// Update a Purchase
	public PurchaseBean updatePurchase(PurchaseBean purchaseBean, String deliverStatus, String payStatus) {
		if (purchaseBean != null && deliverStatus != null && payStatus != null) {
			Date date = new Date(System.currentTimeMillis());
			purchaseBean.setUpdatedTime(date);
			purchaseBean.setDeliverStatus(deliverStatus);
			purchaseBean.setPayStatus(payStatus);
			PurchaseBean result = purchaseDAO.updatePurchase(purchaseBean);
			return result;
		}
		return null;
	}

	/**
	 * @param id
	 * @param searchType
	 * @return if searchType = 1 will search by MemberId, else if searchType = 2
	 *         will search by OrderId
	 */
	public List<PurchaseBean> findOrdersByMemberId(Long id, Integer searchType) {
		if (id != null && searchType != null) {
			if (searchType == 1) {
				List<PurchaseBean> result = purchaseDAO.findPurchaseByMemberId(id);
				if (result != null) {
					return result;
				}
			} else if (searchType == 2) {
				List<PurchaseBean> result = new ArrayList<PurchaseBean>();
				PurchaseBean order = purchaseDAO.findPurchaseByPurchaseId(id);
				result.add(order);
				if (result != null) {
					return result;
				}
			} else {
				return null;
			}
		}
		return null;
	}

	// Create Review
	public List<ReviewBean> createReview(List<ReviewBean> reviews) {
		if (reviews != null) {
			List<ReviewBean> results = new ArrayList<ReviewBean>();
			Iterator<ReviewBean> iterator = reviews.iterator();
			while (iterator.hasNext()) {
				ReviewBean review = (ReviewBean) iterator.next();
				Date date = new Date(System.currentTimeMillis());
				review.setCreateTime(date);
				review.setUpdatedTime(date);
				ReviewBean result = purchaseDAO.insertReview(review);
				results.add(result);
			}
			return results;
		}
		return null;
	}

}
