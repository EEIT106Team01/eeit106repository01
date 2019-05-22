package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;

public class ShopCrawler {
	
	// UserAgents
	private static String userAgentChrome = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
	private static String userAgentMozilla = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";
	private static String userAgentIe = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko";
	private static String userAgentEdge = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.9200";
	private static String userAgentSafari = "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25";

	// Referrer
	@SuppressWarnings("unused")
	private static String referrerGoogle = "https://www.google.com/";
	private static String referrerYahooTW = "https://tw.yahoo.com/";

	//@formatter:off
	public static String YahooCrawler(String fetchProductName, Integer fetchStartPage, Integer fetchEndPage) throws IOException {
		String result = null;
		
		// @formatter:on
		// Random User Agent
		String userAgent;
		Integer randomNum = (int) (Math.random() * 5 + 1);
		if (randomNum == 1) {
			userAgent = userAgentChrome;
		} else if (randomNum == 2) {
			userAgent = userAgentMozilla;
		} else if (randomNum == 3) {
			userAgent = userAgentIe;
		} else if (randomNum == 4) {
			userAgent = userAgentEdge;
		} else {
			userAgent = userAgentSafari;
		}

		Integer currentPage = 0;
		for (currentPage = fetchStartPage; currentPage <= fetchEndPage; currentPage++) {
		//@formatter:off
		StringBuffer URL = new StringBuffer("https://tw.buy.yahoo.com/search/product?p=")
							   .append(fetchProductName)
							   .append("&pg=").append(currentPage);

		Document xmlDoc = Jsoup
						  .connect(URL.toString())
						  .userAgent(userAgent)
						  .referrer(referrerYahooTW)
						  .get();

			// @formatter:on
			// Fetch product's links from query page
			Elements productLinks = xmlDoc.select("li[data-imprsn] a[href]");
			ArrayList<String> links = new ArrayList<String>();

			if (!productLinks.isEmpty()) {
				Integer productNum = 1;

				for (Element element : productLinks) {
					String productLink = element.attr("href");
					System.err.printf("P.%d___No.%d___\r", currentPage, productNum++);
					System.err.println(productLink);
					links.add(productLink);
				}

				// Fetch product info from each links
				//@formatter:off
				Document productXmlDoc = Jsoup
						.connect(links.get(0))
						.userAgent(userAgent)
						.referrer(referrerYahooTW)
						.get();
					
				// @formatter:off
//				Elements name = productXmlDoc.select(".HeroInfo__title___2cEgL HeroInfo__textTooLong___39Fck");
//				Elements price = productXmlDoc.select(".HeroInfo__mainPrice___H9A5r");
				Elements information = productXmlDoc.select("#isoredux-data");// data-state
//				Elements imageLink = productXmlDoc.select("meta[property=og:image]");// content
				
//				ProductBean product = new ProductBean();				
				
//				if (!name.isEmpty() && !price.isEmpty() && !information.isEmpty() && !information.isEmpty()) {
//					for (Element element : name) {
//						product.setName(element.text());
//					}
//					for (Element element : price) {
//						product.setPrice(Integer.valueOf(element.text().replace('$', ' ').trim()));
//					}
					for (Element element : information) {
						result = element.attr("data-state");
					}
//					for (Element element : imageLink) {
//						HashMap<String, String> hashMap = new HashMap<String, String>();
//						hashMap.put(element.cssSelector(), element.attr("content"));
//						product.setImageLink(hashMap);
//					}
//				}
//				Elements description = productXmlDoc.select(".ShoppingProductFeatures__productFeatureWrapper___1D0EZ li");
//				if (!description.isEmpty()) {
//					for (Element element : description) {
//							element.text();
//					}
//				}
				
//				System.err.printf("name= %s\r" 
//								+ "price= %s\r" 
//								+ "description= %s\r" 
//								+ "information= %s\r"
//								+ "imageLink= %s\r", name.text(), price.text(), description.text(), information.attr("data-state"), imageLink.attr("content"));
				
			}
		}

		return result;
	}

