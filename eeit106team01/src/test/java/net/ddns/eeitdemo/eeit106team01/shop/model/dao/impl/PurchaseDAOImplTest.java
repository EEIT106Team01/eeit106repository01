package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.PurchaseDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseDAOImplTest {

	@Autowired
	private PurchaseDAO purchaseDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private MemberDAO memberDAO;

	private PurchaseBean purchaseBean;
	private PurchaseListBean purchaseListBean;
	private ReviewBean reviewBean;
	private Member member;
	private Date date = new Date(System.currentTimeMillis());
	private static HashMap<String, String> hashMap = new HashMap<String, String>();

	@BeforeClass
	public static void beforeClass() {
		hashMap.put("", "");
	}

	
	@Test
	public void name() {
		
	}
	
	// Purchase

	public void testInsertPurchase() {
		this.member = new Member();
		memberDAO.insertMember(member);
		this.purchaseBean = purchaseDAO
				.insertPurchase(new PurchaseBean("", date, date, 100, "", "test", 0, hashMap, member));
		this.purchaseBean = purchaseDAO
				.insertPurchase(new PurchaseBean("", date, date, 200, "", "test", 0, hashMap, member));
		this.purchaseBean = purchaseDAO
				.insertPurchase(new PurchaseBean("", date, date, 1000, "", "test", 0, hashMap, member));
		assertNotNull(purchaseBean);
	}

	public void testUpdatePurchase() {
		this.purchaseBean = purchaseDAO.findPurchaseByPurchaseId(1L);
		this.purchaseBean.setDeliverStatus("test");
		this.purchaseBean.setDeliverType("test");
		this.purchaseBean.setPayStatus("test");
		assertNotNull(purchaseDAO.updatePurchase(this.purchaseBean));
	}

	public void testFindAllPurchaseByPurchaseId() {
		assertNotNull(purchaseDAO.findPurchaseByPurchaseId(1L));
	}

	public void testFindPurchaseByTimeDayBetween() throws Exception {
//		assertNotNull(purchaseDAO.findPurchaseByTimeDayBetween(NewDate.newDate("yyyy-MM-dd", "2019-05-27"),
//				NewDate.newDate("yyyy-MM-dd", "2019-05-28")));
	}

	public void testFindPurchaseByDeliverStatus() throws Exception {
		assertNotNull(purchaseDAO.findPurchaseByDeliverStatus("test"));
	}

	public void testFindPurchaseByDeliverType() throws Exception {
		assertNotNull(purchaseDAO.findPurchaseByDeliverType("Test"));
	}

	public void testFindPurchaseByPayStatus() throws Exception {
		assertNotNull(purchaseDAO.findPurchaseByPayStatus("test"));
	}

	public void testFindPurchaseByProductTotalPrice() throws Exception {
		assertNotNull(purchaseDAO.findPurchaseByProductTotalPrice(0));
	}

	public void testFindPurchaseByProductTotalPriceLower() throws Exception {
		assertEquals(new Integer(purchaseDAO.findPurchaseByProductTotalPriceLower(200).size()), new Integer(9));
	}

	public void testFindPurchaseByProductTotalPriceHigher() throws Exception {
		assertEquals(new Integer(purchaseDAO.findPurchaseByProductTotalPriceHigher(200).size()), new Integer(6));
	}

	public void testFindPurchaseByMemberId() throws Exception {
		assertEquals(new Integer(purchaseDAO.findPurchaseByMemberId(1L).size()), new Integer(1));
	}

	public void testFindAllPurchase() throws Exception {
		assertEquals(new Integer(purchaseDAO.findAllPurchase().size()), new Integer(12));
	}

	// Purchase List
//	@Test
	public void testInsertPurchaseList() throws Exception {
		this.purchaseListBean = new PurchaseListBean(200, "test123", purchaseDAO.findPurchaseByPurchaseId(61L),productDAO.findProductByProductId(1L));
		assertNotNull(purchaseDAO.insertPurchaseList(purchaseListBean));
		this.purchaseListBean = new PurchaseListBean(200, "test321", purchaseDAO.findPurchaseByPurchaseId(62L),productDAO.findProductByProductId(1L));
		assertNotNull(purchaseDAO.insertPurchaseList(purchaseListBean));
	}

	public void testFindPurchaseListByPurchaseListId() throws Exception {
		assertNotNull(purchaseDAO.findPurchaseListByPurchaseListId(1L));
	}

	public void testFindPurchaseListByPurchaseId() throws Exception {
		assertEquals(new Integer(purchaseDAO.findPurchaseListByPurchaseId(1L).size()), new Integer(1));
	}

	public void testFindAllPurchaseList() throws Exception {
		assertNotNull(purchaseDAO.findAllPurchaseList());
	}

	public void testFindPurchaseListBySerialNumber() throws Exception {
		assertNotNull(purchaseDAO.findPurchaseListBySerialNumber("test789"));
	}

	public void testFindPurchaseListByPrice() throws Exception {
		assertNotNull(purchaseDAO.findPurchaseListByPrice(0));
	}

	public void testFindPurchaseListByPriceLower() throws Exception {
		assertEquals(new Integer(purchaseDAO.findPurchaseListByPriceLower(100).size()), new Integer(3));
	}

	public void testFindPurchaseListByPriceHigher() throws Exception {
		assertEquals(new Integer(purchaseDAO.findPurchaseListByPriceHigher(200).size()), new Integer(1));
	}

	public void testFindPurchaseListByProductId() throws Exception {
		assertEquals(new Integer(purchaseDAO.findPurchaseListByProductId(3L).size()), new Integer(1));
	}

	public void testInsertReview() throws Exception {
		reviewBean = new ReviewBean(date, date, 5d, "test", memberDAO.findByMemberId(1L),
				purchaseDAO.findPurchaseListByPurchaseListId(1L), productDAO.findProductByProductId(1L));
		assertNotNull(purchaseDAO.insertReview(reviewBean));
	}

	public void testUpdateReview() throws Exception {
		reviewBean = purchaseDAO.findReviewByReviewId(1L);
		reviewBean.setComment("test2");
		assertEquals(new String(purchaseDAO.updateReview(reviewBean).getComment()), new String("test2"));
	}

	public void testFindReviewByReviewId() throws Exception {
		assertNull(purchaseDAO.findReviewByReviewId(5L));
	}

	public void testFindReviews() throws Exception {
		assertEquals(new Integer(purchaseDAO.findReviews().size()), new Integer(1));
	}

	public void testFindReviewsByTimeDayBetween() throws Exception {
//		assertNull(purchaseDAO.findReviewsByTimeDayBetween(NewDate.newDate("yyyy-MM-dd", "2019-05-26"),
//				NewDate.newDate("yyyy-MM-dd", "2019-05-27")));
//		assertNotNull(purchaseDAO.findReviewsByTimeDayBetween(NewDate.newDate("yyyy-MM-dd", "2019-05-27"),
//				NewDate.newDate("yyyy-MM-dd", "2019-05-28")));
	}

	public void testFindReviewsByImageExistence() throws Exception {
		assertNotNull(purchaseDAO.findReviewsByImageExistence(false));
		assertNull(purchaseDAO.findReviewsByImageExistence(true));
	}

	public void testFindReviewsByRating() throws Exception {
		assertNotNull(purchaseDAO.findReviewsByRating(5d));
		assertNull(purchaseDAO.findReviewsByRating(4d));
	}

	public void testFindReviewsByRatingLower() throws Exception {
		assertNotNull(purchaseDAO.findReviewsByRatingLower(6d));
	}

	public void testFindReviewsByRatingHigher() throws Exception {
		assertNotNull(purchaseDAO.findReviewsByRatingHigher(5d));
	}

	public void testFindReviewsByMemberId() throws Exception {
		assertNotNull(purchaseDAO.findReviewsByMemberId(1L));
	}

	public void testFindReviewsByProductId() throws Exception {
		assertNotNull(purchaseDAO.findReviewsByProductId(1L));
	}

}
