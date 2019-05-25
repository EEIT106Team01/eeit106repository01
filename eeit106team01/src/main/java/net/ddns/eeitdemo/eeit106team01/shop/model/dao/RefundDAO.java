package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;

public interface RefundDAO {

	// Refund
	abstract RefundBean insertRefund(RefundBean refundBean);

	abstract RefundBean updateRefund(RefundBean refundBean);

	abstract RefundBean findRefundByPrimaryKey(Long id);
	
	abstract List<RefundBean>findRefundsByMemberId(Long id);

	abstract List<RefundBean> findRefunds();

	// Refund Detail
	abstract RefundListBean insertRefundDetail(RefundListBean refundDetailBean);

	abstract RefundListBean updateRefundDetail(RefundListBean refundDetailBean);

	abstract RefundListBean findRefundDetailByPrimaryKey(Long id);
	
	abstract List<RefundListBean> findRefundDetailsByRefundId(Long id);

	abstract List<RefundListBean> findRefundDetails();

}
