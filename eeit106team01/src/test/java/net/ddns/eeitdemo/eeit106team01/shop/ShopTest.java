package net.ddns.eeitdemo.eeit106team01.shop;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopTest {

	@Autowired
	SessionFactory sessionFactory;

	@Before
	public void init() {
		System.err.println("Shop Test Begin --------------------");
	}

	@After
	public void after() {
		System.err.println("Shop Test End   --------------------");
	}

}
