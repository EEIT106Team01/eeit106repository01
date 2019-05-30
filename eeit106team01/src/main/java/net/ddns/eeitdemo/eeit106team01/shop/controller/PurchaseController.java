package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.PurchaseService;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@RestController
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private MemberDAO memberDAO;

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

	// POST Method
	// Create a Purchase and Purchase List
	@PostMapping(value = "/shop/newPurchase")
	public ResponseEntity<?> newPurchase(@RequestBody HashMap<String, Object> json, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			System.err.println("error");
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		PurchaseBean result = new PurchaseBean();
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>("缺少必要值: productIdList, purchaseBean", HttpStatus.BAD_REQUEST);
		}
		try {
			System.err.println("error1");
			ArrayList<Integer> productIdList = new ArrayList<>();
			PurchaseBean purchaseBean = new PurchaseBean();
			Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				if (key.equalsIgnoreCase("id")) {
					productIdList.addAll((Collection<? extends Integer>) entry.getValue());
				} else if (key.equalsIgnoreCase("createTime") | key.equalsIgnoreCase("createTime")) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date time = dateFormat.parse(value);
					purchaseBean.setCreateTime(time);
					purchaseBean.setUpdatedTime(time);
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
				} else if (key.equalsIgnoreCase("receiver") || key.equalsIgnoreCase("address")) {
					HashMap<String, String> receiverInformation = new HashMap<String, String>();
					receiverInformation.put(key, value);
					purchaseBean.setReceiverInformation(receiverInformation);
				} else if (key.equalsIgnoreCase("memberId")) {
					Member member = memberDAO.findByMemberId(Long.valueOf(value));
					purchaseBean.setMemberId(member);
				}
			}

			result = purchaseService.newPurchase(productIdList, purchaseBean);
		} catch (IllegalArgumentException | ParseException e) {
			System.err.println("error");
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && result.isNotNull()) {
			return new ResponseEntity<PurchaseBean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

}
