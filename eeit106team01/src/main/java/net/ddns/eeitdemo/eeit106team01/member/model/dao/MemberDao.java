package net.ddns.eeitdemo.eeit106team01.member.model.dao;

public interface MemberDao<T> {
	public T getUserInformationBeanByEmail(String email);
}
