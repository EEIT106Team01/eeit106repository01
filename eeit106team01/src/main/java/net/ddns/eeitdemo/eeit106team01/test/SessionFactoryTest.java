
//package net.ddns.eeitdemo.eeit106team01.test;
//
//import java.util.List;
//
//import javax.persistence.Query;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import net.ddns.eeitdemo.eeit106team01.model.ProductBean;
//
//@Component
//public class SessionFactoryTest implements CommandLineRunner {
//	
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public void run(String... args) throws Exception {
//		Session session = sessionFactory.openSession();
//		Query query = session.createQuery("from ProductBean", ProductBean.class);
//		List<ProductBean> result = query.getResultList();
//		for(ProductBean value: result) {
//			System.out.println(value);
//		}
//	}
//
//}