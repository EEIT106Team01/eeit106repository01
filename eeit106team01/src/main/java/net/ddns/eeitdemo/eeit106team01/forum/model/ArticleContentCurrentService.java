package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleContentCurrentService {
	
	@Autowired
	private ArticleContentCurrentDAO articleContentCurrentDAO;
	
	public ArticleContentCurrentBean findByPrimaryKey(int id) {
		return articleContentCurrentDAO.findByPrimaryKey(id);
	};
	
	public List<ArticleContentCurrentBean> findAll() {
		return articleContentCurrentDAO.findAll();
	};
	
	public ArticleContentCurrentBean insert(ArticleContentCurrentBean bean) {
		if (bean != null) {
			return articleContentCurrentDAO.insert(bean);
		}
		return null;
	};
	
	public ArticleContentCurrentBean update(int id, ArticleContentCurrentBean bean) {
		if (bean != null) {
			bean.setId(id);
			return articleContentCurrentDAO.update(bean);
		}
		return null;
	};
	
	public boolean delete(int id) {
		return articleContentCurrentDAO.delete(id);
	};

}
