package net.ddns.eeitdemo.eeit106team01.shop;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberBeanTestTest extends ShopTest {

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void insert() {

		Session session = sessionFactory.openSession();

//		MemberBeanTest beanTest1 = new MemberBeanTest();
//		MemberBeanTest beanTest2 = new MemberBeanTest();
//		MemberBeanTest beanTest3 = new MemberBeanTest();
//		MemberBeanTest beanTest4 = new MemberBeanTest();
//		MemberBeanTest beanTest5 = new MemberBeanTest();
		
//		beanTest1.setName("Alex");
//		beanTest2.setName("Betty");
//		beanTest3.setName("Carl");
//		beanTest4.setName("Denny");
//		beanTest5.setName("Edge");
//		beanTest1.setName("Dick");
//		beanTest2.setName("Fendi");
//		beanTest3.setName("GG");
//		beanTest4.setName("Hi");
//		beanTest5.setName("Icy");

//		session.save(beanTest1);
//		session.save(beanTest2);
//		session.save(beanTest3);
//		session.save(beanTest4);
//		session.save(beanTest5);

		session.beginTransaction().commit();
		session.close();
	}

}
