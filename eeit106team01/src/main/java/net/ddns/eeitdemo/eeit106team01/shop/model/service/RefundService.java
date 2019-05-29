package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;

@Service
@Transactional
public class RefundService {

	@Autowired
	private RefundDAO refundDAO;

	// Create Refunds
	public RefundBean newRefund(ArrayList<PurchaseListBean> purchaseListBeans, RefundBean refund) {
		if (purchaseListBeans != null && purchaseListBeans.size() > 0 && refund.isNotNull()) {
			//Get PurchaseList want to be refund
			
			//Create a refund
			
			//Change SN status, change total refund
			
		
			
		}
		return null;
	}

	public RefundBean updateRefundProcessStatus(RefundBean refund, String processStatus) {

		return null;
	}

	public List<RefundBean> findRefundsByMemberId(Long memberId) {

		return null;
	}

	public List<RefundBean> findRefunds() {
		return refundDAO.findAllRefund();
	}

}
