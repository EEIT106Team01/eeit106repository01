package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

public interface VideoDAO {

	public VideoBean findByPrimaryKey(int id);
	
	public VideoBean findByPrimaryKeyAsProxy(int id);

	public List<VideoBean> findAll();

	public VideoBean insert(VideoBean videoBean);

	public VideoBean update(VideoBean videoBean);

	public boolean delete(int id);
}
