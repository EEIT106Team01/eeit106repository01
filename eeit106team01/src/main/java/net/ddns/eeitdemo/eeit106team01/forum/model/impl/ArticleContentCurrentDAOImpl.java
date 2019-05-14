package net.ddns.eeitdemo.eeit106team01.forum.model.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		ArticleContentCurrentBean findOne = findByPrimaryKey(bean.getId());
		if (findOne != null) {
			if (bean.getContentLikeNum() != null) {
				findOne.setContentLikeNum(bean.getContentLikeNum());
			}
			if (bean.getContentReplyNum() != null) {
				findOne.setContentReplyNum(bean.getContentReplyNum());
			}
			if (bean.getContentCreateTime() != null) {
				findOne.setContentCreateTime(bean.getContentCreateTime());
			}
			if (bean.getContentUpdateTime() != null) {
				findOne.setContentUpdateTime(bean.getContentUpdateTime());
			}
			if (bean.getContentStatus() != null) {
				findOne.setContentStatus(bean.getContentStatus());
			}
			if (bean.getContentContent() != null) {
				findOne.setContentContent(bean.getContentContent());
			}
			if (bean.getUpdateMessage() != null) {
				findOne.setUpdateMessage(bean.getUpdateMessage());
			}
			if (bean.getArticleTopicCurrent() != null) {
				findOne.setArticleTopicCurrent(bean.getArticleTopicCurrent());
			}
			if (bean.getReply() != null) {
				findOne.setReply(bean.getReply());
			}
			if (bean.getVideoBean() != null) {
				findOne.setVideoBean(bean.getVideoBean());
			}
			return findOne;
		}
		return null;
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
