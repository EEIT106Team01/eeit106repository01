package net.ddns.eeitdemo.eeit106team01.forum.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTopicCurrentServiceTest {

	@Autowired
	private ArticleTopicCurrentService articleTopicCurrentService;
	
	@Autowired
	private ArticleContentCurrentService articleContentCurrentService;
	
	@Autowired
	private VideoService videoService;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("test start!");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("test end!");
	}

	@Test
	public void testFindByPrimaryKey() {
		ArticleTopicCurrentBean findOne = articleTopicCurrentService.findByPrimaryKey(1);
		assertEquals(findOne.getTopicContent(), "我是主題1號");
		assertEquals(findOne.getAccidentLocation(), "中山南路一段");
	}

	@Test
	public void testFindAll() {
		List<ArticleTopicCurrentBean> findAll = articleTopicCurrentService.findAll();
		assertEquals(findAll.size(), 1);
		assertEquals(findAll.get(0).getAccidentLocationLongitude(), (Double)12.34);
	} 

	@Test
	public void testInsert() {
		VideoBean videoTest1 = videoService.findByPrimaryKey(1);
//		ArticleTopicCurrentBean topicTest1 = new ArticleTopicCurrentBean();
//		ArticleContentCurrentBean contentTest1 = new ArticleContentCurrentBean();
		ArticleTopicCurrentBean topicTest2 = articleTopicCurrentService.findByPrimaryKey(16);
		
//		if(videoTest1 != null) {
			topicTest2.setVideoBean(videoTest1);
//			contentTest1.setVideoBean(videoTest1);
//			contentTest1.setArticleTopicCurrent(topicTest1);
//		}
		
//		ArticleContentCurrentBean result1 = articleContentCurrentService.insert(contentTest1);
//		if(result1 != null) {
//			System.out.println("ok");
//		}else {
//			System.out.println("so bad");
//		}
		
//		ArticleTopicCurrentBean topic53 = new ArticleTopicCurrentBean(
//				"文章標題53", // topicHeader
//				"文章類別53", // topicType
//				"高譚市", // topicRegion
//				53, // topicLikeNum
//				null, //topicLikeWho
//				53, // contentReplyNum
//				new java.util.Date(53), // topicCreateTime
//				new java.util.Date(53), // topicUpdateTime
//				"visible53", // topicStatus
//				new java.util.Date(53), // accidentTime
//				"事發位置53", // accidentLocation
//				35.35, // accidentLocationLongitude
//				53.53, // accidentLocationLatitude
//				"主題內文53", // topicContent
//				new java.util.Date(53), // topicContentUpdateTime
//				30, //pageViews
//				"使用者修改53", // updateMessage
//				null, //MemberBean memberBean
//				null //VideoBean videoBean
//		);
//		ArticleTopicCurrentBean insertResult = articleTopicCurrentService.insert(topic53);
//		assertEquals(insertResult.getTopicRegion(), "高譚市");
		
	}

	@Test
	public void testUpdate() {
		ArticleTopicCurrentBean topic54 = new ArticleTopicCurrentBean(
				null, // topicHeader
				null, // topicType
				null, // topicRegion
				null, // topicLikeNum
				null, //topicLikeWho
				null, // contentReplyNum
				null, // topicCreateTime
				null, // topicUpdateTime
				null, // topicStatus
				null, // accidentTime
				"事發位置77", // accidentLocation
				null, // accidentLocationLongitude
				null, // accidentLocationLatitude
				null, // topicContent
				null, // topicContentUpdateTime
				null, //pageViews
				null, // updateMessage
				null, //MemberBean memberBean
				null //VideoBean videoBean
		);
		ArticleTopicCurrentBean updateResult = articleTopicCurrentService.updateIgnoreNullColumn(topic54);
		assertEquals(updateResult.getTopicHeader(), "文章標題53");
	}

	@Test
	public void testDelete() {
		boolean deleteResult = articleTopicCurrentService.delete(4);
		assertEquals(true, deleteResult);
	}

}
