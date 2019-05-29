package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.util.List;

public interface PrivateMessageDAO {

	public PrivateMessageBean findByPrimaryKey(int id);

	public List<PrivateMessageBean> findAll();

	public List<PrivateMessageBean> findAllByUser(String user);

	public PrivateMessageBean insert(PrivateMessageBean privateMessageBean);

	public PrivateMessageBean update(PrivateMessageBean privateMessageBean);

	public List<PrivateMessageBean> queryList(String hql, Integer startPosition, Integer maxResult);

}
