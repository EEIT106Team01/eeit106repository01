package net.ddns.eeitdemo.eeit106team01.forum.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoServiceTest {
	@Autowired
	private VideoService videoService;
	private VideoBean videoBean;

	@Before
	public void setUp() throws Exception {
		videoBean = new VideoBean(new java.util.Date(), "new video", "iAmVideoUri", "iAmThumbnailUri", "iAmGifUri",
				Double.valueOf(System.currentTimeMillis()), "newInsertVideo");
		videoBean = videoService.insert(videoBean);
	}

	@After
	public void tearDown() throws Exception {
//		videoService.delete(videoBean.getId());
	}

	@Test
	public void testFindByPrimaryKey() {
		Integer result = videoService.findByPrimaryKey(videoBean.getId()).getId();
		assertEquals("testFindByPrimaryKey", videoBean.getId().intValue(), result.intValue());
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		boolean result = videoService.delete(videoBean.getId());
		assertEquals("testDelete", true, result);
	}

}
