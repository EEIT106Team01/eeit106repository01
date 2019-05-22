package net.ddns.eeitdemo.eeit106team01.forum.model.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentDAO;

@Repository
public class ArticleTopicCurrentDAOImpl implements ArticleTopicCurrentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ArticleTopicCurrentBean findByPrimaryKey(int id) {
		return getSession().get(ArticleTopicCurrentBean.class, id);
	}
	
	@Override
	public ArticleTopicCurrentBean findByPrimaryKeyAsProxy(int id) {
		return getSession().load(ArticleTopicCurrentBean.class, id);
	}

	@Override
	public List<ArticleTopicCurrentBean> findAll() {
		return getSession().createQuery("from ArticleTopicCurrentBean", ArticleTopicCurrentBean.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleTopicCurrentBean> findByLastRange(String queryString, int startPosition, int maxResult) {
		Query query = getSession().createQuery(queryString, ArticleTopicCurrentBean.class);
		query.setFirstResult(startPosition - 1);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleTopicCurrentBean> findByCoordinateRange(Double lowerLatitude, Double upperLatitude,
			Double lowerLongitude, Double upperLongitude) {
		Query query = getSession().createQuery("from ArticleTopicCurrentBean where accidentLocationLatitude between :lowerLat and :upperLat and accidentLocationLongitude between :lowerLong and :upperLong", ArticleTopicCurrentBean.class);
		query.setParameter("lowerLat", lowerLatitude);
		query.setParameter("upperLat", upperLatitude);
		query.setParameter("lowerLong", lowerLongitude);
		query.setParameter("upperLong", upperLongitude);
		return query.getResultList();
	}

	@Override
	public ArticleTopicCurrentBean insert(ArticleTopicCurrentBean bean) {
		if (bean != null) {
			Integer id = (Integer) getSession().save(bean);
			return findByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public ArticleTopicCurrentBean update(ArticleTopicCurrentBean bean) {
		if (bean != null) {
			getSession().update(bean);
			return findByPrimaryKey(bean.getId());
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		ArticleTopicCurrentBean findOne = findByPrimaryKey(id);
		if (findOne != null) {
			getSession().delete(findOne);
			return true;
		}
		return false;
	}

}
