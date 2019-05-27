package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;

@Repository
@Transactional
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Member insertMember(Member member) {
		if (member != null) {
			try {
				getSession().save(member);
				return this.findByMemberId(member.getId());
			} catch (HibernateException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@Override
	public Member findByMemberId(Long id) {
		if (id != null) {
			try {
				Member result = getSession().find(Member.class, id);
				return result;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
