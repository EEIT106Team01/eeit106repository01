package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.serial.SerialBlob;
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
	public PurchaseBean newPurchase(HashMap<Long, Long> productIdList, PurchaseBean purchaseBean) {
		if (productIdList != null && productIdList.size() > 0 && purchaseBean.isNotNull()) {

			// insert purchase
			PurchaseBean purchase = purchaseDAO.insertPurchase(purchaseBean);

			// Get products
			ArrayList<ProductBean> productBeans = new ArrayList<ProductBean>();
			Iterator<Entry<Long, Long>> iterator = productIdList.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Long, Long> entry = (Entry<Long, Long>) iterator.next();
				Long productId = entry.getValue();
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
	public PurchaseBean updatePurchase(PurchaseBean purchaseBean, String payStatus, String deliverStatus,
			Integer productTotalPrice) {
		if (purchaseBean.isNotNull()
				&& (NullChecker.isEmpty(payStatus) == false || NullChecker.isEmpty(deliverStatus) == false)
				|| (productTotalPrice != null && productTotalPrice.intValue() >= 0)) {
			// PayStatus, DeliverStatus, productTotalPrice
			Boolean flag = false;
			if (NullChecker.isEmpty(payStatus) == false) {
				if (!payStatus.equalsIgnoreCase(purchaseBean.getPayStatus())
						&& NullChecker.isEmpty(payStatus) == false) {
					purchaseBean.setPayStatus(payStatus);
					flag = true;
				}
			}
			if (NullChecker.isEmpty(deliverStatus) == false) {
				if (!deliverStatus.equalsIgnoreCase(purchaseBean.getDeliverStatus())
						&& NullChecker.isEmpty(deliverStatus) == false) {
					purchaseBean.setDeliverStatus(deliverStatus);
					flag = true;
				}
			}
			if (productTotalPrice != null && productTotalPrice.intValue() >= 0) {
				if (productTotalPrice != purchaseBean.getProductTotalPrice()) {
					purchaseBean.setProductTotalPrice(productTotalPrice);
					flag = true;
				}
			}
			if (flag) {
				return purchaseDAO.updatePurchase(purchaseBean);
			}
		}
		return null;
	}

	// find Purchase
	public List<PurchaseBean> findPurchaseById(Long id, String idType) {
		if (id != null && id.longValue() > 0L && NullChecker.isEmpty(idType) == false) {
			List<PurchaseBean> result = new ArrayList<PurchaseBean>();
			if (idType.equalsIgnoreCase("purchase")) {
				PurchaseBean purchaseBean = purchaseDAO.findPurchaseByPurchaseId(id);
				if (purchaseBean != null) {
					result.add(purchaseBean);
					if (result != null && result.size() > 0) {
						return result;
					}
				}
			} else if (idType.equalsIgnoreCase("member")) {
				result = purchaseDAO.findPurchaseByMemberId(id);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException("idType must be purchase, member");
			}
		}
		return null;
	}

	public List<PurchaseBean> findPurchaseByType(String type, Date startDay, Date endDay, String stringValue,
			Integer intValue) {
		if (NullChecker.isEmpty(type) == false) {
			List<PurchaseBean> result = new ArrayList<PurchaseBean>();
			if (type.equalsIgnoreCase("time") && startDay != null && endDay != null) {
				if (startDay.equals(endDay) == false && endDay.compareTo(startDay) > 0) {
					result = purchaseDAO.findPurchaseByTimeDayBetween(startDay, endDay);
					if (result != null && result.size() > 0) {
						return result;
					} else {
						return null;
					}
				}
				throw new IllegalArgumentException("startDay can't greater than endDay or same");
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
				PurchaseListBean purchaseListBean = purchaseDAO.findPurchaseListByPurchaseListId(id);
				if (purchaseListBean != null) {
					result.add(purchaseListBean);
					if (result != null && result.size() > 0) {
						return result;
					}
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
				PurchaseListBean purchaseListBean = purchaseDAO.findPurchaseListBySerialNumber(stringValue);
				if (purchaseListBean != null) {
					result.add(purchaseListBean);
					if (result != null && result.size() > 0) {
						return result;
					}
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
				if (reviewBean != null) {
					result.add(purchaseDAO.insertReview(reviewBean));
				}
			}
			if (result != null && result.size() > 0) {
				return result;
			}
		}
		return null;
	}

	// Update Review
	// @formatter:on
	public ReviewBean updateReview(ReviewBean reviewBean, Double rating, String comment, SerialBlob image) {
		if (reviewBean.isNotNull() && ((rating != null && rating.doubleValue() >= 0D)
				|| NullChecker.isEmpty(comment) == false || NullChecker.isEmpty(image) == false)) {
			Boolean flag = false;
			if ((rating != null && rating.doubleValue() >= 0D)) {
				if (rating != reviewBean.getRating()) {
					reviewBean.setRating(rating);
					flag = true;
				}
			}
			if (NullChecker.isEmpty(comment) == false) {
				if (!comment.equals(reviewBean.getComment())) {
					reviewBean.setComment(comment);
					flag = true;
				}
			}
			if (NullChecker.isEmpty(image) == false) {
				if (reviewBean.getImage() != null) {
					if (!image.equals(reviewBean.getImage())) {
						reviewBean.setImage(image);
						flag = true;
					}
				} else {
					reviewBean.setImage(image);
					flag = true;
				}
			}
			if (flag) {
				return purchaseDAO.updateReview(reviewBean);
			}
		}
		return null;
	}

	// Find Review
	public List<ReviewBean> findReviewById(String idType, Long id) {
		if (NullChecker.isEmpty(idType) == false && id != null && id.longValue() > 0L) {
			List<ReviewBean> result = new ArrayList<ReviewBean>();
			if (idType.equalsIgnoreCase("review")) {
				ReviewBean reviewBean = purchaseDAO.findReviewByReviewId(id);
				if (reviewBean != null) {
					result.add(reviewBean);
					if (result != null && result.size() > 0) {
						return result;
					}
				}
			} else if (idType.equalsIgnoreCase("member")) {
				result = purchaseDAO.findReviewsByMemberId(id);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (idType.equalsIgnoreCase("product")) {
				result = purchaseDAO.findReviewsByProductId(id);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException("idType must be review, member, product");
			}
		}
		return null;
	}

	public List<ReviewBean> findReviewByType(String type, Date startDay, Date endDay, String stringValue,
			Double doubleValue, Boolean truefalse) {
		if (NullChecker.isEmpty(type) == false) {
			List<ReviewBean> result = new ArrayList<ReviewBean>();
			if (type.equalsIgnoreCase("time") && startDay != null && endDay != null) {
				if (startDay.equals(endDay) == false && endDay.compareTo(startDay) > 0) {
					result = purchaseDAO.findReviewsByTimeDayBetween(startDay, endDay);
					if (result != null && result.size() > 0) {
						return result;
					} else {
						return null;
					}
				}
				throw new IllegalArgumentException("startDay can't greater than endDay or same");
			} else if (type.equalsIgnoreCase("image") && truefalse != null) {
				result = purchaseDAO.findReviewsByImageExistence(truefalse);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("rating") && doubleValue != null && doubleValue.doubleValue() <= 5D
					&& doubleValue.doubleValue() >= 0D) {
				result = purchaseDAO.findReviewsByRating(doubleValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("ratingLower") && doubleValue != null && doubleValue.doubleValue() <= 5D
					&& doubleValue.doubleValue() >= 0D) {
				result = purchaseDAO.findReviewsByRatingLower(doubleValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else if (type.equalsIgnoreCase("ratingHigher") && doubleValue != null && doubleValue.doubleValue() <= 5D
					&& doubleValue.doubleValue() >= 0D) {
				result = purchaseDAO.findReviewsByRatingHigher(doubleValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException("type must be time, image, rating, ratingLower, ratingHigher");
			}
		}
		return null;
	}

}
