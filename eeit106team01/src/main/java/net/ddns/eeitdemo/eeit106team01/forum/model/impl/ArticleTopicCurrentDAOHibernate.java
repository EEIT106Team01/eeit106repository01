package net.ddns.eeitdemo.eeit106team01.forum.model.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentDAO;

@Repository
public class ArticleTopicCurrentDAOHibernate implements ArticleTopicCurrentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public ArticleTopicCurrentBean findByPrimaryKey(int id) {
		return sessionFactory
				.getCurrentSession()
				.get(ArticleTopicCurrentBean.class, id);
	}

	@Override
	public List<ArticleTopicCurrentBean> findAll() {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from ArticleTopicCurrentBean", ArticleTopicCurrentBean.class)
				.getResultList();
	}

	@Override
	public ArticleTopicCurrentBean insert(ArticleTopicCurrentBean bean) {
		
		return null;
	}

	@Override
	public ArticleTopicCurrentBean update(ArticleTopicCurrentBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
