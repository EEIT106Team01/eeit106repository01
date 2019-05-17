package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AmazonCrawler {

	public static void main(String[] args) {
		AmazonCrawler crawler = new AmazonCrawler();
		Iterator<String> iterator = crawler.findProductLinks("car recorder", 2).iterator();
		while (iterator.hasNext()) {
			System.out.println(crawler.findProductInfos((String) iterator.next()));
		}

	}

	// Get Products
	public String findProductInfos(String productLink) {
		StringBuffer URL = new StringBuffer("https://www.amazon.com/dp/").append(productLink).append("?language=zh_TW");
//				.append("#productDetails");
		Element name = null;
		Element price = null;
		Elements discriptions = null;
//		Elements informations = null;
		try {
			Document doc = Jsoup.connect(URL.toString()).userAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64;zh_TW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
					.referrer("http://www.google.com").timeout(99999999).get();

			System.out.println("\r\rFetch From: " + URL.toString() + " ..........");

			name = doc.selectFirst("#imgTagWrapperId img");
//			System.out.println("name: " + name.attr("alt"));

			price = doc.selectFirst("#priceblock_ourprice");
//			System.out.println("price: " + price.text());

			discriptions = doc.select("#feature-bullets .a-unordered-list .a-list-item");
			for (Element discription : discriptions) {
				if (discription.text().equals(discriptions.get(0).text())) {
//					System.out.print("discriptions: " + discription.text());
				}
//				System.out.print(discription.text());
			}

//			informations = doc.select(".a-row table .a-size-base");
//			for (Element information : informations) {
//				if (information.text().equals(informations.get(0).text())) {
//					System.out.print("informations: " + information.text());
//				} else {
//					System.out.print(information.text());
//				}
//			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return name.attr("alt");
	}

	// Get Links
	public ArrayList<String> findProductLinks(String inputQuery, Integer inputPage) {
		StringBuffer URL = new StringBuffer("https://www.amazon.com/s?k=").append(inputQuery).append("&page=")
				.append(inputPage).append("&language=zh_TW");
		ArrayList<String> list = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect(URL.toString()).userAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64;zh_TW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
					.referrer("http://www.google.com").timeout(99999999).get();

			System.out.println("Fetch From: " + URL.toString() + " ..........");

			Elements link = doc.select("div[data-index]");
			for (int nodeCount = 0; nodeCount < link.size(); nodeCount++) {
//				System.out.println("=========================" + nodeCount + "=========================");
//				System.out.println("LINK: " + "https://www.amazon.com/dp/" + link.get(nodeCount).attr("data-asin"));
				list.add(link.get(nodeCount).attr("data-asin"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
