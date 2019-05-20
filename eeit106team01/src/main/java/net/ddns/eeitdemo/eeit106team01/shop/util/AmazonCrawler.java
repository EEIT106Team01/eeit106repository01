package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;

public class AmazonCrawler {

	// Get Products
	public ProductBean findProductInfos(String productLink, String productType, int userAgentNo) {
		String userAgent;
		if (userAgentNo == 1) {
			userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14931";
		} else if (userAgentNo == 2) {
			userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:64.0) Gecko/20100101 Firefox/64.0";
		} else if (userAgentNo == 3) {
			userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36";
		} else {
			userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
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
			userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:64.0) Gecko/20100101 Firefox/64.0";
		} else if (userAgentNo == 3) {
			userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36";
		} else {
			userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
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
//				System.out.println("=========================" + nodeCount + "=========================");
//				System.out.println("LINK: " + "https://www.amazon.com/dp/" + link.get(nodeCount).attr("data-asin"));
					list.add(link.get(nodeCount).attr("data-asin"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
