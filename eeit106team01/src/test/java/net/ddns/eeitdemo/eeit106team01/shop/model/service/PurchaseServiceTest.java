package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.ShopTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseServiceTest extends ShopTest {

	@Autowired
	private PurchaseService purchaseService;

//	@Autowired
//	private ProductService productService;

	@Autowired
	private MemberDAO memberDAO;

//	private ArrayList<Integer> productIdList;
//	private NewDate newDate = new NewDate();
//	private Date currentTime = newDate.newCurrentTime();


	@Test
	public void name() {
		
	}
	
	public void testNewPurchase() throws Exception {
		Member member = new Member();
		memberDAO.insertMember(member);

//		Integer productTotalPrice = (productService.findProductByPrimaryKey(5L).getPrice() * 5)
//				+ productService.findProductByPrimaryKey(6L).getPrice()
//				+ productService.findProductByPrimaryKey(7L).getPrice();

		HashMap<String, String> receiverInformation = new HashMap<String, String>();
		receiverInformation.put("receiver", "Alex");
		receiverInformation.put("address", "Neihu district");

//		PurchaseBean purchaseBean = new PurchaseBean("unpaid", date, date, productTotalPrice, "unsend", "7-11", 200,
//				receiverInformation, member);

//		purchaseService.newPurchase(productIdList, purchaseBean);
	}

	public void testUpdatePurchase() throws Exception {
		List<PurchaseBean> purchaseBeans = purchaseService.findPurchaseById(1L, "purchase");
		PurchaseBean purchaseBean = purchaseBeans.get(0);
//		purchaseBean.setUpdatedTime(NewDate.newCurrentTime());
		assertNull(purchaseService.updatePurchase(purchaseBean, "paid", null, null));
		assertNotNull(purchaseService.updatePurchase(purchaseBean, "unpaid", null, null));
		assertNotNull(purchaseService.updatePurchase(purchaseBean, null, null, 20000));
	}

	public void testFindPurchaseById() throws Exception {
		assertNotNull(purchaseService.findPurchaseById(1L, "purchase"));
		assertNotNull(purchaseService.findPurchaseById(1L, "member"));
	}

	public void testFindPurchaseByType() throws Exception {
//		assertNotNull(purchaseService.findPurchaseByType("time", NewDate.newDate("yyyy-MM-dd", "2019-05-28"),
//				NewDate.newDate("yyyy-MM-dd", "2019-05-29"), null, null));
		assertNotNull(purchaseService.findPurchaseByType("deliverStatus", null, null, "paid", null));
		assertNotNull(purchaseService.findPurchaseByType("deliverType", null, null, "7-11", null));
		assertNotNull(purchaseService.findPurchaseByType("payStatus", null, null, "inprocess", null));
		assertNotNull(purchaseService.findPurchaseByType("price", null, null, null, 12440));
		assertNotNull(purchaseService.findPurchaseByType("priceHigher", null, null, null, 10000));
	}

	public void testFindPurchaseListById() throws Exception {
		assertNotNull(purchaseService.findPurchaseListById(1L, "product"));
		assertNotNull(purchaseService.findPurchaseListById(2L, "product"));
		assertNotNull(purchaseService.findPurchaseListById(1L, "purchase"));
		assertNotNull(purchaseService.findPurchaseListById(4L, "purchaseList"));
	}

	public void testFindPurchaseListByType() throws Exception {
		assertNotNull(purchaseService.findPurchaseListByType("serialNumber", "4AAfc6SyhHI9DD2z3idV", null));
		assertNotNull(purchaseService.findPurchaseListByType("price", null, 3980));
		assertNotNull(purchaseService.findPurchaseListByType("priceLower", null, 4000));
		assertNotNull(purchaseService.findPurchaseListByType("priceHigher", null, 1000));
	}

	public void testNewReviews() throws Exception {
		List<ReviewBean> reviewBeans = new ArrayList<ReviewBean>();
//		Member member = memberDAO.findByMemberId(1L);
//		PurchaseListBean purchaseListId1 = purchaseService.findPurchaseListById(1L, "PurchaseList").get(0);
//		PurchaseListBean purchaseListId2 = purchaseService.findPurchaseListById(2L, "PurchaseList").get(0);
//		PurchaseListBean purchaseListId3 = purchaseService.findPurchaseListById(3L, "PurchaseList").get(0);
//		PurchaseListBean purchaseListId4 = purchaseService.findPurchaseListById(4L, "PurchaseList").get(0);
//		PurchaseListBean purchaseListId5 = purchaseService.findPurchaseListById(5L, "PurchaseList").get(0);
//		ReviewBean reviewBean;
//		reviewBean = new ReviewBean(date, date, 10d, "test1", member, purchaseListId1, purchaseListId1.getProductId());
//		reviewBeans.add(reviewBean);
//		reviewBean = new ReviewBean(date, date, 10d, "test1", member, purchaseListId2, purchaseListId2.getProductId());
//		reviewBeans.add(reviewBean);
//		reviewBean = new ReviewBean(date, date, 9d, "test1", member, purchaseListId3, purchaseListId3.getProductId());
//		reviewBeans.add(reviewBean);
//		reviewBean = new ReviewBean(date, date, 8d, "test1", member, purchaseListId4, purchaseListId4.getProductId());
//		reviewBeans.add(reviewBean);
//		reviewBean = new ReviewBean(date, date, 5d, "test1", member, purchaseListId5, purchaseListId5.getProductId());
//		reviewBeans.add(reviewBean);
		purchaseService.newReviews(reviewBeans);
	}

	public void testUpdateReview() throws Exception {
		ReviewBean reviewBean = purchaseService.findReviewById("review", 1L).get(0);
//		reviewBean.setUpdatedTime(NewDate.newCurrentTime());

		// get path object pointing to file
		Path filePath = Paths.get("D:\\Pictures\\photo.png");
		// get byte array with file contents
		byte[] fileContent = Files.readAllBytes(filePath);

		SerialBlob serialBlob = new javax.sql.rowset.serial.SerialBlob(fileContent);
		assertNotNull(purchaseService.updateReview(reviewBean, null, null,
				serialBlob.getBytes(1, (int) serialBlob.length())));
	}

	public void testFindReviewById() throws Exception {
		assertNotNull(purchaseService.findReviewById("review", 1L));
		assertNotNull(purchaseService.findReviewById("member", 1L));
		assertNotNull(purchaseService.findReviewById("product", 1L));
	}

	public void testFindReviewByType() throws Exception {
//		Date startDay = NewDate.newDate("yyyy-MM-dd", "2019-05-29");
//		Date endDay = NewDate.newDate("yyyy-MM-dd", "2019-05-30");
//		assertNotNull(purchaseService.findReviewByType("time", startDay, endDay, null, null, null));
		assertNotNull(purchaseService.findReviewByType("image", null, null, null, null, true));
		assertNotNull(purchaseService.findReviewByType("rating", null, null, null, 10d, null));
//		assertNull(purchaseService.findReviewByType("ratingLower", startDay, endDay, null, 4d, null));
//		assertNotNull(purchaseService.findReviewByType("ratingHigher", startDay, endDay, null, 10d, null));
	}

}
