package net.ddns.eeitdemo.eeit106team01.shop;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.product.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.ReviewBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertDataForTest extends ShopTest {

	@Autowired
	private SessionFactory sessionFactory;

//	@Test
	public void insertMember() {

		Session session = sessionFactory.openSession();

		MemberBeanTest beanTest1 = new MemberBeanTest();
		MemberBeanTest beanTest2 = new MemberBeanTest();
		MemberBeanTest beanTest3 = new MemberBeanTest();
		MemberBeanTest beanTest4 = new MemberBeanTest();
		MemberBeanTest beanTest5 = new MemberBeanTest();

		beanTest1.setName("Alex");
		beanTest2.setName("Betty");
		beanTest3.setName("Carl");
		beanTest4.setName("Denny");
		beanTest5.setName("Edge");

		session.save(beanTest1);
		session.save(beanTest2);
		session.save(beanTest3);
		session.save(beanTest4);
		session.save(beanTest5);

		session.beginTransaction().commit();
		session.close();
	}

//	@Test
	public void insertProduct() {

		Session session = sessionFactory.openSession();

		ProductBean beanTest1 = new ProductBean();
		ProductBean beanTest2 = new ProductBean();
		ProductBean beanTest3 = new ProductBean();
		ProductBean beanTest4 = new ProductBean();
		ProductBean beanTest5 = new ProductBean();

		beanTest1.setName("ABC牌大鎖");
		beanTest1.setDate();
		beanTest2.setName("ZZZ牌鎖頭");
		beanTest2.setDate();
		beanTest3.setName("辣椒牌錄影機");
		beanTest3.setDate();
		beanTest4.setName("青椒排牌行車紀錄器");
		beanTest4.setDate();
		beanTest5.setName("奶奶牌行車紀錄器 電池");
		beanTest5.setDate();

		session.save(beanTest1);
		session.save(beanTest2);
		session.save(beanTest3);
		session.save(beanTest4);
		session.save(beanTest5);

		session.beginTransaction().commit();
		session.close();
	}

//	@Test
	public void insertReview() {

		Session session = sessionFactory.openSession();

		ReviewBean beanTest1 = new ReviewBean();
		ReviewBean beanTest2 = new ReviewBean();
		ReviewBean beanTest3 = new ReviewBean();
		ReviewBean beanTest4 = new ReviewBean();
		ReviewBean beanTest5 = new ReviewBean();

		beanTest1.setComment("Good!");
		beanTest1.setDate();
		beanTest1.setRating(10);
		beanTest2.setComment("Good!");
		beanTest2.setDate();
		beanTest2.setRating(5);
		beanTest3.setComment("還好");
		beanTest3.setDate();
		beanTest3.setRating(6);
		beanTest4.setComment("還可以拉");
		beanTest4.setDate();
		beanTest4.setRating(6);
		beanTest5.setComment("有待加強");
		beanTest5.setDate();
		beanTest5.setRating(7);

		session.save(beanTest1);
		session.save(beanTest2);
		session.save(beanTest3);
		session.save(beanTest4);
		session.save(beanTest5);

		session.beginTransaction().commit();
		session.close();
	}

}
