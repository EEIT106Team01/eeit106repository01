package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;

public class ShopCrawler {

	// UserAgents
	private static String userAgentChrome = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
	private static String userAgentMozilla = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";
	private static String userAgentIe = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko";
	private static String userAgentEdge = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.9200";
	private static String userAgentSafari = "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25";

	// Referrer
	private static String referrerGoogle = "https://www.google.com/";
	private static String referrerYahooTW = "https://tw.yahoo.com/";

	// Util
	private static Random random = new Random();
	private static Logger logger;

	public static ProductBean yahooProductCrawler(String link, String productType, Integer productStock) {

		flag: if (link != null && productType != null && productStock != null) {

			// Prepare new ProductBean
			Date date = new Date(System.currentTimeMillis());
			ProductBean productBean = new ProductBean();
			productBean.setCreateTime(date);
			productBean.setUpdatedTime(date);
			productBean.setStock(productStock);
			productBean.setType(productType);
			productBean.setTotalSold(0);

			// Random User Agent
			String userAgent;
			String referrer;

			Integer randomNum = random.nextInt(5) + 1;
			if (randomNum == 1) {
				userAgent = userAgentChrome;
				referrer = referrerGoogle;
			} else if (randomNum == 2) {
				userAgent = userAgentMozilla;
				referrer = referrerYahooTW;
			} else if (randomNum == 3) {
				userAgent = userAgentIe;
				referrer = referrerGoogle;
			} else if (randomNum == 4) {
				userAgent = userAgentEdge;
				referrer = referrerYahooTW;
			} else {
				userAgent = userAgentSafari;
				referrer = referrerYahooTW;
			}

		// @formatter:off
		// Fetch product info from each links
		Document productXmlDoc = null;
		try {
			productXmlDoc = Jsoup.connect(link)
								 .userAgent(userAgent)
								 .referrer(referrer)
								 .timeout(60000)
								 .get();
		} catch (IOException e) {
			System.err.println(e.getMessage() + " Try again!");
			break flag;
		}
		
			// Fetch Json form YAHOO Mall
			Element element = productXmlDoc.selectFirst("#isoredux-data");
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(element.attr("data-state")).getAsJsonObject();

			// Get Name String
			String name = ((JsonObject) ((JsonObject) jsonObject.get("ecgql")).get("gqlItemPage")).get("title").getAsString();
			productBean.setName(name);


			// Get Price String
			String priceString = ((JsonObject) ((JsonObject) jsonObject.get("ecgql")).get("gqlItemPage")).get("currentPrice").getAsString();
			Integer price = new Integer(priceString);
			productBean.setPrice(price);

			// Get image links Array
			JsonArray imageLink = ((JsonObject) ((JsonObject) jsonObject.get("ecgql")).get("gqlItemPage")).get("images").getAsJsonArray();
			HashMap<Integer, String> imageLinkMap = new HashMap<Integer, String>();
			for (int i = 0; i < imageLink.size(); i++) {
				JsonArray imageLinkInnerArray = imageLink.get(i).getAsJsonArray();
				for (int j = 0; j < imageLinkInnerArray.size(); j++) {
					JsonObject imageLinkJson = imageLinkInnerArray.get(j).getAsJsonObject();
					String url = imageLinkJson.get("url").toString();
					imageLinkMap.put(j, url);
				}
			}
			productBean.setImageLink(imageLinkMap);

			// Get information String
			JsonObject information = ((JsonObject) ((JsonObject) jsonObject.get("ecgql")).get("gqlItemPage")).get("detailDescription").getAsJsonObject();
			JsonObject infoJson;
			String infoJsonString = information.get("specifics").toString().trim().replace('\"', ' ').replace("\\r", "").replace("\\n", "").replace("\\t", "")
											   .replace("\\", "").replaceAll("(<tbody>|</tbody>|<p>|</p>)", "").replace(" ", "");
			if (infoJsonString.contains("<ul>")) {
				infoJsonString = infoJsonString.replaceAll("<ul>", "{").replaceAll("<li>", "\"").replaceAll("：", "\":\"")
											   .replaceAll("</li>", "\",").replaceAll(",</ul>", "}");
				if (infoJsonString.contains("\\")) {
					infoJsonString = infoJsonString.replace('\\', ' ').replaceAll("  ", " ").replaceAll("  ", " ").trim();
				}
			} else if (infoJsonString.contains("<table>")) {
				infoJsonString = infoJsonString.replaceAll("<table>", "{").replaceAll("<tr><th>", "\"").replaceAll("</th><td>", "\":\"")
											   .replaceAll("</td></tr>", "\",").replaceAll(",</table>", "}");
				if (infoJsonString.contains("\\")) {
					infoJsonString = infoJsonString.replace('\\', ' ').replaceAll("  ", " ").replaceAll("  ", " ").trim();
				}
			} else {
				System.err.println(infoJsonString);
			}
			
			infoJson = null;
			try {
				infoJson = jsonParser.parse(infoJsonString).getAsJsonObject();
			} catch (JsonSyntaxException e) {
				System.err.println("Fail to parse infoJson");
				return null;
			}
			HashMap<String, String> infoJsonMap = 
			new Gson().fromJson(infoJson,new TypeToken<HashMap<String, String>>() {private static final long serialVersionUID = 633439657945276680L;}.getType());
			productBean.setInformation(infoJsonMap);

			// Get Brand String
			String brand = null;
			brand = infoJson.get("品牌").getAsString().trim().replace(" ","");
			productBean.setBrand(brand);
			
			// Get Information images link String
			HashMap<Integer, String> infoImagesMap = new HashMap<Integer, String>();
			for (int count = 1; count < count + 1; count++) {
				String infoImages = information.get("longDescription").toString();
				int indexOfTag = StringUtils.ordinalIndexOf(infoImages, "<img src=", count);
				if (indexOfTag > 0) {
					String currentTagFull = infoImages.substring(indexOfTag);
					String currentTagCut = currentTagFull.substring(currentTagFull.indexOf("<img src="),currentTagFull.indexOf(">"));
					String infoImagesresult = currentTagCut.substring(currentTagCut.indexOf("\""), StringUtils.ordinalIndexOf(currentTagCut, "\"", 2))
														   .replaceAll("\"", "").replace('\\', ' ').trim();
					infoImagesMap.put(count, infoImagesresult);
				} else {
					break;
				}
			}
			productBean.setInformationImageLink(infoImagesMap);
			//@formatter:on

			return productBean;
		}
		return null;
	}

