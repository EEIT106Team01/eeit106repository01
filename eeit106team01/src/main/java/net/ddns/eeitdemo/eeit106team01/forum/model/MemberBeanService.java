package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberBeanService {
	@Autowired
	private SessionFactory sessionFactory;

	public MemberBean login(int id) {
		return sessionFactory.getCurrentSession().get(MemberBean.class, id);
	}

	public MemberBean findByPrimaryKey(int id) {
		return sessionFactory.getCurrentSession().get(MemberBean.class, id);
	}

	public MemberBean findByPrimaryKeyAsProxy(int id) {
		return sessionFactory.getCurrentSession().load(MemberBean.class, id);
	}

	@SuppressWarnings("unchecked")
	public MemberBean findByName(String name) {
		MemberBean result = null;
		String hql = "from MemberBean mb where mb.name='" + name + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql, MemberBean.class);
		query.setMaxResults(1);
		List<MemberBean> results = (List<MemberBean>) query.getResultList();
		if (results != null && !results.isEmpty()) {
			result = results.get(0);
		}
		return result;
	}

}
