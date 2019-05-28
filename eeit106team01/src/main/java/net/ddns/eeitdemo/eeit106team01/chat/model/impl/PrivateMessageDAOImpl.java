package net.ddns.eeitdemo.eeit106team01.chat.model.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMessageDAO;

@Repository
public class PrivateMessageDAOImpl implements PrivateMessageDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public PrivateMessageBean findByPrimaryKey(int id) {
		return this.getSession().get(PrivateMessageBean.class, id);
	}

	@Override
	public List<PrivateMessageBean> findAll() {
		return this.getSession().createQuery("from PrivateMessageBean", PrivateMessageBean.class).getResultList();
	}
	
	@Override
	public List<PrivateMessageBean> findAllByUser(String user) {
		return this.getSession().createQuery(
				"from PrivateMessageBean pmb where pmb.userOne='" + user + "' or pmb.userTwo='" + user + "'"
				, PrivateMessageBean.class
				).getResultList();
	}
	
	@Override
	public PrivateMessageBean insert(PrivateMessageBean privateMessageBean) {
		if (privateMessageBean != null) {
			Integer id = (Integer) getSession().save(privateMessageBean);
			return findByPrimaryKey(id);
		}
		return null;
	}
	
	@Override
	public PrivateMessageBean update(PrivateMessageBean privateMessageBean) {
		if (privateMessageBean != null) {
			getSession().update(privateMessageBean);
			return findByPrimaryKey(privateMessageBean.getId());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PrivateMessageBean> queryList(String hql, Integer startPosition, Integer maxResult) {
		Query query = getSession().createQuery(hql, PrivateMessageBean.class);
		if ((startPosition != null) && (maxResult != null)) {
			query.setFirstResult(startPosition);
			query.setMaxResults(maxResult);
		}
		return query.getResultList();
	}

}