	//@formatter:off
	public List<String> yahooProductLinksCrawler(String fetchProductName, Integer fetchStartPage, Integer fetchEndPage, String fetchProductType) {
	//@formatter:on

		List<String> links = new ArrayList<>();

		String userAgent;
		String referrer;
		Integer randomNum = random.nextInt(5) + 1;
		if (randomNum == 1) {
			userAgent = userAgentChrome;
			referrer = referrerGoogle;
		} else if (randomNum == 2) {
			userAgent = userAgentMozilla;
			referrer = referrerYahooTW;
		} else if (randomNum == 3) {
			userAgent = userAgentIe;
			referrer = referrerGoogle;
		} else if (randomNum == 4) {
			userAgent = userAgentEdge;
			referrer = referrerYahooTW;
		} else {
			userAgent = userAgentSafari;
			referrer = referrerYahooTW;
		}

		Integer pageWillFetch = 0;
		Integer totalCount = 0;
		flag: for (Integer currentPage = fetchStartPage; currentPage <= fetchEndPage; currentPage++) {

			//@formatter:off
			StringBuilder url = new StringBuilder("https://tw.buy.yahoo.com/search/product?")
								   .append("flc=" + fetchProductType)
								   .append("&p=" + fetchProductName)
								   .append("&pg=")
								   .append(currentPage);
			Document xmlDoc = null;
			try {
				xmlDoc = Jsoup.connect(url.toString())
							  .userAgent(userAgent)
							  .referrer(referrer)
							  .timeout(60000)
							  .get();
			} catch (IOException e) {
				System.err.println(e.getMessage() + " Try again!");
				break flag;
			}
			// @formatter:on

			// Fetch product's links from query page
			Elements productLinks = xmlDoc.select("li[data-imprsn] a[href]");

			if (!productLinks.isEmpty()) {
				System.out.println("Start Fetching......");
				System.out.println("[" + url.toString() + "]");
				pageWillFetch++;
				for (Element element : productLinks) {
					String productLink = element.attr("href");
					links.add(productLink);
					++totalCount;
				}
			}
		}
		System.out.printf("Total Page:%d, Products:%d\r", pageWillFetch, totalCount);
		return links;
	}

}
