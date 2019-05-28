package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;

public interface RefundDAO {

	// Refund
	abstract RefundBean insertRefund(RefundBean refundBean);

	abstract RefundBean updateRefund(RefundBean refundBean);

	abstract RefundBean findRefundByRefundId(Long refundId);
	
	abstract List<RefundBean>findRefundByMemberId(Long memberId);

	abstract List<RefundBean>findRefundByProcessStatus(String processStatus);
	
	abstract List<RefundBean> findAllRefund();

	// Refund List
	abstract RefundListBean insertRefundList(RefundListBean refundListBean);

	abstract RefundListBean findRefundListByRefundListId(Long refundListId);
	
	abstract RefundListBean findRefundListByPurchaseListId(Long purchaseListId);
	
	abstract List<RefundListBean> findRefundListByRefundId(Long refundId);

	abstract List<RefundListBean> findAllRefundList();

}
