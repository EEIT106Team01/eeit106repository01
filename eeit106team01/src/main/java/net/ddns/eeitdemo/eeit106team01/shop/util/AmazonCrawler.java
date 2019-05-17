package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AmazonCrawler {

	public static void main(String[] args) {
		String inputQuery = "car recorder";
		Integer inputPage = 2;
		// .replaceAll(" ", "+")
		StringBuffer URL = new StringBuffer("https://www.amazon.com/s?k=").append(inputQuery).append("&page=")
				.append(inputPage).append("&language=zh_TW");

		try {
			Document doc = Jsoup.connect(URL.toString())
					.userAgent(
							"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com").timeout(5000).get();

			System.out.println("Fetch From: " + URL.toString() + " ..........");

			// NAME
			Elements name = doc.select("div[data-index] img");
			// PRICE
			Elements price = doc.select("div[data-index] span[data-a-color=base] .a-offscreen");
			// SRC
			// SRCSET
			// LINK
			Elements link = doc.select("div[data-index] span[data-component-type=s-product-image] a");
			for (int nodeCount = 2; nodeCount < 20; nodeCount++) {
				System.out.println("=========================" + nodeCount + "=========================");
				System.out.println("NAME: " + name.get(nodeCount).attr("alt"));
				System.out.println("PRICE: " + price.get(nodeCount).text());
				System.out.println("SRC: " + name.get(nodeCount).attr("src"));
				System.out.println("SRCSET: " + name.get(nodeCount).attr("srcset"));
				System.out.println("LINK: " + "https://www.amazon.com" + link.get(nodeCount).attr("href"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
