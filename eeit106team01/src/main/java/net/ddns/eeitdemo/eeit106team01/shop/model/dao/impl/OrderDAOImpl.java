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

import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.OrderDAO;

@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OrderBean insertOrder(OrderBean orderBean) {
		if (orderBean != null) {
			getSession().save(orderBean);
			return this.findOrderByPrimaryKey(orderBean.getId());
		}
		return null;
	}

	@Override
	public OrderBean updateOrder(OrderBean orderBean) {
		if (orderBean != null) {
			getSession().update(orderBean);
			return this.findOrderByPrimaryKey(orderBean.getId());
		}
		return null;
	}

	@Override
	public OrderBean findOrderByPrimaryKey(Long id) {
		if (id != null) {
			OrderBean result = getSession().get(OrderBean.class, id);
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<OrderBean> findOrders() {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<OrderBean> criteriaQuery = criteriaBuilder.createQuery(OrderBean.class);
		Root<OrderBean> root = criteriaQuery.from(OrderBean.class);
		criteriaQuery.select(root);

		Query<OrderBean> query = getSession().createQuery(criteriaQuery);
		List<OrderBean> result = query.getResultList();
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public OrderDetailBean insertOrderDetail(OrderDetailBean orderDetailBean) {
		if (orderDetailBean != null) {
			getSession().save(orderDetailBean);
			return findOrderDetailByPrimaryKey(orderDetailBean.getId());
		}
		return null;
	}

	@Override
	public OrderDetailBean updateOrderDetail(OrderDetailBean orderDetailBean) {
		if (orderDetailBean != null) {
			getSession().update(orderDetailBean);
			return findOrderDetailByPrimaryKey(orderDetailBean.getId());
		}
		return null;
	}

	@Override
	public OrderDetailBean findOrderDetailByPrimaryKey(Long id) {
		if (id != null) {
			OrderDetailBean result = getSession().get(OrderDetailBean.class, id);
			if (result != null) {
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<OrderDetailBean> findOrderDetails() {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<OrderDetailBean> criteriaQuery = criteriaBuilder.createQuery(OrderDetailBean.class);
		Root<OrderDetailBean> root = criteriaQuery.from(OrderDetailBean.class);
		criteriaQuery.select(root);

		Query<OrderDetailBean> query = getSession().createQuery(criteriaQuery);
		List<OrderDetailBean> result = query.getResultList();
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public ReviewBean insertReview(ReviewBean reviewBean) {
		if (reviewBean != null) {
			getSession().save(reviewBean);
			return this.findReviewByPrimaryKey(reviewBean.getId());
		}
		return null;
	}

	@Override
	public ReviewBean updateReview(ReviewBean reviewBean) {
		if (reviewBean != null) {
			getSession().update(reviewBean);
			return findReviewByPrimaryKey(reviewBean.getId());
		}
		return null;
	}

	@Override
	public ReviewBean findReviewByPrimaryKey(Long id) {
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

}
