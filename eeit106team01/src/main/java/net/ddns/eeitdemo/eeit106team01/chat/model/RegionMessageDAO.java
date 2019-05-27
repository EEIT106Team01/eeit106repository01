package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.util.List;

public interface RegionMessageDAO {

	public RegionMessageBean findByPrimaryKey(int id);

	public List<RegionMessageBean> findAll();

	public RegionMessageBean insert(RegionMessageBean regionMessageBean);

	public RegionMessageBean update(RegionMessageBean regionMessageBean);

	public List<RegionMessageBean> queryList(String hql, Integer startPosition, Integer maxResult);
}
