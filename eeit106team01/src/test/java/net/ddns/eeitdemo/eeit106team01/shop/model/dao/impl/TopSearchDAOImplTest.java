package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.TopSearchBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.TopSearchDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopSearchDAOImplTest {

	@Autowired
	private TopSearchDAO topSearchDAO;

	private TopSearchBean topSearchBean;
	Date date = NewDate.newCurrentTime();

	public void testInsertTopSearch() {
		topSearchBean = new TopSearchBean("test", 20, date);
		assertNotNull(topSearchDAO.insertTopSearch(topSearchBean));
	}

	public void testUpdateTopSearch() throws Exception {
		topSearchBean = topSearchDAO.findTopSearchByTopSearchId(1137L);
		topSearchBean.setSearchCount(30);
		topSearchDAO.updateTopSearch(topSearchBean);
		assertEquals(topSearchDAO.findTopSearchByTopSearchId(1137L).getSearchCount(), new Integer(30));
	}

	public void testFindTopSearchByTopSearchId() throws Exception {
		assertNotNull(topSearchDAO.findTopSearchByTopSearchId(1137L));
	}

//	@Test
	public void testFindAllTopSearch() throws Exception {
		assertNotNull(topSearchDAO.findAllTopSearch());
	}

}
