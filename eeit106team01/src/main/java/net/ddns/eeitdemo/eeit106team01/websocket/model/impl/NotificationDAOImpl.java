package net.ddns.eeitdemo.eeit106team01.websocket.model.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.websocket.model.NotificationBean;
import net.ddns.eeitdemo.eeit106team01.websocket.model.NotificationDAO;

@Repository
public class NotificationDAOImpl implements NotificationDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public NotificationBean findByPrimaryKey(int id) {
		return this.getSession().get(NotificationBean.class, id);
	}

	@Override
	public NotificationBean insert(NotificationBean notificationBean) {
		if (notificationBean != null) {
			Integer id = (Integer) getSession().save(notificationBean);
			return findByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public NotificationBean update(NotificationBean notificationBean) {
		if (notificationBean != null) {
			getSession().update(notificationBean);
			return findByPrimaryKey(notificationBean.getId());
		}
		return null;
	}

	@Override
	public List<NotificationBean> queryList(String hql, Integer startPosition, Integer maxResult) {
		Query<NotificationBean> query = getSession().createQuery(hql, NotificationBean.class);
		if ((startPosition != null) && (maxResult != null)) {
			query.setFirstResult(startPosition);
			query.setMaxResults(maxResult);
		}
		return query.getResultList();
	}
}
