package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.TopSearchBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.TopSearchDAO;

@Repository
public class TopSearchDAOImpl implements TopSearchDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public TopSearchBean insertTopSearch(TopSearchBean topSearchBean) {
		if (topSearchBean != null) {
			getSession().save(topSearchBean);
			return topSearchBean;
		}
		return null;
	}

	@Override
	public TopSearchBean updateTopSearch(TopSearchBean topSearchBean) {
		if (topSearchBean != null) {
			getSession().update(topSearchBean);
			return findTopSearchByPrimaryKey(topSearchBean.getId());
		}
		return null;
	}

	@Override
	public TopSearchBean findTopSearchByPrimaryKey(Long id) {
		return getSession().get(TopSearchBean.class, id);
	}

	@Override
	public List<TopSearchBean> findTopSearchs() {
		return this.getSession().createQuery("from TopSearchBean order by searchTimes desc", TopSearchBean.class).getResultList();
	}

}
