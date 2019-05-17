package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;

public class AmazonCrawler {

	public static void main(String[] args) {
		AmazonCrawler crawler = new AmazonCrawler();
		Iterator<String> iterator = crawler.findProductLinks("car recorder", 3).iterator();
		int i = 0;
		while (iterator.hasNext()) {
			System.out.println("___" + (i++) + "___");
			String string = (String) iterator.next();
			System.out.println(string);
		}

	}

	// Get Products
	public ProductBean findProductInfos(String productLink) {
		StringBuffer URL = new StringBuffer("https://www.amazon.com/dp/").append(productLink).append("?language=zh_TW");

		ProductBean productBean = new ProductBean();

		try {
			Document doc = Jsoup.connect(URL.toString()).userAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
					.referrer("http://www.google.com").timeout(5000).get();

			System.out.println("Fetch From: " + URL.toString() + " ...");

			Element name = doc.selectFirst("#imgTagWrapperId img");
			productBean.setName(name.attr("alt"));

			Element brand = doc.selectFirst("div[data-brand]");
			productBean.setBrand(brand.attr("data-brand"));

			Element price = doc.selectFirst("#priceblock_ourprice");
			productBean.setPrice(Integer.valueOf(price.text().replaceAll("USD", "").trim()));

			ArrayList<String> arrayList = new ArrayList<String>();
			Elements discriptions = doc.select("#feature-bullets .a-unordered-list .a-list-item");
			for (Element discription : discriptions) {
				arrayList.add(discription.text());
			}
			productBean.setDescription(arrayList);

			productBean.setCreateTime();
			productBean.setUpdatedTime();
			productBean.setStock(5);
//		productBean.setTotalSold();
//		productBean.setType("");
//		productBean.setImageLink(imageLink);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return productBean;
	}

	// Get Links
	public ArrayList<String> findProductLinks(String inputQuery, Integer inputPage) {
		StringBuffer URL = new StringBuffer("https://www.amazon.com/s?k=").append(inputQuery).append("&page=")
				.append(inputPage).append("&language=zh_TW");
		ArrayList<String> list = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect(URL.toString()).userAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
					.referrer("http://www.google.com").timeout(5000).get();

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
		return list;
	}
}
