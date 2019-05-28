package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ddns.eeitdemo.eeit106team01.shop.ShopTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseServiceTest extends ShopTest {

	@Autowired
	PurchaseService purchaseService;

	@Autowired
	ProductService productService;

	private HashMap<ProductBean, Integer> purchaseProduct = new HashMap<ProductBean, Integer>();
	private Date date = NewDate.newCurrentTime();

	@Test
	public void testNewPurchase() throws Exception {
		Member member = new Member();
		member.setId(60L);

		purchaseProduct.put(productService.findProductByPrimaryKey(1L), 2);
		purchaseProduct.put(productService.findProductByPrimaryKey(5L), 1);
		purchaseProduct.put(productService.findProductByPrimaryKey(9L), 1);

		Integer productTotalPrice = (productService.findProductByPrimaryKey(1L).getPrice() * 2)
				+ productService.findProductByPrimaryKey(5L).getPrice()
				+ productService.findProductByPrimaryKey(9L).getPrice();

		HashMap<String, String> receiverInformation = new HashMap<String, String>();
		receiverInformation.put("receiver", "Alex");
		receiverInformation.put("address", "Neihu district");

		PurchaseBean purchaseBean = new PurchaseBean("unpaid", date, date, productTotalPrice, "unsend", "[7-11]", 25,
				receiverInformation, member);
//		purchaseService.newPurchase(purchaseProduct, member.getId(), purchaseBean);
	}

}
