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

//	@Test
	public void testReplace() {
		String string = "\"\r\n" + "<table>\\r\\n\\t\r\n" + "    <tbody>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t品牌</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t領先者</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t型號</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\tES-15</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t機身尺寸</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t約300*82*15 mm(不含鏡頭)</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t重量</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t約230g</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n"
				+ "            <th>\\r\\n\\t\\t\\t\\t行車記錄器類型</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t後視鏡型</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t錄影角度</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t161度以上</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t特殊功能</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\tG-sensor碰撞感測器 、 停車監控/移動偵測錄影 、 前車碰撞預警</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t錄影模式</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t1080P</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t螢幕尺寸</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t4.3吋大螢幕</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t鏡頭光圈</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\tF1.8大光圈</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t記憶卡擴充</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t最高支援32G (MicroSD)</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t電池容量</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t180mAh</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n"
				+ "            <th>\\r\\n\\t\\t\\t\\tBSMI認證碼</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\tD33F93</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t注意事項</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t如有問題歡迎聯繫泓愷科技02-22868766，www.top-wipro.com</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t內容物包裝</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\tES-15主機.後鏡頭x1.說明書x1.車充線x1.後拉鏡頭線x1.專用扣環x2</td>\\r\\n\\t\\t</tr>\\r\\n\\t\\t\r\n"
				+ "        <tr>\\r\\n\\t\\t\\t\r\n" + "            <th>\\r\\n\\t\\t\\t\\t保固</th>\\r\\n\\t\\t\\t\r\n"
				+ "            <td>\\r\\n\\t\\t\\t\\t主機180天/配件3個月(本產品已投保2000萬產品責任險)</td>\\r\\n\\t\\t</tr>\\r\\n\\t</tbody>\\r\\n</table>\\r\\n\r\n"
				+ "<p>\\r\\n\\t </p>\"";

		System.out.println(
				string.replace(" ", "").replace("\\r", "").replace("\\n", "").replace("\\t", "").replaceAll("(<tbody>|</tbody>)", "")
						.replaceAll("(<p>|</p>)", "").replaceAll("\"", "").replace("\\", "").replaceAll("<table>", "{")
						.replaceAll("(<tr>|</tr>|</th>)", "").replaceAll("</td>", "\",").replaceAll(",</table>", "}")
						.replaceAll("<th>", "\"").replaceAll("<td>", "\":\"").replace(" ", "").trim());
	}

}
