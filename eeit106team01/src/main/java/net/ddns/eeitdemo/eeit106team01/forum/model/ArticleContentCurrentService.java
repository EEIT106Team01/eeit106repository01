package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleContentCurrentService {

	@Autowired
	private ArticleContentCurrentDAO articleContentCurrentDAO;
	@Autowired
	private MemberBeanService memberBeanService;

	public ArticleContentCurrentBean findByPrimaryKey(int id) {
		return articleContentCurrentDAO.findByPrimaryKey(id);
	};

	public List<ArticleContentCurrentBean> findAll() {
		return articleContentCurrentDAO.findAll();
	};

	public ArticleContentCurrentBean insert(ArticleContentCurrentBean bean) {
		if (bean != null) {
			MemberBean memberBean = memberBeanService.findByPrimaryKey(bean.getMemberBean().getId());
			bean.setMemberBean(memberBean);
			if (bean.getArticleTopicCurrent().getMemberBean().getId() == bean.getMemberBean().getId()) {
				bean.getArticleTopicCurrent().setMemberBean(memberBean);
			}
			if ((bean.getArticleTopicCurrent().getVideoBean() != null)) {
				if ((bean.getArticleTopicCurrent().getVideoBean().getMemberBean().getId() == bean.getMemberBean().getId())){
					bean.getArticleTopicCurrent().getVideoBean().setMemberBean(memberBean);
				} else {
					bean.getArticleTopicCurrent().getVideoBean().setMemberBean(bean.getArticleTopicCurrent().getMemberBean());
				}
			}
			if (
					(bean.getVideoBean() != null) &&
					(bean.getVideoBean().getMemberBean().getId() == bean.getMemberBean().getId())
					) {
				bean.getVideoBean().setMemberBean(memberBean);
			}
			if ((bean.getReply() != null) && (bean.getReply().getId() != null)) {
				bean.getReply().setArticleTopicCurrent(bean.getArticleTopicCurrent());
				if (bean.getReply().getMemberBean().getId() == bean.getMemberBean().getId()) {
					bean.getReply().setMemberBean(memberBean);
				}
				if (
						(bean.getReply().getVideoBean() != null) &&
						(bean.getReply().getVideoBean().getMemberBean().getId() == bean.getMemberBean().getId())						
						) {
					bean.getReply().getVideoBean().setMemberBean(memberBean);
				}
			}
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
				if (bean.getContentLikeWho() != null) {
					findOne.setContentLikeWho(bean.getContentLikeWho());
				}
				if (bean.getContentReplyNum() != null) {
					findOne.setContentReplyNum(bean.getContentReplyNum());
				}
				if (bean.getContentCreateTime() != null) {
//					發布時間不應該被修改
//					findOne.setContentCreateTime(bean.getContentCreateTime());
				}
				if (bean.getContentUpdateTime() != null) {
					findOne.setContentUpdateTime(new java.util.Date(System.currentTimeMillis()));
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
//					Reply不應該被修改
//					findOne.setReply(bean.getReply());
				}
				if ((bean.getVideoBean() != null)) {
					if (bean.getVideoBean().getId() == -1) {
//						Video ID設為-1為上移除與影片的關聯
						findOne.setVideoBean(null);
					} else if (
							(findOne.getVideoBean() == null) ||
							(bean.getVideoBean().getId() != findOne.getVideoBean().getId())
							) {
//						Video ID不同時為上傳新影片
						System.err.println(bean.getVideoBean().getId());
						bean.getVideoBean().setMemberBean(findOne.getMemberBean());
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
				+ "' and accb.reply =null order by accb.contentLikeNum desc, accb.contentCreateTime desc";
		List<ArticleContentCurrentBean> notRepliedContents = articleContentCurrentDAO.queryList(hql, startPosition - 1,
				maxResult);
		if (!notRepliedContents.isEmpty()) {
			String idString = "accb.reply.id ='";
			for (ArticleContentCurrentBean noRepliedContent : notRepliedContents) {
				noRepliedContent.getId();
				idString = idString + noRepliedContent.getId() + "' or accb.reply.id ='";
			}
			idString = idString.substring(0, idString.length() - 20);
			String hql2 = "from ArticleContentCurrentBean accb where " + idString + " order by accb.contentLikeNum desc, accb.contentCreateTime asc";
			List<ArticleContentCurrentBean> repliedContents = articleContentCurrentDAO.queryList(hql2, 0, 0);
			notRepliedContents.addAll(repliedContents);
		}
		return notRepliedContents;
	}

	public List<ArticleContentCurrentBean> findByTime(int id, int startPosition, int maxResult) {
		String hql = "from ArticleContentCurrentBean accb where accb.articleTopicCurrent.id ='" + id
				+ "' and accb.reply =null order by accb.contentCreateTime asc";
		List<ArticleContentCurrentBean> notRepliedContents = articleContentCurrentDAO.queryList(hql, startPosition - 1,
				maxResult);
		if (!notRepliedContents.isEmpty()) {
			String idString = "accb.reply.id ='";
			for (ArticleContentCurrentBean noRepliedContent : notRepliedContents) {
				noRepliedContent.getId();
				idString = idString + noRepliedContent.getId() + "' or accb.reply.id ='";
			}
			idString = idString.substring(0, idString.length() - 20);
			String hql2 = "from ArticleContentCurrentBean accb where " + idString + " order by accb.contentCreateTime asc";
			List<ArticleContentCurrentBean> repliedContents = articleContentCurrentDAO.queryList(hql2, 0, 0);
			notRepliedContents.addAll(repliedContents);
		}
		return notRepliedContents;
	}

	public Map<Integer, String> contentWhoLike(int id, int memberId, String likeOrDislike) {
		ArticleContentCurrentBean articleContentCurrentBean = this.findByPrimaryKey(id);
		int likeNumber = 0;
		if (likeOrDislike.equals("like")) {
			likeNumber = 1;
		} else if (likeOrDislike.equals("dislike")) {
			likeNumber = -1;
		}
		HashMap<Integer, String> likeWho = null;
		if (articleContentCurrentBean != null) {
			likeWho = articleContentCurrentBean.getContentLikeWho();
			if (likeWho != null) {
				if (likeWho.containsKey(memberId)) {
					likeWho.remove(memberId);
				} else {
					likeWho.put(memberId, likeOrDislike);
				}
			} else {
				likeWho = new HashMap<Integer, String>();
				likeWho.put(memberId, likeOrDislike);
			}
			articleContentCurrentBean.setContentLikeWho(likeWho);
			articleContentCurrentBean.setContentLikeNum(articleContentCurrentBean.getContentLikeNum() + likeNumber);
		}
		return likeWho;
	}

	public boolean delete(int id) {
		return articleContentCurrentDAO.delete(id);
	};

}
