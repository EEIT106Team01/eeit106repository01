package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleTopicCurrentService {

	@Autowired
	private ArticleTopicCurrentDAO articleTopicCurrentDAO;

	public ArticleTopicCurrentBean findByPrimaryKey(int id) {
		return articleTopicCurrentDAO.findByPrimaryKey(id);
	};

	public List<ArticleTopicCurrentBean> findAll() {
		return articleTopicCurrentDAO.findAll();
	};

	public List<ArticleTopicCurrentBean> findByTopRange(int begin, int end, String order) {
		String hqlOrderByTime = "from ArticleTopicCurrentBean";
		String hqlOrderByLike = "from ArticleTopicCurrentBean order by topicLikeNum desc";

		List<ArticleTopicCurrentBean> topRangeResult = null;
		if ("orderByLike".equals(order)) {
			topRangeResult = articleTopicCurrentDAO.findByTopRange(hqlOrderByLike, begin, end);
		} else if ("orderByTime".equals(order)){
			topRangeResult = articleTopicCurrentDAO.findByTopRange(hqlOrderByTime, begin, end);
		}
		return topRangeResult;
	}
	
	public List<ArticleTopicCurrentBean> findByCoordinateRange(Double lowerLatitude, Double upperLatitude,
			Double lowerLongitude, Double upperLongitude){
		return articleTopicCurrentDAO.findByCoordinateRange(lowerLatitude, upperLatitude, lowerLongitude, upperLongitude);
	}

	public ArticleTopicCurrentBean insert(ArticleTopicCurrentBean bean) {
		if (bean != null) {
			return articleTopicCurrentDAO.insert(bean);
		}
		return null;
	};

	public ArticleTopicCurrentBean updateIgnoreNullColumn(ArticleTopicCurrentBean bean) {
		ArticleTopicCurrentBean findOne = findByPrimaryKey(bean.getId());
		if (findOne != null) {
			if (bean.getTopicHeader() != null) {
				findOne.setTopicHeader(bean.getTopicHeader());
			}
			if (bean.getTopicType() != null) {
				findOne.setTopicType(bean.getTopicType());
			}
			if (bean.getTopicRegion() != null) {
				findOne.setTopicRegion(bean.getTopicRegion());
			}
			if (bean.getTopicLikeNum() != null) {
				findOne.setTopicLikeNum(bean.getTopicLikeNum());
			}
			if (bean.getContentReplyNum() != null) {
				findOne.setContentReplyNum(bean.getContentReplyNum());
			}
			if (bean.getTopicUpdateTime() != null) {
				findOne.setTopicUpdateTime(bean.getTopicUpdateTime());
			}
			if (bean.getTopicStatus() != null) {
				findOne.setTopicStatus(bean.getTopicStatus());
			}
			if (bean.getAccidentTime() != null) {
				findOne.setAccidentTime(bean.getAccidentTime());
			}
			if (bean.getAccidentLocation() != null) {
				findOne.setAccidentLocation(bean.getAccidentLocation());
			}
			if (bean.getAccidentLocationLongitude() != null) {
				findOne.setAccidentLocationLongitude(bean.getAccidentLocationLongitude());
			}
			if (bean.getAccidentLocationLatitude() != null) {
				findOne.setAccidentLocationLatitude(bean.getAccidentLocationLatitude());
			}
			if (bean.getTopicContent() != null) {
				findOne.setTopicContent(bean.getTopicContent());
			}
			if (bean.getTopicContentUpdateTime() != null) {
				findOne.setTopicContentUpdateTime(bean.getTopicContentUpdateTime());
			}
			if (bean.getUpdateMessage() != null) {
				findOne.setUpdateMessage(bean.getUpdateMessage());
			}
			if (bean.getVideoBean() != null) {
				findOne.setVideoBean(bean.getVideoBean());
			}
			return articleTopicCurrentDAO.update(findOne);
		}
		return null;
	};

	public boolean delete(int id) {
		return articleTopicCurrentDAO.delete(id);
	};
}
