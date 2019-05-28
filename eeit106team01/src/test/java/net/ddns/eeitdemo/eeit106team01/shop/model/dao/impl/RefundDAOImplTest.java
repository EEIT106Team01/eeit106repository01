package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.ddns.eeitdemo.eeit106team01.shop.ShopTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;

public class RefundDAOImplTest extends ShopTest {

	@Autowired
	private RefundDAO refundDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private MemberDAO memberDAO;

	private RefundBean refundBean;
	private RefundListBean refundListBean;
	private Date date = NewDate.newCurrentTime();

	public void testInsertRefund() throws Exception {
		refundBean = new RefundBean(date, date, "test", "test", memberDAO.findByMemberId(1L));
		assertNotNull(refundDAO.insertRefund(refundBean));
	}

	public void testUpdateRefund() throws Exception {
		refundBean = refundDAO.findRefundByRefundId(1L);
		refundBean.setProcessStatus("update");
		assertEquals(refundDAO.updateRefund(refundBean).getProcessStatus(), new String("update"));
	}

	public void testFindRefundByRefundId() throws Exception {
		assertNotNull(refundDAO.findRefundByRefundId(1L));
	}

	public void testFindRefundByMemberId() throws Exception {
		assertNotNull(refundDAO.findRefundByMemberId(1L));
	}

}
