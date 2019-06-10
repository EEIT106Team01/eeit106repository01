package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	final private String logPath = "C:\\Users\\Public\\";

	@Test
	public void testYahooProductCrawler() {
		String fetchProductName = null;
		String fetchProductType = null;
		String productType = null;
		Integer fetchStartPage = null;
		Integer fetchEndPage = null;
		Integer productStock = null;

		Scanner scanner = new Scanner(System.in);

		// @formatter:off
				System.out.println("Choose the product you want to fetch:"
						+ "\r1.行車紀錄器"
						+ "\r2.高壓清洗機及配件"
						+ "\r3.導航"
						+ "\r4.行車導航週邊配件"
						+ "\r5.安全帽"
						+ "\r6.車內用品"
						+ "\r7.推車週邊/汽座週邊"
						+ "\r8.充電器"
						+ "\r9.汽車隔熱/遮陽"
						+ "\r10.改裝汽車用品"
						+ "\r11.機車部品及人身配件"
						+ "\r12.測速器"
						+ "\r13.固定座"
						+ "\r14.輪胎周邊"
						+ "\r15.行車智慧穿戴"
						+ "\r16.汽車電力"
						+ "\r17.千斤頂"
						+ "\r18.胎壓偵測器");
				// @formatter:on
		switch (scanner.nextInt()) {
		case 1:
			fetchProductName = "行車紀錄器";
			fetchProductType = "行車紀錄器";
			break;

		case 2:
			fetchProductName = "高壓清洗機及配件";
			fetchProductType = "高壓清洗機及配件";
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
		}

		System.out.println("Enter the page to strat: ");
		if (scanner.hasNext()) {
			fetchStartPage = Integer.valueOf(scanner.next());
			scanner.reset();
		}

		System.out.println("Enter the page to end: ");
		if (scanner.hasNext()) {
			fetchEndPage = Integer.valueOf(scanner.next());
			scanner.reset();
		}

		System.out.println("Enter the stock: ");
		if (scanner.hasNext()) {
			productStock = Integer.valueOf(scanner.next());
			scanner.reset();
		}

		int count = 0;
		int countFetchFail = 0;
		int countInsetFail = 0;

		if (fetchProductType == null) {
			fetchProductType = "";
			productType = fetchProductName;

		} else if (fetchProductType.equalsIgnoreCase("3C用品固定架%2F固定座")) {
			productType = "3C用品固定架和固定座";
		} else {
			productType = fetchProductType;
		}

		ShopCrawler crawler = new ShopCrawler();

		// @formatter:off
				List<String> links = crawler.YahooProductLinksCrawler(fetchProductName, fetchStartPage, fetchEndPage, fetchProductType);
				// @formatter:on

		scanner.close();

		HashMap<String, String> errors = new HashMap<String, String>();

		if (links != null) {
			for (String link : links) {
				ProductBean productBean = ShopCrawler.yahooProductCrawler(link, productType, productStock);
				ProductBean result = productService.insertProduct(productBean);
				count++;
				if (result != null) {
					System.out.println("Database already have: [" + result.getId().toString() + "] products");
				} else if (productBean == null) {
					errors.put("Fetch Fail No." + count, "Link: " + link);
					countFetchFail++;
				} else {
					errors.put("Insert Fail No." + count, "ProductBean: " + productBean.toString());
					countInsetFail++;
				}
			}
			if (errors != null && errors.size() > 0) {
				SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy_hh.mm.ss");
				try {
					File file = new File(logPath);
					if (file.exists() == false) {
						file.createNewFile();
					}
					String writePath = logPath + "crawler-fail-log_"
							+ format.format(new Date(System.currentTimeMillis())) + ".txt";
					PrintWriter writer = new PrintWriter(writePath);

					Iterator<Entry<String, String>> iterator = errors.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<String, String> entry = (Entry<String, String>) iterator.next();
						writer.write(entry.getKey() + " " + entry.getValue() + "\r");
					}
					System.err.println("FailLog Created, path: " + writePath);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// @formatter:off
					System.out.println("Total attempt:[" + count + "] Inserted:[" + (count - countFetchFail - countInsetFail) + "]");
					// @formatter:on
			System.err.println("Total Fetch Fail:[" + countFetchFail + "]");
			System.err.println("Total Insert Fail:[" + countInsetFail + "]");
		} else {
			System.err.println("Fetch Links Fail!");
		}
	}

//	@Test
	public void insertSN() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDay = null;
		Date endDay = null;
		try {
			startDay = format.parse("2019-05-24");
			endDay = format.parse("2019-05-25");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<ProductBean> productBeans = productService.findProductsByUpdatedTime(null, null, startDay, endDay, null);
		Iterator<ProductBean> iterator = productBeans.iterator();
		while (iterator.hasNext()) {
			productService.insertProductsSN(iterator.next().getId(), 3);
		}
	}

}
