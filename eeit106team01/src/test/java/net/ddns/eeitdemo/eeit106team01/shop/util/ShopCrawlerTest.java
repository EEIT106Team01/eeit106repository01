package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCrawlerTest {

	@Autowired
	private ProductService productService;

	@Test
	public void testYahooProductDetailsCrawler() {

		String fetchProductName = "行車紀錄器";
		String productType = "行車紀錄器";

		Integer fetchStartPage = 12;
		Integer fetchEndPage = 15;

		Integer productStock = 0;

		ShopCrawler crawler = new ShopCrawler();
		int count = 0;
		// @formatter:on
		// Get Links
		// Get Details
		List<String> links = crawler.YahooProductLinksCrawler(fetchProductName, fetchStartPage, fetchEndPage);
		if (links != null) {
			for (String link : links) {
				ProductBean productBean = crawler.YahooProductDetailsCrawler(link, productType, productStock);
				ProductBean result = productService.insertProduct(productBean);
				if (result != null) {
					count++;
					System.out.println(result.toString());
					System.out.println("Insert No." + count);
				} else {
					System.err.println("Insert Fail!");
				}
			}
			System.err.println("Total Insert:" + count);
		} else {
			System.err.println("Fetch Links Fail!");
		}
	}

}
