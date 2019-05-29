package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
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
		if (topSearchBean.isNotNull()) {
			try {
				this.getSession().save(topSearchBean);
				Long id = topSearchBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findTopSearchByTopSearchId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public TopSearchBean updateTopSearch(TopSearchBean topSearchBean) {
		if (topSearchBean != null) {
			try {
				this.getSession().update(topSearchBean);
				Long id = topSearchBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findTopSearchByTopSearchId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public TopSearchBean findTopSearchByTopSearchId(Long topSearchId) {
		if (topSearchId != null && topSearchId.longValue() > 0L) {
			try {
				TopSearchBean result = this.getSession().get(TopSearchBean.class, topSearchId);
				if (result != null) {
					return result;
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<TopSearchBean> findAllTopSearch() {
		try {
			List<TopSearchBean> result = this.getSession()
					.createQuery("from TopSearchBean order by updatedTime desc", TopSearchBean.class).getResultList();
			if (result != null && result.size() > 0) {
				return result;
			}
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		return null;
	}

}
