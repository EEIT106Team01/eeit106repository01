package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.shop.model.TopSearchBean;

public interface TopSearchDAO {

	abstract TopSearchBean insertTopSearch(String keyWord);

	abstract TopSearchBean updateTopSearch(TopSearchBean topSearchBean);

	abstract TopSearchBean findTopSearchByPrimaryKey(Long id);

	abstract List<TopSearchBean> findTopSearchs();
}
