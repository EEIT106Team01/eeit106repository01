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
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.PurchaseDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;

public class RefundDAOImplTest extends ShopTest {

	@Autowired
	private RefundDAO refundDAO;
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private PurchaseDAO purchaseDAO;

	private RefundBean refundBean;
	private RefundListBean refundListBean;
	NewDate newDate = new NewDate();
	private Date date = newDate.newCurrentTime();

	@Test
	public void name() {
		
	}
	
	
//	public void testInsertRefund() throws Exception {
//		memberDAO.insertMember(new Member());
//		refundBean = new RefundBean(date, date, "test", "test", memberDAO.findByMemberId(60L));
//		assertNotNull(refundDAO.insertRefund(refundBean));
//	}

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

	public void testFindRefundByProcessStatus() throws Exception {
		assertNotNull(refundDAO.findRefundByProcessStatus("test"));
	}

	public void testFindAllRefund() throws Exception {
		assertNotNull(refundDAO.findAllRefund());
	}

	public void testInsertRefundList() throws Exception {
		refundListBean = new RefundListBean(purchaseDAO.findPurchaseListByPurchaseListId(64L),
				refundDAO.findRefundByRefundId(67L));
		assertNotNull(refundDAO.insertRefundList(refundListBean));
	}

	public void testFindRefundListByRefundListId() throws Exception {
		refundDAO.findRefundListByPurchaseListId(1L);
	}

//	@Test
	public void testFindRefundListByPurchaseListId() throws Exception {
		assertNotNull(refundDAO.findRefundListByPurchaseListId(64L));
	}

	public void testFindRefundListByRefundId() throws Exception {
		assertNotNull(refundDAO.findRefundListByRefundId(67L));
	}

	public void testFindAllRefundList() throws Exception {
		assertNotNull(refundDAO.findAllRefundList());
	}

}
