package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
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
//		        throw new RuntimeException("save 拋異常了");
//		}
		return result;
	}

	public VideoBean update(VideoBean videoBean) {
		VideoBean vb = this.findByPrimaryKey(videoBean.getId());
		if (vb != null) {
			if (videoBean.getVideoURI() != null) {
				vb.setVideoURI(videoBean.getVideoURI());
			}
			if (videoBean.getVideoStatus() != null) {
				vb.setVideoStatus(videoBean.getVideoStatus());
			}
			if (videoBean.getVideoLength() != null) {
				vb.setVideoLength(videoBean.getVideoLength());
			}
			if (videoBean.getUploadTime() != null) {
				vb.setUploadTime(videoBean.getUploadTime());
			}
			if (videoBean.getUpdateMessage() != null) {
				vb.setUpdateMessage(videoBean.getUpdateMessage());
			}
			if (videoBean.getThumbnailURI() != null) {
				vb.setThumbnailURI(videoBean.getThumbnailURI());
			}
			if (videoBean.getGifURI() != null) {
				vb.setGifURI(videoBean.getGifURI());
			}
			return videoDAO.update(vb);
		}
		return null;
	}

	public boolean delete(int id) {
		return videoDAO.delete(id);
	}

}
