package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.util.List;
import java.util.Scanner;

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
	// @formatter:off
	public void testYahooProductDetailsCrawler() {
		String fetchProductName = null;
		String productType = null;
		Integer fetchStartPage = null;
		Integer fetchEndPage = null;
		Integer productStock = null;
		
		Scanner scanner = new Scanner(System.in);
		System.err.println("Enter the product name: (press enter to commit)");
		if (scanner.hasNext()) {
			fetchProductName = scanner.next();
			scanner.reset();
		}
	
		System.err.println("Enter the product type you are going to fetch: ");
		if (scanner.hasNext()) {
			productType = scanner.next();
			scanner.reset();
		}
		
		System.err.println("Enter the page to strat: ");
		if (scanner.hasNext()) {
			fetchStartPage = Integer.valueOf(scanner.next());
			scanner.reset();
		}
		
		System.err.println("Enter the page to end: ");
		if (scanner.hasNext()) {
			fetchEndPage = Integer.valueOf(scanner.next());
			scanner.reset();
		}

		System.err.println("Enter the stock: ");
		if (scanner.hasNext()) {
			productStock = Integer.valueOf(scanner.next());
			scanner.reset();
		}
		
		ShopCrawler crawler = new ShopCrawler();
		int count = 0;
		int countFail = 0;
		List<String> links = crawler.YahooProductLinksCrawler(fetchProductName, fetchStartPage, fetchEndPage);
		scanner.close();
		
		if (links != null) {
			for (String link : links) {
				ProductBean productBean = crawler.YahooProductDetailsCrawler(link, productType, productStock);
				ProductBean result = productService.insertProduct(productBean);
				if (result != null) {
					count++;
					System.out.println(result.toString());
				} else {
					countFail++;
					System.err.println("Insert Fail!");
				}
			}
			System.out.println("Total Insert:" + count);
			System.err.println("Total Fail:" + countFail);
		} else {
			System.err.println("Fetch Links Fail!");
		}
	}
	
}
