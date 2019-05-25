package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.TopSearchBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.TopSearchDAO;

@Service
@Transactional
public class TopSearchService {
	@Autowired
	private TopSearchDAO topSearchDAO;
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
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
			Date date = new Date(System.currentTimeMillis());
			if (keyWordResult.size() == 0) { // keyWord不能重複
				topSearchBean.setSearchCount(1);
				topSearchBean.setUpdatedTime(date);
				topSearchBean.setKeyword(keyWord);
				topSearchDAO.insertTopSearch(topSearchBean);
				return topSearchBean;
			}else if(keyWordResult.size() != 0 && timeResult.size() == 0 ) { //keyWord重複,但不是這個月內的
				topSearchBean.setSearchCount(1);
				topSearchBean.setUpdatedTime(date);
				topSearchBean.setKeyword(keyWord);
				topSearchDAO.insertTopSearch(topSearchBean);
				return topSearchBean;
			}else if(keyWordResult.size() != 0 && timeResult.size() != 0) { //keyWord重複,且這個月內的,times+1
				TopSearchBean result = keyWordResult.get(0);
				result.setSearchCount( (result.getSearchCount() + 1 ) );
				topSearchDAO.updateTopSearch(result);
				return result;
			}else {
				return null;
			}
		}
		return null;
	}
	
	public TopSearchBean findTopSearchByPrimaryKey(Long id) {
		return topSearchDAO.findTopSearchByPrimaryKey(id);
	}
	
	public List<TopSearchBean> findTopSearchs(){
		return topSearchDAO.findTopSearchs();
	}
}
