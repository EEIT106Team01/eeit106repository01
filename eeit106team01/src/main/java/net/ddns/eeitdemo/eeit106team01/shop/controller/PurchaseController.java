package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.shop.ecpay.example.integration.AllInOne;
import net.ddns.eeitdemo.eeit106team01.shop.ecpay.example.integration.domain.AioCheckOutOneTime;
import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.ProductService;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.PurchaseService;
import net.ddns.eeitdemo.eeit106team01.shop.util.Converter;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;
import net.ddns.eeitdemo.eeit106team01.shop.util.SerialNumberGenerator;

@RestController
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberDAO memberDAO;
	
	private NewDate newDate = new NewDate();
	private static AllInOne all;
	private static final String IDILEGAL= "ID不合法";
	private static final String VALUEMISSED= "缺少必要值";
	private static final String DELIVERSTATUS = "deliverStatus";
	private static final String PAYSTATUS = "payStatus";
	private static final String PURCHASE = "purchase";

	private static void initial() {
		all = new AllInOne("");
	}

	// Get Method
	// Purchase
	@GetMapping(value = "/shop/findPurchaseById")
	public ResponseEntity<?> findPurchaseById(@RequestParam(defaultValue = "1") Long id,
			@RequestParam(defaultValue = PURCHASE) String idType) {
		List<PurchaseBean> result;
		if (id.longValue() <= 0) {
			return new ResponseEntity<>(IDILEGAL, HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findPurchaseById(id, idType);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/shop/findPurchaseByType")
	public ResponseEntity<?> findPurchaseByType(@RequestParam(defaultValue = "time") String type,
			@RequestParam(required = false, defaultValue = "1970-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDay,
			@RequestParam(required = false, defaultValue = "2019-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDay,
			@RequestParam(required = false) String stringValue, @RequestParam(required = false) Integer intValue) {
		List<PurchaseBean> result;
		if ((type.equalsIgnoreCase("time") && startDay == null && endDay == null)
				|| (type.equalsIgnoreCase(DELIVERSTATUS) && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("deliverType") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase(PAYSTATUS) && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("price") && (intValue == null || intValue.intValue() < 0))
				|| (type.equalsIgnoreCase("priceLower") && (intValue == null || intValue.intValue() < 0))
				|| (type.equalsIgnoreCase("priceHigher") && (intValue == null || intValue.intValue() < 0))) {
			return new ResponseEntity<>("缺少必要值: startDay, endDay, stringValue, intValue", HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findPurchaseByType(type, startDay, endDay, stringValue, intValue);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	// Purchase List
	@GetMapping(value = "/shop/findPurchaseListById")
	public ResponseEntity<?> findPurchaseListById(@RequestParam(defaultValue = "1") Long id,
			@RequestParam(defaultValue = "purchaseList") String idType) {
		List<PurchaseListBean> result;
		if (id.longValue() <= 0) {
			return new ResponseEntity<>(IDILEGAL, HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findPurchaseListById(id, idType);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/shop/findPurchaseListByType")
	public ResponseEntity<?> findPurchaseListByType(@RequestParam(defaultValue = "serialNumber") String type,
			@RequestParam(required = false, defaultValue = "test") String stringValue,
			@RequestParam(required = false) Integer intValue) {
		List<PurchaseListBean> result;
		if ((type.equalsIgnoreCase("serialNumber") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("price") && (intValue == null || intValue.intValue() < 0))
				|| (type.equalsIgnoreCase("priceLower") && (intValue == null || intValue.intValue() < 0))
				|| (type.equalsIgnoreCase("priceHigher") && (intValue == null || intValue.intValue() < 0))) {
			return new ResponseEntity<>("缺少必要值: stringValue, intValue", HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findPurchaseListByType(type, stringValue, intValue);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	// Review
	@GetMapping(value = "/shop/findReviewById")
	public ResponseEntity<?> findReviewById(@RequestParam(defaultValue = "review") String idType,
			@RequestParam(defaultValue = "1") Long id) {
		List<ReviewBean> result;
		if (id.longValue() <= 0) {
			return new ResponseEntity<>(IDILEGAL, HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findReviewById(idType, id);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/shop/findReviewByType")
	public ResponseEntity<?> findReviewByType(@RequestParam(defaultValue = "time") String type,
			@RequestParam(required = false, defaultValue = "1970-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDay,
			@RequestParam(required = false, defaultValue = "2019-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDay,
			@RequestParam(required = false) String stringValue, @RequestParam(required = false) Double doubleValue,
			@RequestParam(required = false) Boolean truefalse) {
		List<ReviewBean> result;
		if ((type.equalsIgnoreCase("time") && startDay == null && endDay == null)
				|| (type.equalsIgnoreCase("image") && truefalse == null)
				|| (type.equalsIgnoreCase("rating") && (doubleValue == null || doubleValue.doubleValue() < 0D))
				|| (type.equalsIgnoreCase("ratingLower") && (doubleValue == null || doubleValue.doubleValue() < 0D))
				|| (type.equalsIgnoreCase("ratingHigher") && (doubleValue == null || doubleValue.doubleValue() < 0D))) {
			return new ResponseEntity<>("缺少必要值: time, image, rating, ratingLower, ratingHigher",
					HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findReviewByType(type, startDay, endDay, stringValue, doubleValue, truefalse);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/shop/findPurchaseIdCount")
	public ResponseEntity<?> findPurchaseIdCount() {
		Integer result = null;
		result = purchaseService.findPurchaseIdCount();
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("錯誤", HttpStatus.NOT_FOUND);
		}
	}

	// POST Method
	// Create a Purchase and Purchase List
	@PostMapping(value = "/shop/newPurchase")
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> newPurchase(@RequestBody Map<String, Object> json, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>(VALUEMISSED, HttpStatus.BAD_REQUEST);
		}
		PurchaseBean result = new PurchaseBean();
		try {
			ArrayList<Integer> productIdList = new ArrayList<>();
			PurchaseBean purchaseBean = new PurchaseBean();
			purchaseBean.setCreateTime(newDate.newCurrentTime());
			purchaseBean.setUpdatedTime(newDate.newCurrentTime());
			Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				if (key.equalsIgnoreCase("id")) {
					List<?> integers = Converter.convertObjectToList(entry.getValue());
					productIdList.addAll((Collection<? extends Integer>) integers);
				} else if (key.equalsIgnoreCase(PAYSTATUS)) {
					purchaseBean.setPayStatus(value);
				} else if (key.equalsIgnoreCase("productTotalPrice")) {
					purchaseBean.setProductTotalPrice(Integer.valueOf(value));
				} else if (key.equalsIgnoreCase(DELIVERSTATUS)) {
					purchaseBean.setDeliverStatus(value);
				} else if (key.equalsIgnoreCase("deliverType")) {
					purchaseBean.setDeliverType(value);
				} else if (key.equalsIgnoreCase("deliverPrice")) {
					purchaseBean.setDeliverPrice(Integer.valueOf(value));
				} else if (key.equalsIgnoreCase("receiverInformation")) {
					HashMap<String, String> receiverInformation = (HashMap<String, String>) entry.getValue();
					purchaseBean.setReceiverInformation(receiverInformation);
				} else if (key.equalsIgnoreCase("memberId")) {
					Member member = memberDAO.findByMemberId(Long.valueOf(value));
					purchaseBean.setMemberId(member);
				}
			}

			result = purchaseService.newPurchase(productIdList, purchaseBean);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.isNotNull()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	// Create Review
	@PostMapping(value = "/shop/newReviews")
	public ResponseEntity<?> newReviews(@RequestBody List<ReviewBean> reviews, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((reviews == null) || reviews.isEmpty()) {
			return new ResponseEntity<>(VALUEMISSED, HttpStatus.BAD_REQUEST);
		}
		List<ReviewBean> reviewsWithTime = new ArrayList<>();
		List<ReviewBean> result = new ArrayList<>();
		try {
			for (ReviewBean review : reviews) {
				if (!NullChecker.isEmpty(review.getImageBase64())) {
					Byte[] byteObjects = review.getImageBase64();
					SerialBlob serialBlob = new SerialBlob(ArrayUtils.toPrimitive(byteObjects));
					review.setImage(serialBlob);
					review.setImageBase64(null);
				}
				review.setCreateTime(newDate.newCurrentTime());
				review.setUpdatedTime(newDate.newCurrentTime());
				review.setMemberId(memberDAO.findByMemberId(review.getMemberId().getId()));
				review.setProductId(productService.findProductByPrimaryKey(review.getProductId().getId()));
				review.setPurchaseListId(purchaseService
						.findPurchaseListById(review.getPurchaseListId().getId(), "purchaseList").get(0));
				reviewsWithTime.add(review);
			}
			result = purchaseService.newReviews(reviewsWithTime);
		} catch (IllegalArgumentException | SQLException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	// Put Method
	// Update a Purchase
	@PutMapping(value = "/shop/updatePurchase")
	public ResponseEntity<?> updatePurchase(@RequestBody Map<String, Object> json, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>(VALUEMISSED, HttpStatus.BAD_REQUEST);
		}
		PurchaseBean result = new PurchaseBean();
		String payStatus = null;
		String deliverStatus = null;
		Integer productTotalPrice = null;
		try {
			PurchaseBean purchaseBean = new PurchaseBean();
			Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				if (key.equalsIgnoreCase("id")) {
					String stringId = String.valueOf(value);
					Long purchaseId = Long.valueOf(stringId);
					purchaseBean = purchaseService.findPurchaseById(purchaseId, PURCHASE).get(0);
					purchaseBean.setUpdatedTime(newDate.newCurrentTime());
				} else if (key.equalsIgnoreCase(PAYSTATUS)) {
					payStatus = value;
				} else if (key.equalsIgnoreCase(DELIVERSTATUS)) {
					deliverStatus = value;
				} else if (key.equalsIgnoreCase("productTotalPrice")) {
					productTotalPrice = Integer.valueOf(value);
				}
			}
			if (NullChecker.isEmpty(payStatus) && NullChecker.isEmpty(deliverStatus) && productTotalPrice == null) {
				return new ResponseEntity<>(VALUEMISSED, HttpStatus.BAD_REQUEST);
			}
			result = purchaseService.updatePurchase(purchaseBean, payStatus, deliverStatus, productTotalPrice);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.isNotNull()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	// Update Review
	@PutMapping(value = "/shop/updateReview")
	public ResponseEntity<?> updateReview(@RequestBody Map<String, Object> json, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>(VALUEMISSED, HttpStatus.BAD_REQUEST);
		}
		ReviewBean result = new ReviewBean();
		Double rating = null;
		String comment = null;
		byte[] image = null;
		try {
			ReviewBean reviewBean = new ReviewBean();
			Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				if (key.equalsIgnoreCase("id")) {
					String stringId = String.valueOf(value);
					Long reviewId = Long.valueOf(stringId);
					reviewBean = purchaseService.findReviewById("review", reviewId).get(0);
					reviewBean.setUpdatedTime(newDate.newCurrentTime());
				} else if (key.equalsIgnoreCase("rating")) {
					rating = Double.valueOf(value);
				} else if (key.equalsIgnoreCase("comment")) {
					comment = value;
				} else if (key.equalsIgnoreCase("image") && !value.equals("null")) {
					image = Base64.getDecoder().decode(value);
				}
			}
			if (rating == null && NullChecker.isEmpty(comment) && image == null) {
				return new ResponseEntity<>(VALUEMISSED, HttpStatus.BAD_REQUEST);
			}
			result = purchaseService.updateReview(reviewBean, rating, comment, image);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.isNotNull()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/shop/processEcpay")
	public ResponseEntity<?> processEcpay(@RequestBody String purchaseId, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if (purchaseId == null) {
			return new ResponseEntity<>(VALUEMISSED, HttpStatus.BAD_REQUEST);
		}
		initial();
		String idStr = purchaseId;
		Long id = Long.valueOf(idStr);
		int length = idStr.length();
		int count = 18 - length;
		PurchaseBean purchase = purchaseService.findPurchaseById(id, PURCHASE).get(0);
		List<PurchaseListBean> purchaseListBeans = purchaseService.findPurchaseListById(id, PURCHASE);
		AioCheckOutOneTime obj = new AioCheckOutOneTime();
		obj.setMerchantID("2000132");
		SerialNumberGenerator generator = new SerialNumberGenerator(count);
		String tradeNo = generator.nextString() + "ZZ" + purchaseId;
		obj.setMerchantTradeNo(tradeNo);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		obj.setMerchantTradeDate(format.format(purchase.getCreateTime()));
		obj.setTotalAmount(String.valueOf(purchase.getProductTotalPrice() + purchase.getDeliverPrice()));
		obj.setTradeDesc("三寶商城");
		obj.setReturnURL("http://211.23.128.214:5000");
		obj.setClientBackURL("http://localhost:8080/shop/receipt.html?" + tradeNo);
		StringBuilder itemNameAndCountAndPrice = new StringBuilder();
		HashMap<String, Integer> productMap = new HashMap<>();
		for (PurchaseListBean purchaseListBean : purchaseListBeans) {
			ProductBean productBean = productService.findProductByPrimaryKey(purchaseListBean.getProductId().getId());
			String productName = productBean.getName();
			Integer one = 1;
			if (productMap.get(productName) != null) {
				productMap.put(productName, productMap.get(productName) + 1);
			} else {
				productMap.put(productName, one);
			}
		}
		Iterator<Entry<String, Integer>> iterator = productMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			String productName = entry.getKey();
			Integer productCount = entry.getValue();
			Integer price = productService.findProductsByName(productName).get(0).getPrice();
			itemNameAndCountAndPrice.append(productName).append(" ").append(String.valueOf(price)).append(" ")
					.append("元 ").append(" ").append("x").append(String.valueOf(productCount)).append("#");
		}

		obj.setItemName(itemNameAndCountAndPrice.append("運費 60 元").toString());
		String result = all.aioCheckOut(obj, null);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "shop/receipt/{ecpaySN}")
	public ResponseEntity<?> receipt(@PathVariable String ecpaySN) {
		if (ecpaySN == null) {
			return new ResponseEntity<>(VALUEMISSED, HttpStatus.BAD_REQUEST);
		}
		Long id = Long.valueOf(ecpaySN.substring(ecpaySN.indexOf("ZZ") + 2, ecpaySN.length()));
		PurchaseBean purchaseBean = purchaseService.findPurchaseById(id, PURCHASE).get(0);
		purchaseBean.setUpdatedTime(newDate.newCurrentTime());
		PurchaseBean result = purchaseService.updatePurchase(purchaseBean, "paid", null, null);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("購買失敗", HttpStatus.NOT_FOUND);
		}
	}

}
