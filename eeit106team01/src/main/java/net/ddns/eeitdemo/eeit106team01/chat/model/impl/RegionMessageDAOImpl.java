package net.ddns.eeitdemo.eeit106team01.chat.model.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageDAO;

@Repository
public class RegionMessageDAOImpl implements RegionMessageDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public RegionMessageBean findByPrimaryKey(int id) {
		return this.getSession().get(RegionMessageBean.class, id);
	}

	@Override
	public List<RegionMessageBean> findAll() {
		return this.getSession().createQuery("from RegionMessageBean", RegionMessageBean.class)
				.getResultList();
	}

	@Override
	public RegionMessageBean insert(RegionMessageBean regionMessageBean) {
		if (regionMessageBean != null) {
			Integer id = (Integer) getSession().save(regionMessageBean);
			return findByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public RegionMessageBean update(RegionMessageBean regionMessageBean) {
		if (regionMessageBean != null) {
			getSession().update(regionMessageBean);
			return findByPrimaryKey(regionMessageBean.getId());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionMessageBean> queryList(String hql, Integer startPosition, Integer maxResult) {
		Query query = getSession().createQuery(hql, RegionMessageBean.class);
		if ((startPosition != null) && (maxResult != null)) {
			query.setFirstResult(startPosition);
			query.setMaxResults(maxResult);
		}
		return query.getResultList();
	}

}
