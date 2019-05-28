package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;

@Repository
@Transactional
public class RefundDAOImpl implements RefundDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private List<RefundBean> refundBean = new ArrayList<RefundBean>();
	private List<RefundListBean> refundListBean = new ArrayList<RefundListBean>();

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public RefundBean insertRefund(RefundBean refundBean) {
		if (refundBean.isNotNull()) {
			try {
				this.getSession().save(refundBean);
				Long id = refundBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findRefundByRefundId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public RefundBean updateRefund(RefundBean refundBean) {
		if (refundBean.isNotNull()) {
			try {
				this.getSession().update(refundBean);
				Long id = refundBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findRefundByRefundId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public RefundBean findRefundByRefundId(Long refundId) {
		if (refundId != null && refundId.longValue() > 0L) {
			try {
				RefundBean result = this.getSession().get(RefundBean.class, refundId);
				if (result != null) {
					return result;
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<RefundBean> findRefundByMemberId(Long memberId) {
		if (memberId != null && memberId.longValue() > 0L) {
			try {
				this.refundBean = this.getSession()
						.createQuery("from RefundBean where MemberID= :MemberID", RefundBean.class)
						.setParameter("MemberID", memberId).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.refundBean != null && this.refundBean.size() > 0) {
				return this.refundBean;
			}
		}
		return null;
	}

	@Override
	public List<RefundBean> findRefundByProcessStatus(Date startDay, Date endDay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RefundBean> findAllRefund() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RefundListBean insertRefundList(RefundListBean refundDetailBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RefundListBean findRefundListByRefundListId(Long refundListId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RefundListBean> findRefundListByRefundId(Long refundId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RefundListBean> findRefundListByPurchaseId(Long purchaseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RefundListBean> findAllRefundList() {
		// TODO Auto-generated method stub
		return null;
	}

}
