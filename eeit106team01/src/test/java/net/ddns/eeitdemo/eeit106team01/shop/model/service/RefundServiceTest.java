package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RefundServiceTest {

	@Autowired
	private RefundService refundService;

//	@Test
	public void testCreateRefund() throws Exception {
		RefundBean refund = new RefundBean();
		refund.setComment("我要退貨兩件商品");

		List<RefundListBean> refundDetails = new ArrayList<RefundListBean>();
		RefundListBean refundDetail = new RefundListBean();

		RefundListBean refundDetail1 = new RefundListBean();

		refundDetails.add(refundDetail);
		refundDetails.add(refundDetail1);

		System.out.println(refundService.createRefund(refund, refundDetails).toString());
	}

//	@Test
	public void testUpdateRefundProcessStatus() throws Exception {
		RefundBean refund = new RefundBean();
		refund.setId(3L);
		refund.setComment("123");
		refundService.updateRefundProcessStatus(refund, "refund complete");
	}

//	@Test
	public void testFindRefundsByMemberId() throws Exception {
		System.out.println(refundService.findRefundsByMemberId(2L));
	}

	@Test
	public void testFindRefunds() throws Exception {
		System.out.println(refundService.findRefunds().size());
	}

}
