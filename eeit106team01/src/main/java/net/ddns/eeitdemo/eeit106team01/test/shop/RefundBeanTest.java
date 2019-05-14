package net.ddns.eeitdemo.eeit106team01.test.shop;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import net.ddns.eeitdemo.eeit106team01.model.MemberBeanTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;

@Component
public class RefundBeanTest implements CommandLineRunner {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void run(String... args) throws Exception {
		System.err.println("[RefundBeanTest] Test Begin ----------");

//		insert
		Session session = sessionFactory.openSession();
		
		RefundBean bean = new RefundBean();
		bean.setId(1L);
		bean.setComment("good");
		bean.setProcessStatus("");
		session.save(bean);
		session.beginTransaction().commit();
		session.close();

		System.err.println("[RefundBeanTest] Test End ----------");

	}

}
