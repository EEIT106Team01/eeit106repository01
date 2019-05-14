package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class VideoService {
	
	@Autowired
	private VideoDAO videoDAO;
	
	public VideoBean findByPrimaryKey(int id) {
		return videoDAO.findByPrimaryKey(id);
	}
	
	public List<VideoBean> findAll() {
		return videoDAO.findAll();
	}
	
	public VideoBean insert(VideoBean videoBean) {
		return videoDAO.insert(videoBean);
	}
	
	public VideoBean update(VideoBean videoBean) {
		return videoDAO.update(videoBean);
	}
	
	public boolean delete(int id) {
		return videoDAO.delete(id);
	}
	
}
