package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.TopSearchBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.TopSearchDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopSearchDAOImplTest {

	@Autowired private TopSearchDAO topSearchDAO;

//	@Test
	public void testInsertTopSearch() {
//		topSearchDAO.insertTopSearch("行車紀錄器");

	}

//	@Test
	public void testUpdateTopSearch() {
		TopSearchBean topSearchBean2 = new TopSearchBean();
		topSearchBean2.setId(1L);
		topSearchBean2.setKeyWord("紀錄器");
		topSearchBean2.setSearchTimes(3);
		topSearchBean2.setTime();
		topSearchDAO.updateTopSearch(topSearchBean2);
	}

//	@Test
	public void testFindTopSearchByPrimaryKey() {
		System.out.println(topSearchDAO.findTopSearchByPrimaryKey(1L));
	}

//	@Test
	public void testFindTopSearchs() {
		System.out.println(topSearchDAO.findTopSearchs());
	}

}