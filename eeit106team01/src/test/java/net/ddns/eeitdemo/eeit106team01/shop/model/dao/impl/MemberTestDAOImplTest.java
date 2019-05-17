package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.MemberBeanTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberTestDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberTestDAOImplTest {

	@Autowired
	private MemberTestDAO memberTestDAO;

//	@Test
	public void testInsert() {
		MemberBeanTest member1 = new MemberBeanTest();
		member1.setAccount("hee");
		MemberBeanTest member2 = new MemberBeanTest();
		member2.setAccount("Baby");
		MemberBeanTest member3 = new MemberBeanTest();
		member3.setAccount("Cat");
		MemberBeanTest member4 = new MemberBeanTest();
		member4.setAccount("Dog");
		MemberBeanTest member5 = new MemberBeanTest();
		member5.setAccount("Frank");

		memberTestDAO.insert(member1);
		memberTestDAO.insert(member2);
		memberTestDAO.insert(member3);
		memberTestDAO.insert(member4);
		memberTestDAO.insert(member5);

	}

//	@Test
	public void testUpdate() {
		MemberBeanTest member1 = memberTestDAO.findByPrimaryKey(1L);
		member1.setAccount("KK");
		memberTestDAO.update(member1);

		String actual = "KK";
		assertEquals(memberTestDAO.findByPrimaryKey(1L).getAccount(), actual);
	}

//	@Test
	public void testFindByPrimaryKey() {
		String actual = "KK";
		assertEquals(memberTestDAO.findByPrimaryKey(1L).getAccount(), actual);
	}

	@Test
	public void testFindMembers() {
		assertNotNull(memberTestDAO.findMembers());
		System.out.println(memberTestDAO.findMembers().toString());
	}

}
