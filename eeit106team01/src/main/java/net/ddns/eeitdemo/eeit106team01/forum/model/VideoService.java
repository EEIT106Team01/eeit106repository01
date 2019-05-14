package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
public class VideoService {

	@Autowired
	private VideoDAO videoDAO;

	public VideoBean findByPrimaryKey(int id) {
		return videoDAO.findByPrimaryKey(id);
	}

	public List<VideoBean> findAll() {
		return videoDAO.findAll();
	}

	public VideoBean insert(VideoBean videoBean) throws Exception {
		VideoBean result = videoDAO.insert(videoBean);
//		if (result != null) {
//			System.out.println("new videoBean id = " + result.getId());
//		        throw new RuntimeException("save 抛异常了");
//		}
		return result;
	}

	public VideoBean update(VideoBean videoBean) {
		return videoDAO.update(videoBean);
	}

	public boolean delete(int id) {
		return videoDAO.delete(id);
	}

}
