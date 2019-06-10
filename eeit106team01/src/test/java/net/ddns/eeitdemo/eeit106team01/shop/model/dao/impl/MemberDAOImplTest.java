package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberDAOImplTest {

	@Autowired
	private MemberDAO memberDAO;

	@Test
	public void name() {
		
	}
	
//	@Test
	public void testInsertMember() {
		Member member = new Member();
		Member result = memberDAO.insertMember(member);
		assertNotNull(result);
		assertEquals(result.getId(), new Long(4L));
	}

	public void testFindByMemberId() {
		Member member = memberDAO.findByMemberId(4L);
		// assertNotNull(member);
		assertNull(member);
	}

}
