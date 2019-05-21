package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.ddns.eeitdemo.eeit106team01.shop.ShopTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;

public class RefundDAOImplTest extends ShopTest {

	@Autowired
	private RefundDAO refundDAO;

//	@Test
	public void testInsertRefund() {
		RefundBean refundBean = new RefundBean();
		refundBean.setCreateTime();
		refundBean.setUpdatedTime();
		refundBean.setComment("這個東西用兩天就故障，我要退貨!");
		refundBean.setProcessStatus("退費申請審核中");

		refundDAO.insertRefund(refundBean);
		String actual = "退費申請審核中";
		assertEquals(refundDAO.findRefundByPrimaryKey(1L).getProcessStatus(), actual);
	}

//	@Test
	public void testUpdateRefund() {
		RefundBean refundBean = refundDAO.findRefundByPrimaryKey(1L);
		refundBean.setUpdatedTime();
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
		RefundDetailBean refundDetailBean = new RefundDetailBean();
		refundDetailBean.setSerialNumber("QAZWSXEDC");

		refundDAO.insertRefundDetail(refundDetailBean);
		String actual = "QAZWSXEDC";
		assertEquals(refundDAO.findRefundDetailByPrimaryKey(1L).getSerialNumber(), actual);
	}

//	@Test
	public void testUpdateRefundDetail() {
		RefundDetailBean refundDetailBean = refundDAO.findRefundDetailByPrimaryKey(1L);
		refundDetailBean.setSerialNumber("EDCWSXQAZ");
		refundDAO.updateRefundDetail(refundDetailBean);

		String actual = "EDCWSXQAZ";
		assertEquals(refundDAO.findRefundDetailByPrimaryKey(1L).getSerialNumber(), actual);

	}

//	@Tests
	public void testFindRefundDetailByPrimaryKey() {
		String actual = "EDCWSXQAZ";
		assertEquals(refundDAO.findRefundDetailByPrimaryKey(1L).getSerialNumber(), actual);
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
