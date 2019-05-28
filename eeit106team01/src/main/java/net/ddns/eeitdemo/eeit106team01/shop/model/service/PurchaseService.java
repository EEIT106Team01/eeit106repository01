package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	public PurchaseBean newPurchase(ArrayList<Long> productIdList, PurchaseBean purchaseBean) {
		if (productIdList != null && productIdList.size() > 0L && purchaseBean.isNotNull()) {

			// insert purchase
			PurchaseBean purchase = purchaseDAO.insertPurchase(purchaseBean);

			// Get products
			ArrayList<ProductBean> productBeans = new ArrayList<ProductBean>();
			for (Long productId : productIdList) {
				productBeans.add(productDAO.findProductByProductId(productId));
			}

			for (ProductBean productBean : productBeans) {
				// insert purchase lists
				SerialNumberBean serialNumberBean = productDAO
						.findSerialNumbersAreAvailableByProductId(productBean.getId()).get(0);
				String serialNumber = serialNumberBean.getSerialNumber();
				PurchaseListBean purchaseListBean = new PurchaseListBean(productBean.getPrice(), serialNumber, purchase,
						productBean);
				purchaseDAO.insertPurchaseList(purchaseListBean);
				// get available SN, change SN status
				serialNumberBean.setAvailabilityStatus("sold");
				productDAO.updateAvailabilityStatus(serialNumberBean);
				// change stock, total sold
				productBean.setStock(productBean.getStock() - 1);
				productBean.setTotalSold(productBean.getTotalSold() + 1);
				productDAO.updateProduct(productBean);
			}

			return purchase;
		}
		return null;
	}

	// Update a Purchase
	public PurchaseBean updatePurchase(PurchaseBean purchaseBean, String payStatus, String deliverStatus) {
		if (purchaseBean.isNotNull() && NullChecker.isEmpty(payStatus) == false
				&& NullChecker.isEmpty(deliverStatus) == false) {
			// UpdateTime
			// PayStatus, DeliverStatus
			purchaseBean.setPayStatus(payStatus);
			purchaseBean.setDeliverStatus(deliverStatus);
			return purchaseDAO.updatePurchase(purchaseBean);
		}
		return null;
	}

	// find Purchase
	public List<PurchaseBean> findPurchaseById(Long id, String idType) {
		if (id != null && id.longValue() > 0L && NullChecker.isEmpty(idType) == false) {
			List<PurchaseBean> result = new ArrayList<PurchaseBean>();
			if (idType.equalsIgnoreCase("purchase")) {
				result.add(purchaseDAO.findPurchaseByPurchaseId(id));
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (idType.equalsIgnoreCase("member")) {
				result = purchaseDAO.findPurchaseByMemberId(id);
				if (result != null && result.size() > 0) {
					return result;
				} else {
					throw new IllegalArgumentException("idType must be purchaseList, purchase, product");
				}
			}
		}
		return null;
	}

	public List<PurchaseBean> findPurchaseByType(String type, Date startDay, Date endDay, String stringValue,
			Integer intValue) {
		if (NullChecker.isEmpty(type) == false) {
			List<PurchaseBean> result = new ArrayList<PurchaseBean>();
			if (type.equalsIgnoreCase("time") && startDay != null && endDay != null && startDay.equals(endDay) == false
					&& startDay.compareTo(endDay) < 0) {
				result = purchaseDAO.findPurchaseByTimeDayBetween(startDay, endDay);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("deliverStatus") && NullChecker.isEmpty(stringValue) == false) {
				result = purchaseDAO.findPurchaseByDeliverStatus(stringValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("deliverType") && NullChecker.isEmpty(stringValue) == false) {
				result = purchaseDAO.findPurchaseByDeliverType(stringValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("payStatus") && NullChecker.isEmpty(stringValue) == false) {
				result = purchaseDAO.findPurchaseByPayStatus(stringValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("price") && intValue != null && intValue.intValue() >= 0) {
				result = purchaseDAO.findPurchaseByProductTotalPrice(intValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("priceLower") && intValue != null && intValue.intValue() >= 0) {
				result = purchaseDAO.findPurchaseByProductTotalPriceLower(intValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("priceHigher") && intValue != null && intValue.intValue() >= 0) {
				result = purchaseDAO.findPurchaseByProductTotalPriceHigher(intValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException(
						"type must be deliverStatus, deliverType, payStatus, price, priceLower, priceHigher");
			}

		}
		return null;
	}

	// find Purchase List
	public List<PurchaseListBean> findPurchaseListById(Long id, String idType) {
		if (id != null && id.longValue() > 0L && NullChecker.isEmpty(idType) == false) {
			List<PurchaseListBean> result = new ArrayList<PurchaseListBean>();
			if (idType.equalsIgnoreCase("purchaseList")) {
				result.add(purchaseDAO.findPurchaseListByPurchaseListId(id));
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (idType.equalsIgnoreCase("purchase")) {
				result = purchaseDAO.findPurchaseListByPurchaseId(id);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (idType.equalsIgnoreCase("product")) {
				result = purchaseDAO.findPurchaseListByProductId(id);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException("idType must be purchaseList, purchase, product");
			}
		}
		return null;
	}

	public List<PurchaseListBean> findPurchaseListByType(String type, String stringValue, Integer intValue) {
		if (NullChecker.isEmpty(type) == false) {
			List<PurchaseListBean> result = new ArrayList<PurchaseListBean>();
			if (type.equalsIgnoreCase("serialNumber") && NullChecker.isEmpty(stringValue) == false) {
				result.add(purchaseDAO.findPurchaseListBySerialNumber(stringValue));
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("price") & intValue != null && intValue.intValue() >= 0) {
				result = purchaseDAO.findPurchaseListByPrice(intValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("priceLower") & intValue != null && intValue.intValue() >= 0) {
				result = purchaseDAO.findPurchaseListByPriceLower(intValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("priceHigher") & intValue != null && intValue.intValue() >= 0) {
				result = purchaseDAO.findPurchaseListByPriceHigher(intValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException("type must be serialNumber, price, priceLower, priceHigher");
			}
		}
		return null;
	}

	// Create Review
	public List<ReviewBean> newReviews(List<ReviewBean> reviews) {
		if (reviews != null && reviews.size() > 0) {
			List<ReviewBean> result = new ArrayList<ReviewBean>();
			for (ReviewBean reviewBean : reviews) {
				result.add(purchaseDAO.insertReview(reviewBean));
			}
			return result;
		}
		return null;
	}
	
	// Update Review
	public ReviewBean updateReview(ReviewBean reviewBean, String rating, String comment, Blob image) {
		
		return null;
	}
	

}
