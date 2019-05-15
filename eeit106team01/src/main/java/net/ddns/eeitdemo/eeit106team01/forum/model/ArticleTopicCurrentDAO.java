package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

public interface ArticleTopicCurrentDAO {
	
	public ArticleTopicCurrentBean findByPrimaryKey(int id);

	public List<ArticleTopicCurrentBean> findAll();

	public ArticleTopicCurrentBean insert(ArticleTopicCurrentBean bean);

	public ArticleTopicCurrentBean update(ArticleTopicCurrentBean bean);

	public boolean delete(int id);
}
