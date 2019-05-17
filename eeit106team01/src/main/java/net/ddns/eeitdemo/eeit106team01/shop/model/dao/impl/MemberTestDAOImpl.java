package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.MemberBeanTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberTestDAO;

@Repository
@Transactional
public class MemberTestDAOImpl implements MemberTestDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public MemberBeanTest insert(MemberBeanTest memberBean) {
		if (memberBean != null) {
			getSession().save(memberBean);
			return this.findByPrimaryKey(memberBean.getId());
		}
		return null;
	}

	@Override
	public MemberBeanTest update(MemberBeanTest memberBean) {
		if (memberBean != null) {
			getSession().update(memberBean);
			return this.findByPrimaryKey(memberBean.getId());
		}
		return null;
	}

	@Override
	public MemberBeanTest findByPrimaryKey(Long id) {
		if (id != null) {
			MemberBeanTest result = getSession().get(MemberBeanTest.class, id);
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<MemberBeanTest> findMembers() {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<MemberBeanTest> criteriaQuery = criteriaBuilder.createQuery(MemberBeanTest.class);
		Root<MemberBeanTest> root = criteriaQuery.from(MemberBeanTest.class);
		criteriaQuery.select(root);

		Query<MemberBeanTest> query = getSession().createQuery(criteriaQuery);
		List<MemberBeanTest> result = query.getResultList();
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

}
