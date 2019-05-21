package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundDetailBean;

public interface RefundDAO {

	// Refund
	abstract RefundBean insertRefund(RefundBean refundBean);

	abstract RefundBean updateRefund(RefundBean refundBean);

	abstract RefundBean findRefundByPrimaryKey(Long id);
	
	abstract List<RefundBean>findRefundsByMemberId(Long id);

	abstract List<RefundBean> findRefunds();

	// Refund Detail
	abstract RefundDetailBean insertRefundDetail(RefundDetailBean refundDetailBean);

	abstract RefundDetailBean updateRefundDetail(RefundDetailBean refundDetailBean);

	abstract RefundDetailBean findRefundDetailByPrimaryKey(Long id);
	
	abstract List<RefundDetailBean> findRefundDetailsByRefundId(Long id);

	abstract List<RefundDetailBean> findRefundDetails();

}
