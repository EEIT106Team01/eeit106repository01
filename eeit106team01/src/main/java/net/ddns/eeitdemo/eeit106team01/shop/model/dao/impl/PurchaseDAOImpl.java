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

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.PurchaseDAO;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@Repository
@Transactional
public class PurchaseDAOImpl implements PurchaseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private List<PurchaseBean> purchase = new ArrayList<PurchaseBean>();
	private List<PurchaseListBean> purchaseList = new ArrayList<PurchaseListBean>();
	private List<ReviewBean> review = new ArrayList<ReviewBean>();

	// Purchase
	@Override
	public PurchaseBean insertPurchase(PurchaseBean purchaseBean) {
		if (purchaseBean.isNotNull()) {
			try {
				this.getSession().save(purchaseBean);
				Long id = purchaseBean.getId();
				if (id != null && id.longValue() > 0) {
					return this.findPurchaseByPurchaseId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public PurchaseBean updatePurchase(PurchaseBean purchaseBean) {
		if (purchaseBean.isNotNull()) {
			try {
				this.getSession().update(purchaseBean);
				Long id = purchaseBean.getId();
				if (id != null && id.longValue() > 0) {
					return this.findPurchaseByPurchaseId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public PurchaseBean findPurchaseByPurchaseId(Long id) {
		if (id != null && id.longValue() > 0) {
			try {
				PurchaseBean result = this.getSession().get(PurchaseBean.class, id);
				if (result != null) {
					return result;
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	/**
	 * @param startDay = day start
	 * @param endDay   = day end
	 * @return Between startDay and endDay
	 */
	@Override
	public List<PurchaseBean> findPurchaseByTimeDayBetween(Date startDay, Date endDay) {
		if (startDay != null && endDay != null && startDay.equals(endDay) == false && startDay.compareTo(endDay) < 0) {
			try {
				this.purchase = this.getSession()
						.createQuery("from PurchaseBean where updatedTime BETWEEN :startDay AND :endDay",
								PurchaseBean.class)
						.setParameter("startDay", startDay).setParameter("endDay", endDay).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchase != null && this.purchase.size() > 0) {
				return this.purchase;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findPurchaseByDeliverStatus(String deliverStatus) {
		if (NullChecker.isEmpty(deliverStatus) == false) {
			try {
				this.purchase = this.getSession()
						.createQuery("from PurchaseBean where deliverStatus= :deliverStatus", PurchaseBean.class)
						.setParameter("deliverStatus", deliverStatus).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchase != null && this.purchase.size() > 0) {
				return this.purchase;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findPurchaseByDeliverType(String deliverType) {
		if (NullChecker.isEmpty(deliverType) == false) {
			try {
				this.purchase = this.getSession()
						.createQuery("from PurchaseBean where deliverType= :deliverType", PurchaseBean.class)
						.setParameter("deliverType", deliverType).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchase != null && this.purchase.size() > 0) {
				return this.purchase;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findPurchaseByPayStatus(String payStatus) {
		if (NullChecker.isEmpty(payStatus) == false) {
			try {
				this.purchase = this.getSession()
						.createQuery("from PurchaseBean where payStatus= :payStatus", PurchaseBean.class)
						.setParameter("payStatus", payStatus).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchase != null && this.purchase.size() > 0) {
				return this.purchase;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findPurchaseByProductTotalPrice(Integer productTotalPrice) {
		if (productTotalPrice != null && productTotalPrice.intValue() >= 0) {
			try {
				this.purchase = this.getSession()
						.createQuery("from PurchaseBean where productTotalPrice= :productTotalPrice",
								PurchaseBean.class)
						.setParameter("productTotalPrice", productTotalPrice).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchase != null && this.purchase.size() > 0) {
				return this.purchase;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findPurchaseByProductTotalPriceLower(Integer productTotalPrice) {
		if (productTotalPrice != null && productTotalPrice.intValue() >= 0) {
			try {
				this.purchase = this.getSession()
						.createQuery("from PurchaseBean where productTotalPrice<= :productTotalPrice",
								PurchaseBean.class)
						.setParameter("productTotalPrice", productTotalPrice).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchase != null && this.purchase.size() > 0) {
				return this.purchase;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findPurchaseByProductTotalPriceHigher(Integer productTotalPrice) {
		if (productTotalPrice != null && productTotalPrice.intValue() >= 0) {
			try {
				this.purchase = this.getSession()
						.createQuery("from PurchaseBean where productTotalPrice>= :productTotalPrice",
								PurchaseBean.class)
						.setParameter("productTotalPrice", productTotalPrice).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchase != null && this.purchase.size() > 0) {
				return this.purchase;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findPurchaseByMemberId(Long id) {
		if (id != null && id.longValue() > 0) {
			try {
				this.purchase = this.getSession()
						.createQuery("from PurchaseBean where MemberID= :MemberID", PurchaseBean.class)
						.setParameter("MemberID", id).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchase != null && this.purchase.size() > 0) {
				return this.purchase;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseBean> findAllPurchase() {
		try {
			this.purchase = this.getSession().createQuery("from PurchaseBean", PurchaseBean.class).getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.purchase != null && this.purchase.size() > 0) {
			return this.purchase;
		} else {
			return null;
		}
	}

	// Purchase List
	@Override
	public PurchaseListBean insertPurchaseList(PurchaseListBean purchaseListBean) {
		if (purchaseListBean.isNotNull()) {
			try {
				this.getSession().save(purchaseListBean);
				Long id = purchaseListBean.getId();
				if (id != null && id.longValue() > 0L) {
					return findPurchaseListByPurchaseListId(purchaseListBean.getId());
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public PurchaseListBean findPurchaseListByPurchaseListId(Long id) {
		if (id != null && id.longValue() > 0L) {
			try {
				return this.getSession().get(PurchaseListBean.class, id);
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<PurchaseListBean> findPurchaseListByPurchaseId(Long id) {
		if (id != null && id.longValue() > 0L) {
			try {
				this.purchaseList = this.getSession()
						.createQuery("from PurchaseListBean where PurchaseID= :PurchaseID", PurchaseListBean.class)
						.setParameter("PurchaseID", id).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchaseList != null && this.purchaseList.size() > 0) {
				return this.purchaseList;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseListBean> findAllPurchaseList() {
		try {
			this.purchaseList = this.getSession().createQuery("from PurchaseListBean", PurchaseListBean.class)
					.getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.purchaseList != null && this.purchaseList.size() > 0) {
			return this.purchaseList;
		} else {
			return null;
		}
	}

	@Override
	public PurchaseListBean findPurchaseListBySerialNumber(String serialNumber) {
		if (NullChecker.isEmpty(serialNumber) == false) {
			PurchaseListBean result = new PurchaseListBean();
			try {
				result = this.getSession()
						.createQuery("from PurchaseListBean where serialNumber= :serialNumber", PurchaseListBean.class)
						.setParameter("serialNumber", serialNumber).getSingleResult();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseListBean> findPurchaseListByPrice(Integer price) {
		if (price != null && price.intValue() >= 0) {
			try {
				this.purchaseList = this.getSession()
						.createQuery("from PurchaseListBean where price= :price", PurchaseListBean.class)
						.setParameter("price", price).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchaseList != null && this.purchaseList.size() > 0) {
				return this.purchaseList;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseListBean> findPurchaseListByPriceLower(Integer price) {
		if (price != null && price.intValue() >= 0) {
			try {
				this.purchaseList = this.getSession()
						.createQuery("from PurchaseListBean where price<= :price", PurchaseListBean.class)
						.setParameter("price", price).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchaseList != null && this.purchaseList.size() > 0) {
				return this.purchaseList;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseListBean> findPurchaseListByPriceHigher(Integer price) {
		if (price != null && price.intValue() >= 0) {
			try {
				this.purchaseList = this.getSession()
						.createQuery("from PurchaseListBean where price>= :price", PurchaseListBean.class)
						.setParameter("price", price).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchaseList != null && this.purchaseList.size() > 0) {
				return this.purchaseList;
			}
		}
		return null;
	}

	@Override
	public List<PurchaseListBean> findPurchaseListByProductId(Long id) {
		if (id != null && id.longValue() > 0) {
			try {
				this.purchaseList = this.getSession()
						.createQuery("from PurchaseListBean where ProductID>= :ProductID", PurchaseListBean.class)
						.setParameter("ProductID", id).getResultList();
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.purchaseList != null && this.purchaseList.size() > 0) {
				return this.purchaseList;
			}
		}
		return null;
	}

	// Review
	@Override
	public ReviewBean insertReview(ReviewBean reviewBean) {
		if (reviewBean.isNotNull()) {
			try {
				this.getSession().save(reviewBean);
				Long id = reviewBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findReviewByReviewId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public ReviewBean updateReview(ReviewBean reviewBean) {
		if (reviewBean.isNotNull()) {
			try {
				this.getSession().update(reviewBean);
				Long id = reviewBean.getId();
				if (id != null && id.longValue() > 0L) {
					return this.findReviewByReviewId(id);
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public ReviewBean findReviewByReviewId(Long id) {
		if (id != null && id.longValue() > 0L) {
			try {
				ReviewBean result = this.getSession().get(ReviewBean.class, id);
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
	public List<ReviewBean> findReviews() {
		try {
			this.review = this.getSession().createQuery("from ReviewBean", ReviewBean.class).getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.review != null && this.review.size() > 0) {
			return this.review;
		}
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByTimeDayBetween(Date startDay, Date endDay) {
		try {
			this.review = this.getSession()
					.createQuery("from ReviewBean where updatedTime BETWEEN :startDay AND :endDay", ReviewBean.class)
					.setParameter("startDay", startDay).setParameter("endDay", endDay).getResultList();
		} catch (HibernateException e) {
			throw new HibernateException(e.getMessage());
		}
		if (this.review != null && this.review.size() > 0) {
			return this.review;
		}
		return null;
	}

	@Override
	public List<ReviewBean> findReviewsByImageExistence(Boolean truefalse) {
		if (truefalse != null) {
			try {
				if (truefalse == true) {
					this.review = this.getSession()
							.createQuery("from ReviewBean where image is not null", ReviewBean.class).getResultList();
				} else {
					this.review = this.getSession().createQuery("from ReviewBean where image is null", ReviewBean.class)
							.getResultList();
				}
			} catch (HibernateException e) {
				throw new HibernateException(e.getMessage());
			}
			if (this.review != null && this.review.size() > 0) {
				return this.review;
			}
		}
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
