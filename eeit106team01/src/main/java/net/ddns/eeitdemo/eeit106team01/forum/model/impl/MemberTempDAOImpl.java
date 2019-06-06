package net.ddns.eeitdemo.eeit106team01.forum.model.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberTempBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.MemberTempDAO;

@Repository
public class MemberTempDAOImpl implements MemberTempDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public MemberTempBean findByPrimaryKey(int id) {
		return getSession().get(MemberTempBean.class, id);
	}

	@Override
	public MemberTempBean findByPrimaryKeyAsProxy(int id) {
		return getSession().load(MemberTempBean.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MemberTempBean findByName(String name) {
		System.out.println("DAO name: " + name);
		MemberTempBean result = null;
		String hql = "from MemberTempBean mb where mb.name='" + name + "'";
		Query query = getSession().createQuery(hql, MemberTempBean.class);
		query.setMaxResults(1);
		List<MemberTempBean> results = (List<MemberTempBean>) query.getResultList();
		if (results != null && !results.isEmpty()) {
			result = results.get(0);
		}
		return result;
	}

	@Override
	public List<MemberTempBean> findAll() {
		return getSession().createQuery("from ArticleTopicCurrentBean", MemberTempBean.class).getResultList();
	}

	@Override
	public MemberTempBean insert(MemberTempBean bean) {
		if (bean != null) {
			Integer id = (Integer) getSession().save(bean);
			return findByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public MemberTempBean update(MemberTempBean bean) {
		if (bean != null) {
			getSession().update(bean);
			return findByPrimaryKey(bean.getId());
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		MemberTempBean findOne = findByPrimaryKey(id);
		if (findOne != null) {
			getSession().delete(findOne);
			return true;
		}
		return false;
	}

}
