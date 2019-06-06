package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

public interface MemberTempDAO {

	public MemberTempBean findByPrimaryKey(int id);
	
	public MemberTempBean findByPrimaryKeyAsProxy(int id);

	public MemberTempBean findByName(String name);
	
	public List<MemberTempBean> findAll();

	public MemberTempBean insert(MemberTempBean bean);

	public MemberTempBean update(MemberTempBean bean);

	public boolean delete(int id);
}
