package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleContentCurrentService {

	@Autowired
	private ArticleContentCurrentDAO articleContentCurrentDAO;
//	@Autowired
//	private VideoDAO videoDAO;

	public ArticleContentCurrentBean findByPrimaryKey(int id) {
		return articleContentCurrentDAO.findByPrimaryKey(id);
	};

	public List<ArticleContentCurrentBean> findAll() {
		return articleContentCurrentDAO.findAll();
	};

	public ArticleContentCurrentBean insert(ArticleContentCurrentBean bean) {
		if (bean != null) {
//			videoDAO.findByPrimaryKey(bean.getVideoBean().getId());
			return articleContentCurrentDAO.insert(bean);
		}
		return null;
	};

	public ArticleContentCurrentBean update(ArticleContentCurrentBean bean) {
		if (bean != null) {
			ArticleContentCurrentBean findOne = this.findByPrimaryKey(bean.getId());
			if (findOne != null) {
				if (bean.getContentLikeNum() != null) {
					findOne.setContentLikeNum(bean.getContentLikeNum());
				}
				if (bean.getContentReplyNum() != null) {
					findOne.setContentReplyNum(bean.getContentReplyNum());
				}
				if (bean.getContentCreateTime() != null) {
//					發布時間不應該被修改
//					findOne.setContentCreateTime(bean.getContentCreateTime());
				}
				if (bean.getContentUpdateTime() != null) {
					findOne.setContentUpdateTime(bean.getContentUpdateTime());
				}
				if (bean.getContentStatus() != null) {
					findOne.setContentStatus(bean.getContentStatus());
				}
				if (bean.getContentContent() != null) {
					findOne.setContentContent(bean.getContentContent());
				}
				if (bean.getUpdateMessage() != null) {
					findOne.setUpdateMessage(bean.getUpdateMessage());
				}
				if (bean.getArticleTopicCurrent() != null) {
//					Topic不應該被修改
//					findOne.setArticleTopicCurrent(bean.getArticleTopicCurrent());
				}
				if (bean.getReply() != null) {
					findOne.setReply(bean.getReply());
				}
				if ((bean.getVideoBean() != null)) {
					if (bean.getVideoBean().getId() == -1) {
//						Video ID設為-1為上移除與影片的關聯
						findOne.setVideoBean(null);
					} else if (bean.getVideoBean().getId().toString().length() == 0) {
//						Video ID設為空字串時為上傳新影片
						findOne.setVideoBean(bean.getVideoBean());
					}
				}
				return articleContentCurrentDAO.update(findOne);
			}
		}
		return null;
	};

	public List<ArticleContentCurrentBean> findByLike(int id, int startPosition, int maxResult) {
		String hql = "from ArticleContentCurrentBean accb where accb.articleTopicCurrent.id ='" + id
				+ "' order by accb.contentLikeNum desc, accb.contentCreateTime desc";
		return articleContentCurrentDAO.queryList(hql, startPosition - 1, maxResult);
	}

	public List<ArticleContentCurrentBean> findByTime(int id, int startPosition, int maxResult) {
		String hql = "from ArticleContentCurrentBean accb where accb.articleTopicCurrent.id ='" + id
				+ "' order by accb.contentCreateTime desc";
		return articleContentCurrentDAO.queryList(hql, startPosition - 1, maxResult);
	}

	public boolean delete(int id) {
		return articleContentCurrentDAO.delete(id);
	};

}
