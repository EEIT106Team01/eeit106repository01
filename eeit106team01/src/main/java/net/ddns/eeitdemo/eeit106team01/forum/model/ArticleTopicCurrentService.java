package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleTopicCurrentService {

	@Autowired
	private MemberBeanService memberBeanService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ArticleTopicCurrentDAO articleTopicCurrentDAO;

	public ArticleTopicCurrentBean findByPrimaryKey(int id) {
		return articleTopicCurrentDAO.findByPrimaryKey(id);
	};
	
	public ArticleTopicCurrentBean findByPrimaryKeyAsProxy(int id) {
		return articleTopicCurrentDAO.findByPrimaryKeyAsProxy(id);
	};
	
	public List<ArticleTopicCurrentBean> findLikeTopicHeader(String likeTopicHeader) {
		return articleTopicCurrentDAO.findLikeTopicHeader(likeTopicHeader);
	}
	
	public List<ArticleTopicCurrentBean> findAll() {
		return articleTopicCurrentDAO.findAll();
	};

	public List<ArticleTopicCurrentBean> findByLastRange(int startPosition, int maxResult, String topicType, String orderColumn, String likeTopicHeader) {
		return articleTopicCurrentDAO.findByLastRange(startPosition, maxResult, topicType, orderColumn, likeTopicHeader);
	}
	
	public List<ArticleTopicCurrentBean> findByCoordinateRange(Double lowerLatitude, Double upperLatitude,
			Double lowerLongitude, Double upperLongitude) {
		return articleTopicCurrentDAO.findByCoordinateRange(lowerLatitude, upperLatitude, lowerLongitude,
				upperLongitude);
	}

	public List<String> findAutocompleteByTopicHeader(String inputString) {
		return articleTopicCurrentDAO.findAutocompleteByTopicHeader(inputString);
	}
	
	public Long findTopicNum(String topicType, String likeTopicHeader) {
		return articleTopicCurrentDAO.findTopicNum(topicType, likeTopicHeader);
	}
	
	public ArticleTopicCurrentBean insert(ArticleTopicCurrentBean bean) {
		if (bean != null) {
			System.err.println("before select memberBean");
			MemberTempBean memberBean = memberBeanService.findByPrimaryKey(bean.getMemberBean().getId().intValue());
			System.err.println("after select memberBean");
			
			bean.setMemberBean(memberBean);
			
			System.err.println("before select videoBean in topic");
			if(bean.getVideoBean() != null && bean.getVideoBean().getId() != null) {
				VideoBean videoBean = videoService.findByPrimaryKey(bean.getVideoBean().getId().intValue());
				bean.setVideoBean(videoBean);
			}
			System.err.println("after select videoBean in topic");
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
				//不應該在這修改
//				findOne.setTopicLikeNum(bean.getTopicLikeNum());
			}
			if (bean.getTopicLikeWho() != null) {
				//不應該在這修改
//				findOne.setTopicLikeWho(bean.getTopicLikeWho());
			}
			if (bean.getContentReplyNum() != null) {
				//不應該在這修改
//				findOne.setContentReplyNum(bean.getContentReplyNum());
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
				//不應該在這修改
//				findOne.setTopicContentUpdateTime(bean.getTopicContentUpdateTime());
			}
			if (bean.getPageViews() != null) {
				//不應該在這修改
//				findOne.setPageViews(bean.getPageViews());
			}
			if (bean.getUpdateMessage() != null) {
				findOne.setUpdateMessage(bean.getUpdateMessage());
			}
			if (bean.getMemberBean() != null && bean.getMemberBean().getId() != null) {
				// memberBean不應該被修改
//				MemberBean memberBean = memberBeanService.findByPrimaryKey(bean.getMemberBean().getId().intValue());
//				findOne.setMemberBean(memberBean);
			}
			if (bean.getVideoBean() != null && bean.getVideoBean().getId() != null) {
				VideoBean videoBean = videoService.findByPrimaryKey(bean.getVideoBean().getId().intValue());
				findOne.setVideoBean(videoBean);
			}
			return articleTopicCurrentDAO.update(findOne);
		}
		return null;
	};

	public void increasePageViews(int id) {
		ArticleTopicCurrentBean findOne = findByPrimaryKey(id);
		if (findOne != null) {
			findOne.setPageViews(findOne.getPageViews() + 1);
			articleTopicCurrentDAO.update(findOne);
		}
	}

	public void increaseContentReplyNum(int id) {
		ArticleTopicCurrentBean findOne = findByPrimaryKey(id);
		if (findOne != null) {
			findOne.setContentReplyNum(findOne.getContentReplyNum() + 1);
			articleTopicCurrentDAO.update(findOne);
		}
	}
	
	public boolean delete(int id) {
		return articleTopicCurrentDAO.delete(id);
	};
}