	// Get Products
	public ProductBean findProductInfos(String productLink, String productType, int userAgentNo) {

		String userAgent;
		if (userAgentNo == 1) {
			userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14931";
		} else if (userAgentNo == 2) {
			userAgent = "Chrome (AppleWebKit/537.1; Chrome50.0; Windows NT 10) AppleWebKit/537.36 (KHTML like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393";
		} else if (userAgentNo == 3) {
			userAgent = "Mozilla/5.0 (Windows NT 10; WOW64; Trident/7.0; AS; rv:11.0) like Gecko";
		} else {
			userAgent = "W3C-checklink/4.5 [4.160] libwww-perl/5.823";
		}

		StringBuffer URL = new StringBuffer("https://www.amazon.com/dp/").append(productLink).append("?language=en_US");

		ProductBean productBean = new ProductBean();

		try {
			Document doc = Jsoup.connect(URL.toString()).userAgent(userAgent).referrer("http://www.google.com")
					.timeout(60000).get();

			System.out.println("Fetch From: " + URL.toString() + " ...");

			Elements name = doc.select("#imgTagWrapperId img");
			if (!name.isEmpty()) {
				System.err.println(name.get(0).attr("alt"));
				productBean.setName(name.get(0).attr("alt"));
			} else {
				return null;
			}

			Elements brand = doc.select("#bylineInfo");
			if (!brand.isEmpty()) {
				System.err.println(brand.get(0).text());
				productBean.setBrand(brand.get(0).text());
			} else {
				return null;
			}

			Elements price = doc.select("#priceblock_ourprice");
			Elements price2 = doc.select("#priceblock_dealprice");
			if (!price.isEmpty()) {
				System.err.println(price.get(0).text().replace('$', ' ').trim());
				Integer USD = Math.round(Float.valueOf(price.get(0).text().replace('$', ' ').trim()));
				Integer TWD = USD * 31;
				productBean.setPrice(TWD);
			} else if (!price2.isEmpty()) {
				System.err.println(price2.get(0).text().replace('$', ' ').trim());
				Integer USD = Math.round(Float.valueOf(price2.get(0).text().replace('$', ' ').trim()));
				Integer TWD = USD * 31;
				productBean.setPrice(TWD);
			} else {
				return null;
			}

			ArrayList<String> arrayList = new ArrayList<String>();
			Elements discriptions = doc.select("#feature-bullets .a-unordered-list .a-list-item");
			if (!discriptions.isEmpty()) {
				System.err.println("discriptions");
				for (Element discription : discriptions) {
					arrayList.add(discription.text());
				}
				productBean.setDescription(arrayList);
			} else {
				return null;
			}

			HashMap<String, String> hashMap = new HashMap<String, String>();
			Elements imageLink = doc.select("img[data-old-hires]");
			if (!imageLink.isEmpty()) {
				System.err.println(imageLink.get(0).attr("data-old-hires"));
				hashMap.put("alt", productBean.getName());
				hashMap.put("src", imageLink.get(0).attr("data-old-hires"));
				productBean.setImageLink(hashMap);
			} else {
				return null;
			}

			productBean.setStock(0);
			productBean.setType(productType);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return productBean;
	}

	// Get Links

	/**
	 * @param inputQuery Product name
	 * @param startPage  Page start, can not be null.
	 * @param endPage    Page end , can be null, default is startPage.
	 * @return ArrayList of links.
	 */
	public ArrayList<String> findProductLinks(String inputQuery, Integer startPage, Integer endPage, int userAgentNo) {
		String userAgent;
		if (userAgentNo == 1) {
			userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14931";
		} else if (userAgentNo == 2) {
			userAgent = "Chrome (AppleWebKit/537.1; Chrome50.0; Windows NT 10) AppleWebKit/537.36 (KHTML like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393";
		} else if (userAgentNo == 3) {
			userAgent = "Mozilla/5.0 (Windows NT 10; WOW64; Trident/7.0; AS; rv:11.0) like Gecko";
		} else {
			userAgent = "W3C-checklink/4.5 [4.160] libwww-perl/5.823";
		}

		if (endPage == null) {
			endPage = startPage;
		} else if (startPage == null || inputQuery == null) {
			System.err.println("Please enter startPage and inputQuery!");
			return null;
		}

		ArrayList<String> list = new ArrayList<String>();
		for (int page = startPage; page <= endPage; page++) {
			StringBuffer URL = new StringBuffer("https://www.amazon.com/s?k=").append(inputQuery).append("&page=")
					.append(page);
			try {
				Document doc = Jsoup.connect(URL.toString()).userAgent(userAgent).referrer("http://www.google.com")
						.timeout(5000).get();

				System.out.println("Fetch From: " + URL.toString() + " ...");

				Elements link = doc.select("div[data-index]");
				for (int nodeCount = 0; nodeCount < link.size(); nodeCount++) {
					list.add(link.get(nodeCount).attr("data-asin"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
