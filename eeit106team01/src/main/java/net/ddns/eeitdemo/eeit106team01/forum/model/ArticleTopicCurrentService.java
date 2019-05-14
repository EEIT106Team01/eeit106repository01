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

	public ArticleTopicCurrentBean insert(ArticleTopicCurrentBean bean, VideoBean videoBean) {
		if (bean != null) {
			bean.setVideoBean(videoBean);
			return articleTopicCurrentDAO.insert(bean);
		}
		return null;
	};

	public ArticleTopicCurrentBean update(int id, ArticleTopicCurrentBean bean) {
		if (bean != null) {
			bean.setId(id);
			return articleTopicCurrentDAO.update(bean);
		}
		return null;
	};

	public boolean delete(int id) {
		return articleTopicCurrentDAO.delete(id);
	};
}
