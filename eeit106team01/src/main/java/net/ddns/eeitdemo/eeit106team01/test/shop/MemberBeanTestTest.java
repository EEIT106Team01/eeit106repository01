package net.ddns.eeitdemo.eeit106team01.test.shop;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import net.ddns.eeitdemo.eeit106team01.model.MemberBeanTest;

@Component
public class MemberBeanTestTest implements CommandLineRunner {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void run(String... args) throws Exception {
		System.err.println("[MemberBeanTest] Test Begin ----------");

//		insert
		Session session = sessionFactory.openSession();
		
		MemberBeanTest bean = new MemberBeanTest();
		bean.setId(3L);
		bean.setName("Alex");
		session.save(bean);
		session.beginTransaction().commit();
		session.close();

		System.err.println("[MemberBeanTest] Test End ----------");

	}

}
