package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.PurchaseDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@Service
@Transactional
public class RefundService {

	@Autowired
	private RefundDAO refundDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private PurchaseDAO purchaseDAO;

	// Create Refunds
	public RefundBean newRefund(List<PurchaseListBean> purchaseListBeans, RefundBean refund) {
		if (purchaseListBeans != null && !purchaseListBeans.isEmpty() && refund.isNotNull()) {
			// Create a refund
			RefundBean newRefund = refundDAO.insertRefund(refund);
			RefundListBean refundList;

			ArrayList<Integer> productsPrice = new ArrayList<>();
			PurchaseBean purchaseBean = purchaseListBeans.get(0).getPurchaseId();
			// Get PurchaseList want to be refund
			for (PurchaseListBean purchaseList : purchaseListBeans) {
				String serialNumber = purchaseList.getSerialNumber();
				refundList = new RefundListBean(purchaseList, newRefund);
				refundDAO.insertRefundList(refundList);

				// Change SN status
				SerialNumberBean serialNumberBean = productDAO.findSerialNumber(serialNumber);
				serialNumberBean.setAvailabilityStatus("refund");
				productDAO.updateAvailabilityStatus(serialNumberBean);
				// Get Refund products price
				Integer productPrice = purchaseList.getPrice();
				if (productPrice != null) {
					productsPrice.add(productPrice);
				}
			}

			// Change totalProductPrice
			Integer totalRefundPrice = 0;
			for (Integer price : productsPrice) {
				totalRefundPrice += price;
			}
			purchaseBean.setProductTotalPrice(purchaseBean.getProductTotalPrice() - totalRefundPrice);
			purchaseDAO.updatePurchase(purchaseBean);

			return newRefund;
		}
		return null;
	}

	// Update Refund
	public RefundBean updateRefundProcessStatus(RefundBean refund, String processStatus) {
		if (refund.isNotNull() && NullChecker.isEmpty(processStatus) == false) {
			Boolean flag = false;
			if (!processStatus.equalsIgnoreCase(refund.getProcessStatus())) {
				refund.setProcessStatus(processStatus);
				flag = true;
			}
			if (flag) {
				return refundDAO.updateRefund(refund);
			}
		}
		return null;
	}

	// Find Refund
	public List<RefundBean> findRefundsById(String idType, Long id) {
		if (NullChecker.isEmpty(idType) == false && id != null && id.longValue() > 0L) {
			List<RefundBean> result = new ArrayList<RefundBean>();
			if (idType.equalsIgnoreCase("refund")) {
				RefundBean refundBean = refundDAO.findRefundByRefundId(id);
				if (refundBean != null) {
					result.add(refundBean);
					if (result != null && result.size() > 0) {
						return result;
					}
				}
			} else if (idType.equalsIgnoreCase("member")) {
				result = refundDAO.findRefundByMemberId(id);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException("idType must be refund, member");
			}
		}
		return null;
	}

	public List<RefundBean> findRefundsByType(String type, String stringValue) {
		if (NullChecker.isEmpty(type) == false && NullChecker.isEmpty(stringValue) == false) {
			List<RefundBean> result = new ArrayList<RefundBean>();
			if (type.equalsIgnoreCase("processStatus")) {
				result = refundDAO.findRefundByProcessStatus(stringValue);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException("type must be processStatus");
			}
		}
		return null;
	}

	public List<RefundBean> findRefunds() {
		List<RefundBean> result = new ArrayList<RefundBean>();
		result = refundDAO.findAllRefund();
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}

	// Find Refund List
	public List<RefundListBean> findRefundListById(String idType, Long id) {
		if (NullChecker.isEmpty(idType) == false && id != null && id.longValue() > 0L) {
			List<RefundListBean> result = new ArrayList<RefundListBean>();
			if (idType.equalsIgnoreCase("refundList")) {
				RefundListBean refundListBean = refundDAO.findRefundListByRefundListId(id);
				if (refundListBean != null) {
					result.add(refundListBean);
					if (result != null && result.size() > 0) {
						return result;
					}
				}
			} else if (idType.equalsIgnoreCase("purchaseList")) {
				RefundListBean refundListBean = refundDAO.findRefundListByPurchaseListId(id);
				if (refundListBean != null) {
					result.add(refundListBean);
					if (result != null && result.size() > 0) {
						return result;
					}
				}
			} else if (idType.equalsIgnoreCase("refund")) {
				result = refundDAO.findRefundListByRefundId(id);
				if (result != null && result.size() > 0) {
					return result;
				}
			} else {
				throw new IllegalArgumentException("idType must be refundList, purchaseList, refund");
			}
		}
		return null;
	}

	public List<RefundListBean> findAllRefundList() {
		List<RefundListBean> result = new ArrayList<RefundListBean>();
		result = refundDAO.findAllRefundList();
		if (result != null && result.size() > 0) {
			return result;
		}
		return null;
	}

}
