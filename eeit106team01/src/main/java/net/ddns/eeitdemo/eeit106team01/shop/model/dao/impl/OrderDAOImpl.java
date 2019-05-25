package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;

@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// Order
	@Override
	public PurchaseBean insertOrder(PurchaseBean orderBean) {
		if (orderBean != null) {
			getSession().save(orderBean);
			return this.findOrderByOrderId(orderBean.getId());
		}
		return null;
	}

	@Override
	public PurchaseBean updateOrder(PurchaseBean orderBean) {
		if (orderBean != null) {
			try {
				getSession().update(orderBean);
			} catch (HibernateException e) {
				e.printStackTrace();
				return null;
			}
			return this.findOrderByOrderId(orderBean.getId());
		}
		return null;
	}

	@Override
	public PurchaseBean findOrderByOrderId(Long id) {
		if (id != null) {
			try {
				PurchaseBean result = getSession().get(PurchaseBean.class, id);
				if (result != null) {
					return result;
				}
			} catch (HibernateException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findOrdersByCreateTime(Integer day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseBean> findOrdersByUpdateTime(Integer day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseBean> findOrdersByDeliverStatus(String deliverStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseBean> findOrdersByDeliverType(String deliverType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseBean> findOrdersByPayStatus(String payStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseBean findOrdersByProductTotalPrice(Integer price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseBean> findOrdersByProductTotalPriceLower(Integer price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseBean> findOrdersByProductTotalPriceHigher(Integer price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseBean> findOrdersByMemberId(Long id) {
		if (id != null) {
			CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
			CriteriaQuery<PurchaseBean> criteriaQuery = criteriaBuilder.createQuery(PurchaseBean.class);
			Root<PurchaseBean> order = criteriaQuery.from(PurchaseBean.class);
			criteriaQuery.select(order).where(criteriaBuilder.equal(order.get("memberBeanTest"), id));

			Query<PurchaseBean> query = getSession().createQuery(criteriaQuery);
			List<PurchaseBean> result = query.getResultList();
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findOrders() {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<PurchaseBean> criteriaQuery = criteriaBuilder.createQuery(PurchaseBean.class);
		Root<PurchaseBean> root = criteriaQuery.from(PurchaseBean.class);
		criteriaQuery.select(root);

		Query<PurchaseBean> query = getSession().createQuery(criteriaQuery);
		List<PurchaseBean> result = query.getResultList();
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	// OrderDetail
	@Override
	public PurchaseListBean insertOrderDetail(PurchaseListBean orderDetailBeans) {
		if (orderDetailBeans != null) {
			getSession().save(orderDetailBeans);
			return findOrderDetailByOrderDetailId(orderDetailBeans.getId());
		}
		return null;
	}

	@Override
	public PurchaseListBean updateOrderDetail(PurchaseListBean orderDetailBean) {
		if (orderDetailBean != null) {
			getSession().update(orderDetailBean);
			return findOrderDetailByOrderDetailId(orderDetailBean.getId());
		}
		return null;
	}

	@Override
	public PurchaseListBean findOrderDetailByOrderDetailId(Long id) {
		if (id != null) {
			PurchaseListBean result = getSession().get(PurchaseListBean.class, id);
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseListBean> findOrderDetailsByOrderId(Long id) {
		if (id != null) {
			CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
			CriteriaQuery<PurchaseListBean> criteriaQuery = criteriaBuilder.createQuery(PurchaseListBean.class);
			Root<PurchaseListBean> oderDetail = criteriaQuery.from(PurchaseListBean.class);
			criteriaQuery.select(oderDetail).where(criteriaBuilder.equal(oderDetail.get("orderBean"), id));

			Query<PurchaseListBean> query = getSession().createQuery(criteriaQuery);
			List<PurchaseListBean> result = query.getResultList();
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseListBean> findOrderDetails() {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<PurchaseListBean> criteriaQuery = criteriaBuilder.createQuery(PurchaseListBean.class);
		Root<PurchaseListBean> root = criteriaQuery.from(PurchaseListBean.class);
		criteriaQuery.select(root);

		Query<PurchaseListBean> query = getSession().createQuery(criteriaQuery);
		List<PurchaseListBean> result = query.getResultList();
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public PurchaseListBean findOrderDetailBySerialNumber(String serialNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseListBean findOrderDetailByPrice(Integer price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseListBean> findOrderDetailsByPriceLower(Integer price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseListBean> findOrderDetailsByPriceHigher(Integer price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseListBean> findOrderDetailsByProductId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	// Review
	@Override
	public ReviewBean insertReview(ReviewBean reviewBean) {
		if (reviewBean != null) {
			getSession().save(reviewBean);
			return this.findReviewByReviewId(reviewBean.getId());
		}
		return null;
	}

	@Override
	public ReviewBean updateReview(ReviewBean reviewBean) {
		if (reviewBean != null) {
			getSession().update(reviewBean);
			return findReviewByReviewId(reviewBean.getId());
		}
		return null;
	}

	@Override
	public ReviewBean findReviewByReviewId(Long id) {
		if (id != null) {
			ReviewBean result = getSession().get(ReviewBean.class, id);
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<ReviewBean> findReviews() {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<ReviewBean> criteriaQuery = criteriaBuilder.createQuery(ReviewBean.class);
		Root<ReviewBean> root = criteriaQuery.from(ReviewBean.class);
		criteriaQuery.select(root);

		Query<ReviewBean> query = getSession().createQuery(criteriaQuery);
		List<ReviewBean> result = query.getResultList();
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<ReviewBean> findReviewsByCreateTime(Integer day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByUpdateTime(Integer day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByImageExistence(Boolean truefalse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByRating(Double rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByRatingLower(Double rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByRatingHigher(Double rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByMemberId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByProductId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
