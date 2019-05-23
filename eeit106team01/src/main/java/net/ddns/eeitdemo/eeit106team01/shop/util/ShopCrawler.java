package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	private String userAgentChrome = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
	private String userAgentMozilla = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";
	private String userAgentIe = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko";
	private String userAgentEdge = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.9200";
	private String userAgentSafari = "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25";

	// Referrer
	private String referrerGoogle = "https://www.google.com/";
	private String referrerYahooTW = "https://tw.yahoo.com/";

	//@formatter:off
	public ProductBean YahooProductDetailsCrawler(String link, String productType, Integer productStock) {
		
		if (link != null && productType != null && productStock != null) {
		
		ProductBean productBean = new ProductBean();
		productBean.setCreateTime();
		productBean.setUpdatedTime();
		productBean.setStock(productStock);
		productBean.setType(productType);
		productBean.setTotalSold(0);
		
			// @formatter:on
			// Random User Agent
			String userAgent;
			String referrer;
			Integer randomNum = (int) (Math.random() * 5 + 1);
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

			// System.out.println("UserAgent: " + userAgent + "...");

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
			e.printStackTrace();
		}

		Element element = productXmlDoc.selectFirst("#isoredux-data");// data-state
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(element.attr("data-state")).getAsJsonObject();

		String name = ((JsonObject) ((JsonObject) jsonObject.get("ecgql")).get("gqlItemPage")).get("title").getAsString();
		productBean.setName(name);

		String brandString = ((JsonObject) ((JsonObject) jsonObject.get("ecgql")).get("gqlItemPage")).get("title").getAsString();
		int firstSpace = brandString.replace('【', ' ').trim().replace('】', ' ').indexOf(" ");
		String brand = null;
		if (firstSpace < 1) {
			brand = brandString.replace('【', ' ').trim().replace('】', ' ').substring(0, 3).trim();
		} else {
			brand = brandString.replace('【', ' ').trim().replace('】', ' ').substring(0, firstSpace).trim();
		}
		productBean.setBrand(brand);

		String priceString = ((JsonObject) ((JsonObject) jsonObject.get("ecgql")).get("gqlItemPage")).get("currentPrice").getAsString();
		Integer price = new Integer(priceString);
		productBean.setPrice(price);

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

		JsonObject information = ((JsonObject) ((JsonObject) jsonObject.get("ecgql")).get("gqlItemPage")).get("detailDescription").getAsJsonObject();
		String infoJsonString = information.get("specifics").toString()
				.replace(" ", "").replace("\\r", "").replace("\\n", "").replace("\\t", "").replaceAll("(<tbody>|</tbody>)", "")
				.replaceAll("(<p>|</p>)", "").replaceAll("\"", "").replace("\\", "").replaceAll("<table>", "{")
				.replaceAll("(<tr>|</tr>|</th>)", "").replaceAll("</td>", "\",").replaceAll(",</table>", "}")
				.replaceAll("<th>", "\"").replaceAll("<td>", "\":\"").replace(" ", "").replace(" ", "").trim();
		JsonObject infoJson;
			if (infoJsonString.contains("<ul>")) {
				String infoJsonStringHaveUl = infoJsonString.replaceAll("<ul>", "{").replaceAll("</li>", "\",").replaceAll(",</ul>", "}").replaceAll("<li>", "\"").replaceAll("：", "\":\"").replace(" ", "").replace(" ", "").trim();
				System.out.println(infoJsonStringHaveUl);
				infoJson = jsonParser.parse(infoJsonStringHaveUl).getAsJsonObject();
			}else if (infoJsonString.contains("<br/>")) {
				String infoJsonStringHaveBr = "{\"" + infoJsonString.replaceAll("<br/>", "\",\"").replaceAll(":", "\":\"") + "\"}";
				System.out.println(infoJsonStringHaveBr);
				infoJson = null;
				try {
					infoJson = jsonParser.parse(infoJsonStringHaveBr).getAsJsonObject();
				} catch (JsonSyntaxException e) {
					return null;
				}
			} else {
				System.out.println(infoJsonString);
				infoJson = jsonParser.parse(infoJsonString).getAsJsonObject();
			}
		HashMap<String, String> infoJsonMap = new Gson().fromJson(infoJson, new TypeToken<HashMap<String, String>>() { private static final long serialVersionUID = 633439657945276680L; }.getType());
		productBean.setInformation(infoJsonMap);

		HashMap<Integer, String> infoImagesMap = new HashMap<Integer, String>();
		for (int count = 1; count < count + 1; count++) {
			String infoImages = information.get("longDescription").toString();
			int indexOfTag = StringUtils.ordinalIndexOf(infoImages, "<img src=", count);
			if (indexOfTag > 0) {
				String currentTagFull = infoImages.substring(indexOfTag);
				String currentTagCut = currentTagFull.substring(currentTagFull.indexOf("<img src="), currentTagFull.indexOf(">"));
				String infoImagesresult = currentTagCut
						.substring(currentTagCut.indexOf("\""), StringUtils.ordinalIndexOf(currentTagCut, "\"", 2))
						.replaceAll("\"", "").replace('\\', ' ').trim();
				infoImagesMap.put(count, infoImagesresult);
			} else {
				break;
			}
		}
		productBean.setInformationImageLink(infoImagesMap);
		
			return productBean;
		}
		return null;
	}

	// @formatter:off
	public List<String> YahooProductLinksCrawler(String fetchProductName, Integer fetchStartPage, Integer fetchEndPage) {
		List<String> links = new ArrayList<String>();
		// @formatter:on
		// Random User Agent
		String userAgent;
		String referrer;
		Integer randomNum = (int) (Math.random() * 5 + 1);
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

		Integer currentPage = 0;
		for (currentPage = fetchStartPage; currentPage <= fetchEndPage; currentPage++) {
			//@formatter:off
			StringBuffer URL = new StringBuffer("https://tw.buy.yahoo.com/search/product?p=")
								   .append(fetchProductName)
								   .append("&pg=")
								   .append(currentPage);

			Document xmlDoc = null;
			try {
				xmlDoc = Jsoup
						.connect(URL.toString())
						.userAgent(userAgent)
						.referrer(referrer)
						.timeout(60000)
						.get();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// @formatter:on
			// Fetch product's links from query page
			Elements productLinks = xmlDoc.select("li[data-imprsn] a[href]");

			if (!productLinks.isEmpty()) {
				@SuppressWarnings("unused")
				Integer productNum = 1;
				for (Element element : productLinks) {
					String productLink = element.attr("href");
					// System.out.printf("P.%d___No.%d___\r", currentPage, productNum++);
					links.add(productLink);
				}
			}
		}
		return links;
	}

}
