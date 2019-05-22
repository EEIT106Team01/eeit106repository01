package net.ddns.eeitdemo.eeit106team01.shop.model.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.TopSearchBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.TopSearchDAO;

@Transactional
@Repository
public class TopSearchDAOImpl implements TopSearchDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public TopSearchBean insertTopSearch(String keyWord) {
		if (keyWord != null) {
			Query query = this.getSession().createQuery("from TopSearchBean where keyWord = :keyWord", TopSearchBean.class);
			query.setParameter("keyWord", keyWord);
			List<TopSearchBean> keyWordResult = query.getResultList();
			Query query1 = this.getSession().createQuery(
					"from TopSearchBean where time >= DATEADD(day,:day ,GETDATE()) AND time <= DATEADD(day,:day1,GETDATE()) AND keyWord = :keyWord",
					TopSearchBean.class);
			query1.setParameter("day", -30);
			query1.setParameter("day1", 0);
			query1.setParameter("keyWord", keyWord);
			List<TopSearchBean> timeResult =  query1.getResultList();
			TopSearchBean topSearchBean = new TopSearchBean();
			System.out.println(keyWordResult);
			System.out.println(timeResult);
			if (keyWordResult.size() == 0) { // keyWord不能重複
				topSearchBean.setSearchTimes(1);
				topSearchBean.setTime();
				topSearchBean.setKeyWord(keyWord);
				getSession().save(topSearchBean);
				return topSearchBean;
			}else if(keyWordResult.size() != 0 && timeResult.size() == 0 ) { //keyWord重複,但不是這個月內的
				topSearchBean.setSearchTimes(1);
				topSearchBean.setTime();
				topSearchBean.setKeyWord(keyWord);
				getSession().save(topSearchBean);
				return topSearchBean;
			}else if(keyWordResult.size() != 0 && timeResult.size() != 0) { //keyWord重複,且這個月內的,times+1
				TopSearchBean result = keyWordResult.get(0);
				result.setSearchTimes( (result.getSearchTimes() + 1 ) );
				updateTopSearch(result);
				return result;
			}else {
				return null;
			}
		}
		return null;
	}

	@Override
	public TopSearchBean updateTopSearch(TopSearchBean topSearchBean) {
		if (topSearchBean != null) {
			getSession().update(topSearchBean);
			return findTopSearchByPrimaryKey(topSearchBean.getId());
		}
		return null;
	}

	@Override
	public TopSearchBean findTopSearchByPrimaryKey(Long id) {
		return getSession().get(TopSearchBean.class, id);
	}

	@Override
	public List<TopSearchBean> findTopSearchs() {
		return this.getSession().createQuery("from TopSearchBean order by searchTimes desc", TopSearchBean.class).getResultList();
	}

}
