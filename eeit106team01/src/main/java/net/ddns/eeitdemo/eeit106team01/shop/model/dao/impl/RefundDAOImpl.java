package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;

@Repository
public class RefundDAOImpl implements RefundDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public RefundBean insertRefund(RefundBean refundBean) {
		if (refundBean != null) {
			getSession().save(refundBean);
			return this.findRefundByPrimaryKey(refundBean.getId());
		}
		return null;
	}

	@Override
	public RefundBean updateRefund(RefundBean refundBean) {
		if (refundBean != null) {
			getSession().update(refundBean);
			return findRefundByPrimaryKey(refundBean.getId());
		}
		return null;
	}

	@Override
	public RefundBean findRefundByPrimaryKey(Long id) {
		if (id != null) {
			RefundBean result = getSession().get(RefundBean.class, id);
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<RefundBean> findRefundsByMemberId(Long id) {
		if (id != null) {
			CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
			CriteriaQuery<RefundBean> criteriaQuery = criteriaBuilder.createQuery(RefundBean.class);
			Root<RefundBean> refund = criteriaQuery.from(RefundBean.class);
			criteriaQuery.select(refund).where(criteriaBuilder.equal(refund.get("memberBeanTest"), id));

			Query<RefundBean> query = getSession().createQuery(criteriaQuery);
			List<RefundBean> result = query.getResultList();
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<RefundBean> findRefunds() {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<RefundBean> criteriaQuery = criteriaBuilder.createQuery(RefundBean.class);
		Root<RefundBean> root = criteriaQuery.from(RefundBean.class);
		criteriaQuery.select(root);

		Query<RefundBean> query = getSession().createQuery(criteriaQuery);
		List<RefundBean> result = query.getResultList();
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public RefundListBean insertRefundDetail(RefundListBean refundDetailBean) {
		if (refundDetailBean != null) {
			getSession().save(refundDetailBean);
			return this.findRefundDetailByPrimaryKey(refundDetailBean.getId());
		}
		return null;
	}

	@Override
	public RefundListBean updateRefundDetail(RefundListBean refundDetailBean) {
		if (refundDetailBean != null) {
			getSession().update(refundDetailBean);
			return this.findRefundDetailByPrimaryKey(refundDetailBean.getId());
		}
		return null;
	}

	@Override
	public RefundListBean findRefundDetailByPrimaryKey(Long id) {
		if (id != null) {
			RefundListBean result = getSession().get(RefundListBean.class, id);
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<RefundListBean> findRefundDetailsByRefundId(Long id) {
		if (id != null) {
			CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
			CriteriaQuery<RefundListBean> criteriaQuery = criteriaBuilder.createQuery(RefundListBean.class);
			Root<RefundListBean> refundDetail = criteriaQuery.from(RefundListBean.class);
			criteriaQuery.select(refundDetail).where(criteriaBuilder.equal(refundDetail.get("refundBean"), id));

			Query<RefundListBean> query = getSession().createQuery(criteriaQuery);
			List<RefundListBean> result = query.getResultList();
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<RefundListBean> findRefundDetails() {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<RefundListBean> criteriaQuery = criteriaBuilder.createQuery(RefundListBean.class);
		Root<RefundListBean> root = criteriaQuery.from(RefundListBean.class);
		criteriaQuery.select(root);

		Query<RefundListBean> query = getSession().createQuery(criteriaQuery);
		List<RefundListBean> result = query.getResultList();
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

}
