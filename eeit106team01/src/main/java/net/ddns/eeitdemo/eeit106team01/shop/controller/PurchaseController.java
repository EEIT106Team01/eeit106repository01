package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.PurchaseService;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@RestController
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	// Get Method
	// Purchase
	@GetMapping(value = "/shop/findPurchaseById")
	public ResponseEntity<?> findPurchaseById(@RequestParam Long id, @RequestParam String idType) {
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
	public ResponseEntity<?> findPurchaseByType(@RequestParam String type,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDay,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDay,
			@RequestParam(required = false) String stringValue, @RequestParam(required = false) Integer intValue) {
		List<PurchaseBean> result = new ArrayList<PurchaseBean>();
		if ((type.equalsIgnoreCase("time") && startDay == null && endDay == null)
				|| (type.equalsIgnoreCase("deliverStatus") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("deliverType") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("payStatus") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("price") && (intValue == null || intValue.intValue() <= 0))
				|| (type.equalsIgnoreCase("priceLower") && (intValue == null || intValue.intValue() <= 0))
				|| (type.equalsIgnoreCase("priceHigher") && (intValue == null || intValue.intValue() <= 0))) {
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
	public ResponseEntity<?> findPurchaseListById(@RequestParam() Long id, @RequestParam String idType) {
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
	public ResponseEntity<?> findPurchaseListByType(@RequestParam String type,
			@RequestParam(required = false) String stringValue, @RequestParam(required = false) Integer intValue) {
		List<PurchaseListBean> result = new ArrayList<PurchaseListBean>();
		if ((type.equalsIgnoreCase("serialNumber") && NullChecker.isEmpty(stringValue))
				|| (type.equalsIgnoreCase("price") & (intValue == null || intValue.intValue() <= 0))
				|| (type.equalsIgnoreCase("priceLower") & (intValue == null || intValue.intValue() <= 0))
				|| (type.equalsIgnoreCase("priceHigher") & (intValue == null || intValue.intValue() <= 0))) {
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
	public ResponseEntity<?> findReviewById(@RequestParam String idType, @RequestParam Long id) throws SerialException {
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
			SerialBlob serialBlob = result.get(0).getImage();
			byte[] array = serialBlob.getBytes(1, (int) serialBlob.length());
			File file;
			FileOutputStream out;
			try {
				file = new File("D:\\Pictures\\something.binary");
				out = new FileOutputStream(file);
				out.write(array);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<List<ReviewBean>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("無此訂單", HttpStatus.NOT_FOUND);
		}
	}

}
