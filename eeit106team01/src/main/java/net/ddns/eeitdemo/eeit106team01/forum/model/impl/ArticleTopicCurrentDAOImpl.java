package net.ddns.eeitdemo.eeit106team01.forum.model.impl;

import java.util.List;

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
	public List<ArticleTopicCurrentBean> findAll() {
		return getSession().createQuery("from ArticleTopicCurrentBean", ArticleTopicCurrentBean.class).getResultList();
	}

	@Override
	public ArticleTopicCurrentBean insert(ArticleTopicCurrentBean bean) {
		if (bean != null) {
			Integer id = (Integer) getSession().save(bean);
			System.out.println("this is daoImpl, id: "+id);
			return findByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public ArticleTopicCurrentBean update(ArticleTopicCurrentBean bean) {
		ArticleTopicCurrentBean findOne = findByPrimaryKey(bean.getId());
		if (findOne != null) {
			if (bean.getTopicHeader() != null) {
				findOne.setTopicHeader(bean.getTopicHeader());
			}
			if (bean.getTopicType() != null) {
				findOne.setTopicType(bean.getTopicType());
			}
			if (bean.getTopicRegion() != null) {
				findOne.setTopicRegion(bean.getTopicRegion());
			}
			if (bean.getTopicLikeNum() != null) {
				findOne.setTopicLikeNum(bean.getTopicLikeNum());
			}
			if (bean.getContentReplyNum() != null) {
				findOne.setContentReplyNum(bean.getContentReplyNum());
			}
			if (bean.getTopicCreateTime() != null) {
				findOne.setTopicCreateTime(bean.getTopicCreateTime());
			}
			if (bean.getTopicUpdateTime() != null) {
				findOne.setTopicUpdateTime(bean.getTopicUpdateTime());
			}
			if (bean.getTopicStatus() != null) {
				findOne.setTopicStatus(bean.getTopicStatus());
			}
			if (bean.getAccidentTime() != null) {
				findOne.setAccidentTime(bean.getAccidentTime());
			}
			if (bean.getAccidentLocation() != null) {
				findOne.setAccidentLocation(bean.getAccidentLocation());
			}
			if (bean.getAccidentLocationLongitude() != null) {
				findOne.setAccidentLocationLongitude(bean.getAccidentLocationLongitude());
			}
			if (bean.getAccidentLocationLatitude() != null) {
				findOne.setAccidentLocationLatitude(bean.getAccidentLocationLatitude());
			}
			if (bean.getTopicContent() != null) {
				findOne.setTopicContent(bean.getTopicContent());
			}
			if (bean.getTopicContentUpdateTime() != null) {
				findOne.setTopicContentUpdateTime(bean.getTopicContentUpdateTime());
			}
			if (bean.getUpdateMessage() != null) {
				findOne.setUpdateMessage(bean.getUpdateMessage());
			}
			if (bean.getVideoBean()!=null) {
				findOne.setVideoBean(bean.getVideoBean());
			}
			return findOne;
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
