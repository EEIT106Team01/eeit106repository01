package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RefundServiceTest {

	@Autowired
	private RefundService refundService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private MemberDAO memberDAO;

	private Date date = NewDate.newCurrentTime();
	private Member member;
	private ArrayList<PurchaseListBean> purchaseListBeans = new ArrayList<PurchaseListBean>();

	public void testNewRefund() throws Exception {
		member = memberDAO.findByMemberId(1L);
		RefundBean refund = new RefundBean(date, date, "test refund", "created", member);
		PurchaseListBean purchaseListBean = purchaseService.findPurchaseListById(1L, "purchaseList").get(0);
		purchaseListBeans.add(purchaseListBean);
		PurchaseListBean purchaseListBean2 = purchaseService.findPurchaseListById(2L, "purchaseList").get(0);
		purchaseListBeans.add(purchaseListBean2);
		PurchaseListBean purchaseListBean3 = purchaseService.findPurchaseListById(3L, "purchaseList").get(0);
		purchaseListBeans.add(purchaseListBean3);
		refundService.newRefund(purchaseListBeans, refund);
	}

	public void testUpdateRefundProcessStatus() throws Exception {
		RefundBean refundBean = refundService.findRefundsById("refund", 2L).get(0);
		refundBean.setUpdatedTime(date);
		assertNull(refundService.updateRefundProcessStatus(refundBean, "done"));
		assertNotNull(refundService.updateRefundProcessStatus(refundBean, "created"));
	}

	public void testFindRefundsById() throws Exception {
		assertNotNull(refundService.findRefundsById("refund", 2L));
		assertNotNull(refundService.findRefundsById("member", 1L));
	}

	public void testFindRefundsByType() throws Exception {
		assertNotNull(refundService.findRefundsByType("ProcessStatus", "created"));
		assertNull(refundService.findRefundsByType("ProcessStatus", "fail"));
	}

	public void testFindRefunds() throws Exception {
		assertNotNull(refundService.findRefunds());
	}

	public void testFindRefundListById() throws Exception {
		assertNotNull(refundService.findRefundListById("refundList", 3L));
		assertNotNull(refundService.findRefundListById("purchaseList", 3L));
		assertNotNull(refundService.findRefundListById("refund", 2L));
	}

	@Test
	public void testFindAllRefundList() throws Exception {
		assertNotNull(refundService.findAllRefundList());
	}

}
