package net.ddns.eeitdemo.eeit106team01.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;

@Component
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

		// 把topic1塞給3個content
		content1.setArticleTopicCurrent(topic1);
		content2.setArticleTopicCurrent(topic1);
		content3.setArticleTopicCurrent(topic1);

		// content1是層級一的回覆
		// content2是層級二的回覆，回覆對象為content1
		// content3是層級二的回覆，回覆對象為content1。回覆對象可以為content2，但違反我們的設計邏輯
		content2.setReply(content1);
		content3.setReply(content1);

		Session session1 = sessionFactory.openSession();
		session1.save(content1);
		session1.save(content2);
		session1.save(content3);
		session1.close();

		// 看看topic1在這情況下有沒有塞入content們，結果沒有
		List<ArticleContentCurrentBean> topic1Contents = topic1.getArticleContentCurrentBeanList();
		System.out.println("topic1Contents is empty?:" + topic1Contents.isEmpty());

		// 從資料庫取出topic1，看看有沒有塞入content們，結果有
		Session session2 = sessionFactory.openSession();
		ArticleTopicCurrentBean topic1ByGet = session2.get(ArticleTopicCurrentBean.class, 1);
		List<ArticleContentCurrentBean> topic1ByGetContents = topic1ByGet.getArticleContentCurrentBeanList();
		System.out.println("topic1ByGetContents begin");
		System.out.println("topic1ByGetContents is empty?:" + topic1ByGetContents.isEmpty());
		for (ArticleContentCurrentBean value : topic1ByGetContents) {
			System.err.println("contents of topic1: " + value.getContentContent());
		}
		System.out.println("topic1ByGetContents end");

		// 從資料庫取出content1，看看有沒有塞入reply們，結果有
		ArticleContentCurrentBean content1ByGet = session2.get(ArticleContentCurrentBean.class, 1);
		List<ArticleContentCurrentBean> content1ByGetReplys = content1ByGet.getSubReplyList();
		System.out.println("content1ByGetReplys begin");
		System.out.println("content1ByGetReplys is empty?:" + content1ByGetReplys.isEmpty());
		for (ArticleContentCurrentBean value : content1ByGetReplys) {
			System.err.println("replys of content1: " + value.getContentContent());
		}
		System.out.println("content1ByGetReplys end");

		// 從資料庫取出content2，看看有沒有塞入reply們，結果沒有
		ArticleContentCurrentBean content2ByGet = session2.get(ArticleContentCurrentBean.class, 2);
		List<ArticleContentCurrentBean> content2ByGetReplys = content2ByGet.getSubReplyList();
		System.out.println("content2ByGetReplys is empty?:" + content2ByGetReplys.isEmpty());

		// 從資料庫取出content3，看看有沒有塞入reply們，結果沒有
		ArticleContentCurrentBean content3ByGet = session2.get(ArticleContentCurrentBean.class, 3);
		List<ArticleContentCurrentBean> content3ByGetReplys = content3ByGet.getSubReplyList();
		System.out.println("content3ByGetReplys is empty?:" + content3ByGetReplys.isEmpty());

		session2.close();

		System.out.println("ArticleBeanTest Finished !");

	}

}
