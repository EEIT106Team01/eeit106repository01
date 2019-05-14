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

		MemberBeanTest beanTest1 = new MemberBeanTest();
		MemberBeanTest beanTest2 = new MemberBeanTest();
		MemberBeanTest beanTest3 = new MemberBeanTest();
		MemberBeanTest beanTest4 = new MemberBeanTest();
		MemberBeanTest beanTest5 = new MemberBeanTest();
		
		beanTest1.setId(1L);
		beanTest1.setName("Alex");
		beanTest2.setId(2L);
		beanTest2.setName("Betty");
		beanTest3.setId(3L);
		beanTest3.setName("Carl");
		beanTest4.setId(4L);
		beanTest4.setName("Denny");
		beanTest5.setId(5L);
		beanTest5.setName("Edge");

		session.save(beanTest1);
		session.save(beanTest2);
		session.save(beanTest3);
		session.save(beanTest4);
		session.save(beanTest5);

		session.beginTransaction().commit();
		session.close();
	}

}
