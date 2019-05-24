package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.util.Iterator;
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

//	@Test
	// @formatter:off
	public void testYahooProductDetailsCrawler() {
		String fetchProductName = null;
		String fetchProductType = null;
		String productType = null;
		Integer fetchStartPage = null;
		Integer fetchEndPage = null;
		Integer productStock = null;
		
		Scanner scanner = new Scanner(System.in);
		/*System.err.println("Enter the product name: (enter words or num)");
		if (scanner.hasNext()) {
			fetchProductName = scanner.next();
			scanner.reset();
		}*/
		
		System.err.println("Choose the product you want to fetch: (enter number)");
		switch (scanner.nextInt()) {
		case 1:
			fetchProductName = "行車紀錄器";
			fetchProductType = "行車紀錄器";
			break;

		case 2:
			fetchProductName = "行車紀錄器";
			fetchProductType = "行車紀錄器";
			break;

		case 3:
			fetchProductName = "導航";
			fetchProductType = "衛星導航";
			break;

		case 4:
			fetchProductName = "行車導航週邊配件";
			fetchProductType = "行車導航週邊配件";
			break;
		case 5:
			fetchProductName = "安全帽";
			fetchProductType = "安全帽";
			break;

		case 6:
			fetchProductName = "車內用品";
			fetchProductType = "車內用品";
			break;

		case 7:
			fetchProductName = "車內用品";
			fetchProductType = "推車週邊/汽座週邊";
			break;

		case 8:
			fetchProductName = "車內用品";
			fetchProductType = "充電器";
			break;

		case 9:
			fetchProductName = "遮陽";
			fetchProductType = "汽車隔熱/遮陽";
			break;

		case 10:
			fetchProductName = "改裝汽車用品";
			fetchProductType = "改裝汽車用品";
			break;

		case 11:
			fetchProductName = "機車部品及人身配件";
			break;

		case 12:
			fetchProductName = "測速器";
			fetchProductType = "測速器";
			break;

		case 13:
			fetchProductName = "固定座";
			fetchProductType = "3C用品固定架%2F固定座";
			break;

		case 14:
			fetchProductName = "輪胎周邊";
			break;

		case 15:
			fetchProductName = "行車智慧穿戴";
			fetchProductType = "智慧穿戴";
			break;

		case 16:
			fetchProductName = "汽車電力";
			break;

		case 17:
			fetchProductName = "千斤頂";
			break;

		case 18:
			fetchProductName = "胎壓偵測器";
			fetchProductType = "胎壓偵測器";
			break;

		case 19:
			fetchProductName = "高壓清洗機及配件";
			fetchProductType = "高壓清洗機及配件";
			break;
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
		
		if (fetchProductType == null) {
			fetchProductType = "";
			productType = fetchProductName;
		} else {
		    productType = fetchProductType;			
		} 
		
		List<String> links = crawler.YahooProductLinksCrawler(fetchProductName, fetchStartPage, fetchEndPage, fetchProductType);
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
					System.err.println("Insert Fail! No." + countFail);
				}
			}
			System.out.println("Total Insert:" + count);
			System.err.println("Total Fail:" + countFail);
		} else {
			System.err.println("Fetch Links Fail!");
		}
	}
	
	@Test
	public void insertSN() {
		List<ProductBean> productBeans = productService.findProductsByUpdatedTime(-1);
		Iterator<ProductBean> iterator = productBeans.iterator();
		while (iterator.hasNext()) {
			productService.insertProductsSN(iterator.next().getId(), 3);
		}
	}
	
}
