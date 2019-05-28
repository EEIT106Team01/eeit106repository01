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
		try {
			this.getSession().save(member);
			Long id = member.getId();
			if (id != null && id.longValue() > 0L) {
				return this.findByMemberId(member.getId());
			} else {
				return null;
			}
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
	}

	@Override
	public Member findByMemberId(Long memberId) {
		if (memberId != null && memberId.longValue() > 0L) {
			try {
				Member result = this.getSession().find(Member.class, memberId);
				if (result != null) {
					return result;
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

}
