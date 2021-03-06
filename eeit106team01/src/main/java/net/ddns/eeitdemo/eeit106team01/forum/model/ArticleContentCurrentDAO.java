package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

public interface ArticleContentCurrentDAO {
	public ArticleContentCurrentBean findByPrimaryKey(int id);
	
	public ArticleContentCurrentBean findByPrimaryKeyAsProxy(int id);

	public List<ArticleContentCurrentBean> findAll();

	public ArticleContentCurrentBean insert(ArticleContentCurrentBean bean);

	public ArticleContentCurrentBean update(ArticleContentCurrentBean bean);
	
	public List<ArticleContentCurrentBean> queryList(String hql, int startPosition, int maxResult);

	public boolean delete(int id);
}
