package net.ddns.eeitdemo.eeit106team01.forum.model;

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

}
