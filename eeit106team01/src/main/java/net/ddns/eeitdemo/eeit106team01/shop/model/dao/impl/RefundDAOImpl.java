package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.RefundDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@Repository
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
	public List<RefundBean> findRefundByProcessStatus(String processStatus) {
		if (NullChecker.isEmpty(processStatus) == false) {
			try {
				this.refundBean = this.getSession()
						.createQuery("from RefundBean where processStatus= :processStatus", RefundBean.class)
						.setParameter("processStatus", processStatus).getResultList();
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
	public List<RefundBean> findAllRefund() {
		try {
			this.refundBean = this.getSession().createQuery("from RefundBean", RefundBean.class).getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.refundBean != null && this.refundBean.size() > 0) {
			return this.refundBean;
		}
		return null;
	}

	@Override
	public RefundListBean insertRefundList(RefundListBean refundListBean) {
		if (refundListBean.isNotNull()) {
			try {
				this.getSession().save(refundListBean);
				Long id = refundListBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findRefundListByRefundListId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public RefundListBean findRefundListByRefundListId(Long refundListId) {
		if (refundListId != null && refundListId.longValue() > 0L) {
			try {
				RefundListBean result = this.getSession().get(RefundListBean.class, refundListId);
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
	public RefundListBean findRefundListByPurchaseListId(Long purchaseListId) {
		if (purchaseListId != null && purchaseListId.longValue() > 0L) {
			try {
				RefundListBean result = this.getSession()
						.createQuery("from RefundListBean where PurchaseListID= :PurchaseListID", RefundListBean.class)
						.setParameter("PurchaseListID", purchaseListId).getSingleResult();
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
	public List<RefundListBean> findRefundListByRefundId(Long refundId) {
		if (refundId != null && refundId.longValue() > 0L) {
			try {
				this.refundListBean = this.getSession()
						.createQuery("from RefundListBean where RefundID = :RefundID", RefundListBean.class)
						.setParameter("RefundID", refundId).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.refundListBean != null && this.refundListBean.size() > 0) {
				return this.refundListBean;
			}
		}
		return null;
	}

	@Override
	public List<RefundListBean> findAllRefundList() {
		try {
			this.refundListBean = this.getSession().createQuery("from RefundListBean", RefundListBean.class)
					.getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.refundListBean != null && this.refundListBean.size() > 0) {
			return this.refundListBean;
		} else {
			return null;
		}
	}

}
