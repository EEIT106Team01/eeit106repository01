package net.ddns.eeitdemo.eeit106team01.forum.model.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentDAO;

@Repository
public class ArticleContentCurrentDAOImpl implements ArticleContentCurrentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ArticleContentCurrentBean findByPrimaryKey(int id) {
		return getSession().get(ArticleContentCurrentBean.class, id);
	}
	
	@Override
	public ArticleContentCurrentBean findByPrimaryKeyAsProxy(int id) {
		return getSession().load(ArticleContentCurrentBean.class, id);
	}

	@Override
	public List<ArticleContentCurrentBean> findAll() {
		return getSession().createQuery("from ArticleContentCurrentBean", ArticleContentCurrentBean.class)
				.getResultList();
	}

	@Override
	public ArticleContentCurrentBean insert(ArticleContentCurrentBean bean) {
		if (bean != null) {
			Integer id = (Integer) getSession().save(bean);
			return findByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public ArticleContentCurrentBean update(ArticleContentCurrentBean bean) {
		if (bean != null) {
			getSession().update(bean);
			return findByPrimaryKey(bean.getId());
		}
		return null;
	}

	@Override
	public List<ArticleContentCurrentBean> queryList(String hql, int startPosition, int maxResult) {
		Query<ArticleContentCurrentBean> query = getSession().createQuery(hql, ArticleContentCurrentBean.class);
		if ((startPosition >= 0) && (maxResult != 0)) {
			query.setFirstResult(startPosition);
			query.setMaxResults(maxResult);
		}
		return query.list();
	}

	@Override
	public boolean delete(int id) {
		ArticleContentCurrentBean findOne = findByPrimaryKey(id);
		if (findOne != null) {
			getSession().delete(findOne);
			return true;
		}
		return false;
	}

}
