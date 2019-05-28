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

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.PurchaseDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

@Service
@Transactional
public class PurchaseService {

	@Autowired
	private MemberDAO memberTestDAO;

	@Autowired
	private PurchaseDAO orderDAO;

	@Autowired
	private ProductDAO productDAO;

	// Create a Order, Order Details
	public PurchaseBean createOrder(ArrayList<Long> productIds, Long memberId, PurchaseBean order) {

		if (productIds != null && memberId != null) {

			// find products which have been purchase
			HashMap<Integer, ProductBean> products = new HashMap<Integer, ProductBean>();

			Iterator<Long> ids = productIds.iterator();
			Integer count = 0;
			Integer productTotalPrice = 0;
			while (ids.hasNext()) {
				count++;
				Long id = (Long) ids.next();
				ProductBean product = productDAO.findProductByProductId(id);
				products.put(count, product);
				productTotalPrice += product.getPrice();
			}

			// Order set default value
			Date date = new Date(System.currentTimeMillis());
			order.setCreateTime(date);
			order.setUpdatedTime(date);
			order.setDeliverStatus("order created");
			order.setMemberId(memberTestDAO.findByMemberId(memberId));
			order.setProductTotalPrice(productTotalPrice);

			// Order Detail
			ArrayList<PurchaseListBean> orderDetails = new ArrayList<PurchaseListBean>();

			Iterator<Entry<Integer, ProductBean>> product = products.entrySet().iterator();
			while (product.hasNext()) {
				Map.Entry<Integer, ProductBean> entry = (Map.Entry<Integer, ProductBean>) product.next();
				ProductBean productBean = entry.getValue();

				PurchaseListBean orderDetail = new PurchaseListBean();
				orderDetail.setPurchaseId(order);
				orderDetail.setProductId(productBean);
				orderDetail.setPrice(productBean.getPrice());

				SerialNumberBean serialNumber = productDAO.findSerialNumbersAreAvailableByProductId(productBean.getId()).get(0);
				orderDetail.setSerialNumber(serialNumber.getSerialNumber());
				serialNumber.setAvailabilityStatus("sold");
				productDAO.updateAvailabilityStatus(serialNumber);

				productBean.setStock(productBean.getStock() - 1);
				productBean.setTotalSold(productBean.getTotalSold() + 1);

				PurchaseListBean insertResult = orderDAO.insertPurchaseList(orderDetail);
				orderDetails.add(orderDAO.findPurchaseListByPurchaseListId(insertResult.getId()));
			}

			// Create Order
			return orderDAO.insertPurchase(order);
		}
		return null;
	}

	// Update a Order Status
	public PurchaseBean updateOrderStatus(PurchaseBean order, String deliverStatus, String payStatus) {
		if (order != null && deliverStatus != null && payStatus != null) {
			Date date = new Date(System.currentTimeMillis());
			order.setUpdatedTime(date);
			order.setDeliverStatus(deliverStatus);
			order.setPayStatus(payStatus);
			PurchaseBean result = orderDAO.updatePurchase(order);
			return result;
		}
		return null;
	}

	// Find Orders by Member id, by Order id.
	/**
	 * @param id
	 * @param searchType
	 * @return if searchType = 1 will search by MemberId, else if searchType = 2
	 *         will search by OrderId
	 */
	public List<PurchaseBean> findOrdersByMemberId(Long id, Integer searchType) {
		if (id != null && searchType != null) {
			if (searchType == 1) {
				List<PurchaseBean> result = orderDAO.findPurchaseByMemberId(id);
				if (result != null) {
					return result;
				}
			} else if (searchType == 2) {
				List<PurchaseBean> result = new ArrayList<PurchaseBean>();
				PurchaseBean order = orderDAO.findPurchaseByPurchaseId(id);
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
				ReviewBean result = orderDAO.insertReview(review);
				results.add(result);
			}
			return results;
		}
		return null;
	}

}
