package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.MemberBeanTest;

public interface MemberTestDAO {

	abstract MemberBeanTest insert(MemberBeanTest memberBean);

	abstract MemberBeanTest update(MemberBeanTest memberBean);

	abstract MemberBeanTest findByPrimaryKey(Long id);

	abstract List<MemberBeanTest> findMembers();
}
