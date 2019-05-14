package net.ddns.eeitdemo.eeit106team01.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.VideoBean;

//@Component
public class ArticleBeanTest implements CommandLineRunner {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("ArticleBeanTest Begin !");

		ArticleTopicCurrentBean topic1 = new ArticleTopicCurrentBean(
				"協尋0513錄像", // topicHeader
				"協尋文", // topicType
				"台北市", // topicRegion
				11, // topicLikeNum
				11, // contentReplyNum
				new java.util.Date(0), // topicCreateTime
				new java.util.Date(20000), // topicUpdateTime
				"visible", // topicStatus
				new java.util.Date(40000), // accidentTime
				"中山南路一段", // accidentLocation
				12.34, // accidentLocationLongitude
				34.56, // accidentLocationLatitude
				"我是主題1號", // topicContent
				new java.util.Date(60000), // topicContentUpdateTime
				"使用者修改" // updateMessage
		);

		ArticleContentCurrentBean content1 = new ArticleContentCurrentBean(
				21, // contentLikeNum
				21, // contentReplyNum
				new java.util.Date(80000), // contentCreateTime
				new java.util.Date(100000), // contentUpdateTime
				"replyType", // contentStatus 這欄位可以刪了吧
				"我是內文1號", // contentContent
				"使用者修改" // updateMessage
		);

		ArticleContentCurrentBean content2 = new ArticleContentCurrentBean(
				22, // contentLikeNum
				22, // contentReplyNum
				new java.util.Date(80000), // contentCreateTime
				new java.util.Date(100000), // contentUpdateTime
				"replyType", // contentStatus 這欄位可以刪了吧
				"我是內文2號", // contentContent
				"使用者修改" // updateMessage
		);

		ArticleContentCurrentBean content3 = new ArticleContentCurrentBean(
				23, // contentLikeNum
				23, // contentReplyNum
				new java.util.Date(80000), // contentCreateTime
				new java.util.Date(100000), // contentUpdateTime
				"replyType", // contentStatus 這欄位可以刪了吧
				"我是內文3號", // contentContent
				"使用者修改" // updateMessage
		);

		VideoBean video1 = new VideoBean(
				new java.util.Date(),
				"new video",
				"iAmVideoUri", 
				"iAmThumbnailUri",
				"iAmGifUri",
				System.currentTimeMillis(),
				"newInsertVideo"
				);
		
		// 把topic1塞給3個content
		content1.setArticleTopicCurrent(topic1);
		content2.setArticleTopicCurrent(topic1);
		content3.setArticleTopicCurrent(topic1);

		// content1是層級一的回覆
		// content2是層級二的回覆，回覆對象為content1
		// content3是層級二的回覆，回覆對象為content1。回覆對象可以為content2，但違反我們的設計邏輯
		content2.setReply(content1);
		content3.setReply(content1);
		
		//把video1塞入
		topic1.setVideoBean(video1);
		content1.setVideoBean(video1);
		content2.setVideoBean(video1);

		Session session1 = sessionFactory.openSession();
//		session1.save(video1);
		session1.save(content1);
		session1.save(content2);
		session1.save(content3);
		
		session1.close();

		System.out.println("ArticleBeanTest Finished !");

	}

}
