package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.MemberBeanTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberTestDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;

@Service
@Transactional
public class RefundService {

	@Autowired
	private RefundDAO refundDAO;
	
	@Autowired
	private MemberTestDAO memberTestDAO;

	public RefundBean createRefund(RefundBean refund, List<RefundDetailBean> refundDetails) {
		if (refund != null && refundDetails != null) {
			refund.setCreateTime();
			refund.setUpdatedTime();
			refund.setProcessStatus("refund created");

			Iterator<RefundDetailBean> iterator = refundDetails.iterator();
			while (iterator.hasNext()) {
				RefundDetailBean refundDetail = (RefundDetailBean) iterator.next();
				refundDetail.setRefundBean(refund);
				refundDAO.insertRefundDetail(refundDetail);
			}

			RefundBean result = refundDAO.insertRefund(refund);

			return result;
		}
		return null;
	}

	public RefundBean updateRefundProcessStatus(RefundBean refund, String processStatus) {
		if (refund != null && processStatus != null) {
			refund.setUpdatedTime();
			refund.setProcessStatus(processStatus);
			RefundBean result = refundDAO.updateRefund(refund);
			return result;
		}
		return null;
	}
	
	public List<RefundBean> findRefundsByMemberId(Long memberId) {
		if (memberId != null) {
			return refundDAO.findRefundsByMemberId(memberId);
		}
		return null;
	}
	
	public List<RefundBean> findRefunds() {
		return refundDAO.findRefunds();
	}

}
