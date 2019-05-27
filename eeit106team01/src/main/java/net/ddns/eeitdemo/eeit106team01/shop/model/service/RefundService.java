package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;

@Service
@Transactional
public class RefundService {

	@Autowired
	private RefundDAO refundDAO;

	public RefundBean createRefund(RefundBean refund, List<RefundListBean> refundDetails) {
		if (refund != null && refundDetails != null) {
			Date date = new Date(System.currentTimeMillis());
			refund.setCreateTime(date);
			refund.setUpdatedTime(date);
			refund.setProcessStatus("refund created");

			Iterator<RefundListBean> iterator = refundDetails.iterator();
			while (iterator.hasNext()) {
				RefundListBean refundDetail = (RefundListBean) iterator.next();
				refundDetail.setRefundId(refund);
				refundDAO.insertRefundDetail(refundDetail);
			}

			RefundBean result = refundDAO.insertRefund(refund);

			return result;
		}
		return null;
	}

	public RefundBean updateRefundProcessStatus(RefundBean refund, String processStatus) {
		if (refund != null && processStatus != null) {
			Date date = new Date(System.currentTimeMillis());
			refund.setUpdatedTime(date);
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
