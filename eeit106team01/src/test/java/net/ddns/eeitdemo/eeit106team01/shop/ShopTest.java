package net.ddns.eeitdemo.eeit106team01.shop;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopTest {

	@Before
	public void init() {
		System.err.println("Shop Test Begin --------------------");
	}

	@After
	public void after() {
		System.err.println("Shop Test End   --------------------");
	}

}
