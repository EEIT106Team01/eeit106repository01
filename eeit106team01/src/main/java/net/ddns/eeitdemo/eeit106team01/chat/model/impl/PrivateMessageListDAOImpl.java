package net.ddns.eeitdemo.eeit106team01.chat.model.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMessageListBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMessageListDAO;

@Repository
public class PrivateMessageListDAOImpl implements PrivateMessageListDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public PrivateMessageListBean findByPrimaryKey(int id) {
		return this.getSession().get(PrivateMessageListBean.class, id);
	}

	@Override
	public List<PrivateMessageListBean> findAll() {
		return this.getSession().createQuery("from PrivateMessageListBean", PrivateMessageListBean.class)
				.getResultList();
	}

	@Override
	public PrivateMessageListBean findByUser(String user) {
		PrivateMessageListBean result = null;
		List<PrivateMessageListBean> results = this.getSession()
				.createQuery("from PrivateMessageListBean pmlb where pmlb.name='" + user + "'",
						PrivateMessageListBean.class)
				.getResultList();
		if (!results.isEmpty()) {
			result = results.get(0);
		}
		return result;
	}

	@Override
	public PrivateMessageListBean insert(PrivateMessageListBean privateMessageListBean) {
		if (privateMessageListBean != null) {
			Integer id = (Integer) getSession().save(privateMessageListBean);
			return findByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public PrivateMessageListBean update(PrivateMessageListBean privateMessageListBean) {
		if (privateMessageListBean != null) {
			getSession().update(privateMessageListBean);
			return findByPrimaryKey(privateMessageListBean.getId());
		}
		return null;
	}

}
