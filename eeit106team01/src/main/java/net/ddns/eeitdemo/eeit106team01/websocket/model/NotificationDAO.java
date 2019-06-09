package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.util.List;

public interface NotificationDAO {
	public NotificationBean findByPrimaryKey(int id);

	public NotificationBean insert(NotificationBean notificationBean);

	public NotificationBean update(NotificationBean notificationBean);

	public List<NotificationBean> queryList(String hql, Integer startPosition, Integer maxResult);
}
