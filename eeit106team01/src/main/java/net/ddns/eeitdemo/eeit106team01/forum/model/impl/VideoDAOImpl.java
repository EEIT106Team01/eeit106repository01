package net.ddns.eeitdemo.eeit106team01.forum.model.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.forum.model.VideoBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.VideoDAO;

@Repository
public class VideoDAOImpl implements VideoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public VideoBean findByPrimaryKey(int id) {
		return sessionFactory.getCurrentSession().find(VideoBean.class, id);
	}

	@Override
	public List<VideoBean> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from VideoBean", VideoBean.class).list();
	}

	@Override
	public VideoBean insert(VideoBean videoBean) {
		Integer id = (Integer) sessionFactory.getCurrentSession().save(videoBean);
		return this.findByPrimaryKey(id);
	}

	@Override
	public VideoBean update(VideoBean videoBean) {
		sessionFactory.getCurrentSession().update(videoBean);
		return this.findByPrimaryKey(videoBean.getId());
	}

	@Override
	public boolean delete(int id) {
		sessionFactory.getCurrentSession().delete(this.findByPrimaryKey(id));
		VideoBean videoBean = this.findByPrimaryKey(id);
		if (videoBean == null) {
			return true;
		}
		return false;
	}

}
