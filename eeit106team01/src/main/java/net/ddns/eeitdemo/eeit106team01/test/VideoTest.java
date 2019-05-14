package net.ddns.eeitdemo.eeit106team01.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import net.ddns.eeitdemo.eeit106team01.forum.model.VideoBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.VideoService;

//@Component
public class VideoTest implements CommandLineRunner {
	@Autowired
	private VideoService videoService;

	@Override
	public void run(String... args) throws Exception {

		VideoBean videoBean = new VideoBean(
				new java.util.Date(),
				"new video",
				"iAmVideoUri", 
				"iAmThumbnailUri",
				"iAmGifUri",
				System.currentTimeMillis(),
				"newInsertVideo"
				);
		
		
		VideoBean result = videoService.insert(videoBean);
		System.out.println("new videoBean id = " + result.getId());
//		if (result != null) {
//			System.out.println("new videoBean id = " + result.getId());
//		        throw new RuntimeException("save 抛异常了");
//		}
	}

}
