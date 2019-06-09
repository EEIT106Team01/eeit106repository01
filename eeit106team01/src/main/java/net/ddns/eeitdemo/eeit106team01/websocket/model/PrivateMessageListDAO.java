package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.util.List;

public interface PrivateMessageListDAO {
	
	public PrivateMessageListBean findByPrimaryKey(int id);

	public List<PrivateMessageListBean> findAll();

	public PrivateMessageListBean findByUser(String user);

	public PrivateMessageListBean insert(PrivateMessageListBean privateMessageListBean);

	public PrivateMessageListBean update(PrivateMessageListBean privateMessageListBean);

}
