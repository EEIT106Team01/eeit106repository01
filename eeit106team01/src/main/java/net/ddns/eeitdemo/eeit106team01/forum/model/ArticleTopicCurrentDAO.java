package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

public interface ArticleTopicCurrentDAO {
	
	public ArticleTopicCurrentBean findByPrimaryKey(int id);
	
	public ArticleTopicCurrentBean findByPrimaryKeyAsProxy(int id);

	public List<ArticleTopicCurrentBean> findAll();
	
	public List<ArticleTopicCurrentBean> findByLastRange(int startPosition, int maxResult, String topicType, String orderColumn, String likeTopicHeader);
	
	public List<ArticleTopicCurrentBean> findByCoordinateRange(Double lowerLatitude, Double upperLatitude, Double lowerLongitude, Double upperLongitude);
	
	public List<ArticleTopicCurrentBean> findLikeTopicHeader(String likeTopicHeader);
	
	public Long findTopicNum(String topicType, String likeTopicHeader);
	
	public List<String> findAutocompleteByTopicHeader(String inputString);
	
	public ArticleTopicCurrentBean insert(ArticleTopicCurrentBean bean);

	public ArticleTopicCurrentBean update(ArticleTopicCurrentBean bean);

	public boolean delete(int id);
}
