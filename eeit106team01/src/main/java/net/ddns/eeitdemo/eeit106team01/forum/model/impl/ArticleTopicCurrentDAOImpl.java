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
	public List<ArticleTopicCurrentBean> findByLastRange(int startPosition, int maxResult, String topicType, String orderColumn, String likeTopicHeader) {
		String queryString = "";
		if("topicUpdateTime".equals(orderColumn)) {
			if(likeTopicHeader == null) {
				queryString = "from ArticleTopicCurrentBean where (topicType = :topicType) and (topicStatus = 'normal') order by topicUpdateTime desc";
			} else {
				queryString = "from ArticleTopicCurrentBean where (topicType = :topicType) and (topicStatus = 'normal') and (topicHeader like '%"+ likeTopicHeader +"%') order by topicUpdateTime desc";
			}
		}else if("topicLikeNum".equals(orderColumn)) {
			if(likeTopicHeader == null) {
				queryString = "from ArticleTopicCurrentBean where (topicType = :topicType) and (topicStatus = 'normal') order by topicLikeNum desc";
			} else {
				queryString = "from ArticleTopicCurrentBean where (topicType = :topicType) and (topicStatus = 'normal') and (topicHeader like '%"+ likeTopicHeader +"%') order by topicLikeNum desc";
			}
		}else {
			System.out.println("OrderColumn Error!");
		}
		Query query = getSession().createQuery(queryString, ArticleTopicCurrentBean.class);
		query.setParameter("topicType", topicType);
		query.setFirstResult(startPosition - 1);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleTopicCurrentBean> findByCoordinateRange(Double lowerLatitude, Double upperLatitude,
			Double lowerLongitude, Double upperLongitude) {
		String queryString = "from ArticleTopicCurrentBean where (accidentLocationLatitude between :lowerLat and :upperLat) and (accidentLocationLongitude between :lowerLong and :upperLong) and (topicType = 'shareTopic') and (topicStatus = 'normal')";
		Query query = getSession().createQuery(queryString, ArticleTopicCurrentBean.class);
		query.setParameter("lowerLat", lowerLatitude);
		query.setParameter("upperLat", upperLatitude);
		query.setParameter("lowerLong", lowerLongitude);
		query.setParameter("upperLong", upperLongitude);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleTopicCurrentBean> findLikeTopicHeader(String likeTopicHeader) {
		String queryString = "from ArticleTopicCurrentBean where topicHeader like '%"+ likeTopicHeader +"%'";
		Query query = getSession().createQuery(queryString, ArticleTopicCurrentBean.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAutocompleteByTopicHeader(String inputString) {
		String queryString = "select topicHeader from ArticleTopicCurrentBean where topicHeader like '%"+ inputString +"%'";
		Query query = getSession().createQuery(queryString);
		return query.getResultList();
	}
	
	@Override
	public Long findTopicNum(String topicType, String likeTopicHeader) {
		String queryString = "";
		if(likeTopicHeader == null) {
			queryString = "select count(*) from ArticleTopicCurrentBean where (topicType = :topicType) and (topicStatus = 'normal')";
		} else {
			queryString = "select count(*) from ArticleTopicCurrentBean where (topicType = :topicType) and (topicStatus = 'normal') and (topicHeader like '%"+ likeTopicHeader +"%')";
		}
		
		Query query = getSession().createQuery(queryString);
		query.setParameter("topicType", topicType);
		return (Long) query.getSingleResult();
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
