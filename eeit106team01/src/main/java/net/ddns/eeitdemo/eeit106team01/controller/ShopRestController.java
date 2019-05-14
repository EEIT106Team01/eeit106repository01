package net.ddns.eeitdemo.eeit106team01.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.model.ProductBean1;
import net.ddns.eeitdemo.eeit106team01.model.ShopBean;

@RestController
public class ShopRestController {
	@Autowired
	private SessionFactory sessionFactory;

	@GetMapping(value = { "/products" })
	@SuppressWarnings("unchecked")
	public List<ProductBean1> findAll() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ProductBean", ProductBean1.class);
		List<ProductBean1> result = query.getResultList();
		return result;
	}

	@GetMapping(value = { "/items" })
	@SuppressWarnings("unchecked")
	public List<ShopBean> findAllItem() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ShopBean", ShopBean.class);
		List<ShopBean> result = query.getResultList();
		return result;
	}

	@PostMapping(path = { "/items" }, consumes = { "application/x-www-form-urlencoded", "application/json" })
	public List<ShopBean> createItem(@RequestBody ShopBean bean) {
		Session session = sessionFactory.openSession();
		ShopBean newBean = new ShopBean();
		newBean.setId(bean.getId());
		newBean.setName(bean.getName());
		newBean.setPrice(String.valueOf(bean.getPrice()));
		newBean.setImgLink(bean.getImgLink());
		newBean.setImgSetLink(bean.getImgSetLink());
		newBean.setPlatformName(bean.getPlatformName());
		newBean.setPlatformLink(bean.getPlatformLink());
		newBean.setTotalCount(String.valueOf(bean.getTotalCount()));
		newBean.setTotalPage(String.valueOf(bean.getTotalPage()));
		session.save(newBean);
		session.beginTransaction().commit();
		return findAllItem();
	}

	@GetMapping(value = { "/feebeeitems" })
	public List<ShopBean> findAllItemFromFeeBee() {
		Long startTime = 0L;
		Long endTime;
		Long durationInNano;
		Float durationInSeconds;
		
		List<ShopBean> result = new ArrayList<ShopBean>();
		try {
			URL url = new URL("https://feebee.com.tw/all/iphone/?mode=t&page=1");
			String html = String.valueOf(url);
			Document doc = Jsoup.connect(html)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
					.referrer("http://www.google.com").get();

			startTime = System.nanoTime();
			System.out.printf("\nFetching '%s' ...\n", html);
			
			Elements names = doc.select(".pure-u-1-4 .container h3");
			Elements prices = doc.select(".pure-u-1-4 .price");
			Elements imgLinks = doc.select(".pure-u-1-4 img");
			Elements imgSetLinks = doc.select(".pure-u-1-4 img");
			Elements platforms = doc.select(".pure-u-1-4 .shop");
			Elements platformLinks = doc.select(".pure-u-1-4 .items_layer_link");
			Elements productCounts = doc.select("#result_total");
			Elements pageCounts = doc.select(".pagination_last");
			
			String pageCountHref = pageCounts.get(0).absUrl("href");
			
//			final String cardFooterOneStar = "&#9733; &#9734; &#9734; &#9734; &#9734;";
//			final String cardFooterTwoStar = "&#9733; &#9733; &#9734; &#9734; &#9734;";
//			final String cardFooterThreeStar = "&#9733; &#9733; &#9733; &#9734; &#9734;";
//			final String cardFooterFourStar = "&#9733; &#9733; &#9733; &#9733; &#9734;";
//			final String cardFooterFive = "&#9733; &#9733; &#9733; &#9733; &#9733;";

			if (names != null && prices != null && imgLinks != null && imgSetLinks != null && platforms != null
					&& platformLinks != null && productCounts != null && pageCounts != null) {

				for (int nodeCount = 0; nodeCount < names.size(); nodeCount++) {
					ShopBean bean = new ShopBean();
					System.out.printf("\n[This is P.%s No.%s]\n", 1, nodeCount + 1);
					System.out.println("Name: " + names.get(nodeCount).text());
					System.out.println("Price: " + prices.get(nodeCount).text().replace("價格", "").replace(",", "").trim());
					System.out.println("ImgLink: " + imgLinks.get(nodeCount).absUrl("src"));
					System.out.println("ImgSetLink: " + imgSetLinks.get(nodeCount).absUrl("srcset"));
					System.out.println("Platform: " + platforms.get(nodeCount).text());
					System.out.println("PlatformLink: " + platformLinks.get(nodeCount).absUrl("href"));

					bean.setId(nodeCount + 1);
					bean.setName(names.get(nodeCount).text());
					bean.setPrice(prices.get(nodeCount).text().replace("價格", "").replace(",", "").trim());
					bean.setImgLink(imgLinks.get(nodeCount).absUrl("src"));
					bean.setImgSetLink(imgSetLinks.get(nodeCount).absUrl("srcset"));
					bean.setPlatformName(platforms.get(nodeCount).text());
					bean.setPlatformLink(platformLinks.get(nodeCount).absUrl("href"));
					bean.setTotalCount(String.valueOf(pageCountHref.substring(pageCountHref.indexOf("page=") + 5, pageCountHref.length())));
					bean.setTotalPage(String.valueOf(productCounts.text()));
					result.add(nodeCount, bean);
				}
			}
			System.out.printf("\n[There are %s... products match your search!]\n", pageCountHref.substring(pageCountHref.indexOf("page=") + 5, pageCountHref.length()));
			System.out.printf("\n[There will be %s... pages match your search!]\n", productCounts.text());
			System.out.printf("\nFinished! Fetched from '%s'\n", html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		endTime = System.nanoTime();
		// Total execution time in nano seconds
		durationInNano = (endTime - startTime);

		// Same duration in seconds (convert from millis)
		durationInSeconds = (float) TimeUnit.NANOSECONDS.toMillis(durationInNano) / 1000;
		System.out.printf("\n本次搜尋使用   %s 秒...", durationInSeconds);
//		String spendTimeString = String.format("\n本次搜尋使用   %s 秒...", durationInSeconds);
		
		return result;
	}

}
