package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.ddns.eeitdemo.eeit106team01.shop.ShopTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;

public class RefundDAOImplTest extends ShopTest {

	@Autowired
	private RefundDAO refundDAO;

	Date date = new Date(System.currentTimeMillis());

//	@Test
	public void testInsertRefund() {
		RefundBean refundBean = new RefundBean();
		refundBean.setCreateTime(date);
		refundBean.setUpdatedTime(date);
		refundBean.setComment("這個東西用兩天就故障，我要退貨!");
		refundBean.setProcessStatus("退費申請審核中");

		refundDAO.insertRefund(refundBean);
		String actual = "退費申請審核中";
		assertEquals(refundDAO.findRefundByPrimaryKey(1L).getProcessStatus(), actual);
	}

//	@Test
	public void testUpdateRefund() {
		RefundBean refundBean = refundDAO.findRefundByPrimaryKey(1L);
		refundBean.setUpdatedTime(date);
		refundBean.setProcessStatus("退費已完成");
		refundDAO.updateRefund(refundBean);
		String actual = "退費已完成";
		assertEquals(refundDAO.findRefundByPrimaryKey(1L).getProcessStatus(), actual);
	}

//	@Test
	public void testFindRefundByPrimaryKey() {
		refundDAO.findRefundByPrimaryKey(2L);
		String actual = "退費申請審核中";
		assertEquals(refundDAO.findRefundByPrimaryKey(2L).getProcessStatus(), actual);
	}

//	@Test
	public void testFindRefunds() {
		System.out.println(refundDAO.findRefunds());
	}

//	@Test
	public void testInsertRefundDetail() {
		RefundListBean refundDetailBean = new RefundListBean();

		refundDAO.insertRefundDetail(refundDetailBean);
	}

//	@Test
	public void testUpdateRefundDetail() {
		RefundListBean refundDetailBean = refundDAO.findRefundDetailByPrimaryKey(1L);
		refundDAO.updateRefundDetail(refundDetailBean);

	}

//	@Tests
	public void testFindRefundDetailByPrimaryKey() {
	}

//	@Test
	public void testFindRefundDetails() {
		System.out.println(refundDAO.findRefundDetails().toString());
	}

//	@Test
	public void testFindRefundsByMemberId() throws Exception {
		System.out.println(refundDAO.findRefundsByMemberId(2L).size());
	}

	@Test
	public void testFindRefundDetailsByRefundId() throws Exception {
		System.out.println(refundDAO.findRefundDetailsByRefundId(6L).size());
	}

}
