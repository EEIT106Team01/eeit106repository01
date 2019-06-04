package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.sql.SQLException;
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
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.ProductService;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.PurchaseService;
import net.ddns.eeitdemo.eeit106team01.shop.util.Converter;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@RestController
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberDAO memberDAO;

	private Date currentTime = NewDate.newCurrentTime();

	// Get Method
	// Purchase
	@GetMapping(value = "/shop/findPurchaseById")
	public ResponseEntity<?> findPurchaseById(@RequestParam(defaultValue = "1") Long id,
			@RequestParam(defaultValue = "purchase") String idType) {
		List<PurchaseBean> result = new ArrayList<PurchaseBean>();
		if (id.longValue() <= 0) {
			return new ResponseEntity<>("ID不合法", HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findPurchaseById(id, idType);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.size() > 0) {
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
		List<PurchaseBean> result = new ArrayList<PurchaseBean>();
		if ((type.equalsIgnoreCase("time") && startDay == null && endDay == null)
				|| (type.equalsIgnoreCase("deliverStatus") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("deliverType") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("payStatus") && NullChecker.isEmpty(stringValue))
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
		if (result != null && result.size() > 0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	// Purchase List
	@GetMapping(value = "/shop/findPurchaseListById")
	public ResponseEntity<?> findPurchaseListById(@RequestParam(defaultValue = "1") Long id,
			@RequestParam(defaultValue = "purchaseList") String idType) {
		List<PurchaseListBean> result = new ArrayList<PurchaseListBean>();
		if (id.longValue() <= 0) {
			return new ResponseEntity<>("ID不合法", HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findPurchaseListById(id, idType);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.size() > 0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/shop/findPurchaseListByType")
	public ResponseEntity<?> findPurchaseListByType(@RequestParam(defaultValue = "serialNumber") String type,
			@RequestParam(required = false, defaultValue = "test") String stringValue,
			@RequestParam(required = false) Integer intValue) {
		List<PurchaseListBean> result = new ArrayList<PurchaseListBean>();
		if ((type.equalsIgnoreCase("serialNumber") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("price") & (intValue == null || intValue.intValue() < 0))
				|| (type.equalsIgnoreCase("priceLower") & (intValue == null || intValue.intValue() < 0))
				|| (type.equalsIgnoreCase("priceHigher") & (intValue == null || intValue.intValue() < 0))) {
			return new ResponseEntity<>("缺少必要值: stringValue, intValue", HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findPurchaseListByType(type, stringValue, intValue);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.size() > 0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

	// Review
	@GetMapping(value = "/shop/findReviewById")
	public ResponseEntity<?> findReviewById(@RequestParam(defaultValue = "review") String idType,
			@RequestParam(defaultValue = "1") Long id) throws SerialException {
		List<ReviewBean> result = new ArrayList<ReviewBean>();
		if (id.longValue() <= 0) {
			return new ResponseEntity<>("ID不合法", HttpStatus.BAD_REQUEST);
		}
		try {
			result = purchaseService.findReviewById(idType, id);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.size() > 0) {
			return new ResponseEntity<List<ReviewBean>>(result, HttpStatus.OK);
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
		List<ReviewBean> result = new ArrayList<ReviewBean>();
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
		if (result != null && result.size() > 0) {
			return new ResponseEntity<List<ReviewBean>>(result, HttpStatus.OK);
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
	public ResponseEntity<?> newPurchase(@RequestBody HashMap<String, Object> json, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>("缺少必要值", HttpStatus.BAD_REQUEST);
		}
		PurchaseBean result = new PurchaseBean();
		try {
			ArrayList<Integer> productIdList = new ArrayList<>();
			PurchaseBean purchaseBean = new PurchaseBean();
			purchaseBean.setCreateTime(currentTime);
			purchaseBean.setUpdatedTime(currentTime);
			Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				if (key.equalsIgnoreCase("id")) {
					List<?> integers = Converter.convertObjectToList(entry.getValue());
					productIdList.addAll((Collection<? extends Integer>) integers);
				} else if (key.equalsIgnoreCase("payStatus")) {
					purchaseBean.setPayStatus(value);
				} else if (key.equalsIgnoreCase("productTotalPrice")) {
					purchaseBean.setProductTotalPrice(Integer.valueOf(value));
				} else if (key.equalsIgnoreCase("deliverStatus")) {
					purchaseBean.setDeliverStatus(value);
				} else if (key.equalsIgnoreCase("deliverType")) {
					purchaseBean.setDeliverType(value);
				} else if (key.equalsIgnoreCase("deliverPrice")) {
					purchaseBean.setDeliverPrice(Integer.valueOf(value));
				} else if (key.equalsIgnoreCase("receiverInformation")) {
					HashMap<String, String> receiverInformation = new HashMap<String, String>();
					receiverInformation = (HashMap<String, String>) entry.getValue();
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
			return new ResponseEntity<PurchaseBean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	// Create Review
	@PostMapping(value = "/shop/newReviews")
	public ResponseEntity<?> newReviews(@RequestBody List<ReviewBean> reviews, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((reviews == null) || reviews.size() < 0) {
			return new ResponseEntity<>("缺少必要值", HttpStatus.BAD_REQUEST);
		}
		List<ReviewBean> reviewsWithTime = new ArrayList<ReviewBean>();
		List<ReviewBean> result = new ArrayList<ReviewBean>();
		try {
			for (ReviewBean review : reviews) {
				if (NullChecker.isEmpty(review.getImageBase64()) == false) {
					Byte[] byteObjects = review.getImageBase64();
					SerialBlob serialBlob = new SerialBlob(ArrayUtils.toPrimitive(byteObjects));
					review.setImage(serialBlob);
					review.setImageBase64(null);
				}
				review.setCreateTime(currentTime);
				review.setUpdatedTime(currentTime);
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
		if (result != null && result.size() > 0) {
			return new ResponseEntity<List<ReviewBean>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	// Put Method
	// Update a Purchase
	@PutMapping(value = "/shop/updatePurchase")
	public ResponseEntity<?> updatePurchase(@RequestBody HashMap<String, Object> json, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>("缺少必要值", HttpStatus.BAD_REQUEST);
		}
		PurchaseBean result = new PurchaseBean();
		String payStatus = null;
		String deliverStatus = null;
		Integer productTotalPrice = null;
		try {
			PurchaseBean purchaseBean = new PurchaseBean();
			Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				if (key.equalsIgnoreCase("id")) {
					String stringId = String.valueOf(value);
					Long purchaseId = Long.valueOf(stringId);
					purchaseBean = purchaseService.findPurchaseById(purchaseId, "purchase").get(0);
					purchaseBean.setUpdatedTime(currentTime);
				} else if (key.equalsIgnoreCase("payStatus")) {
					payStatus = value;
				} else if (key.equalsIgnoreCase("deliverStatus")) {
					deliverStatus = value;
				} else if (key.equalsIgnoreCase("productTotalPrice")) {
					productTotalPrice = Integer.valueOf(value);
				}
			}
			if (NullChecker.isEmpty(payStatus) && NullChecker.isEmpty(deliverStatus) && productTotalPrice == null) {
				return new ResponseEntity<>("缺少必要值", HttpStatus.BAD_REQUEST);
			}
			result = purchaseService.updatePurchase(purchaseBean, payStatus, deliverStatus, productTotalPrice);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.isNotNull()) {
			return new ResponseEntity<PurchaseBean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	// Update Review
	@PutMapping(value = "/shop/updateReview")
	public ResponseEntity<?> updateReview(@RequestBody HashMap<String, Object> json, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>("缺少必要值", HttpStatus.BAD_REQUEST);
		}
		ReviewBean result = new ReviewBean();
		Double rating = null;
		String comment = null;
		byte[] image = null;
		try {
			ReviewBean reviewBean = new ReviewBean();
			Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				if (key.equalsIgnoreCase("id")) {
					String stringId = String.valueOf(value);
					Long reviewId = Long.valueOf(stringId);
					reviewBean = purchaseService.findReviewById("review", reviewId).get(0);
					reviewBean.setUpdatedTime(currentTime);
				} else if (key.equalsIgnoreCase("rating")) {
					rating = Double.valueOf(value);
				} else if (key.equalsIgnoreCase("comment")) {
					comment = value;
				} else if (key.equalsIgnoreCase("image")) {
					if (value.equals("null")) {
						image = null;
					}
					image = Base64.getDecoder().decode(value);
				}
			}
			if (rating == null && NullChecker.isEmpty(comment) && image == null) {
				return new ResponseEntity<>("缺少必要值", HttpStatus.BAD_REQUEST);
			}
			result = purchaseService.updateReview(reviewBean, rating, comment, image);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.isNotNull()) {
			return new ResponseEntity<ReviewBean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

}
