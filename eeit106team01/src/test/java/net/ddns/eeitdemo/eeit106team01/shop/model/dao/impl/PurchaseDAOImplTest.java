package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.PurchaseDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

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
	private Member member;
	private Date date = new Date(System.currentTimeMillis());
	private static HashMap<String, String> hashMap = new HashMap<String, String>();
	
	
	@BeforeClass
	public static void beforeClass() {
		hashMap.put("", "");
	}
	
	@Test
	public void testInsertOrder() {
		member = new Member();
		purchaseBean = new PurchaseBean("", date, date, 0, "", "", 0, hashMap, member);
//		assertNotNull(object);
	}

	@Test
	public void testUpdateOrder() {
	}

	@Test
	public void testFindOrderByOrderId() {
	}

}
