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
	@Autowired
	private MemberBeanService memberBeanService;

	public VideoBean findByPrimaryKey(int id) {
		return videoDAO.findByPrimaryKey(id);
	}
	
	public VideoBean findByPrimaryKeyAsProxy(int id) {
		return videoDAO.findByPrimaryKeyAsProxy(id);
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
			MemberBean memberBean = memberBeanService.findByPrimaryKeyAsProxy(videoBean.getMemberBean().getId());
			vb.setMemberBean(memberBean);
			if (videoBean.getVideoURI() != null && videoBean.getVideoURI().length() != 0) {
				vb.setVideoURI(videoBean.getVideoURI());
			}
			if (videoBean.getVideoStatus() != null && videoBean.getVideoStatus().length() != 0) {
				vb.setVideoStatus(videoBean.getVideoStatus());
			}
			if (videoBean.getVideoLength() != null) {
//				影片長度不應該被修改
//				vb.setVideoLength(videoBean.getVideoLength());
			}
			if (videoBean.getUploadTime() != null) {
//				上傳時間不應該被修改
//				vb.setUploadTime(videoBean.getUploadTime());
			}
			if (videoBean.getUpdateMessage() != null && videoBean.getUpdateMessage().length() != 0) {
				vb.setUpdateMessage(videoBean.getUpdateMessage());
			}
			if (videoBean.getThumbnailURI() != null && videoBean.getThumbnailURI().length() != 0) {
				vb.setThumbnailURI(videoBean.getThumbnailURI());
			}
			if (videoBean.getGifURI() != null && videoBean.getGifURI().length() != 0) {
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
