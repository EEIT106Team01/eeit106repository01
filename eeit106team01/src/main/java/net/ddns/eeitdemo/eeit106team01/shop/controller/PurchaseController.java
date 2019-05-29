package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.PurchaseService;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@RestController
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	// Get Method
	// Purchase
	@GetMapping(value = "/shop/findPurchaseById")
	public ResponseEntity<?> findPurchaseById(@RequestParam Long id, @RequestParam(value = "idType") String idType) {
		List<PurchaseBean> result = new ArrayList<PurchaseBean>();
		result = purchaseService.findPurchaseById(id, idType);
		if (result != null && result.size() >= 1) {
			System.err.println(result.toString());
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else if (id.longValue() <= 0) {
			return new ResponseEntity<>("ID不合法", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
	}

	@GetMapping(value = "/shop/findPurchaseByType")
	public List<PurchaseBean> findPurchaseByType(@RequestParam String type,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDay,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDay,
			@RequestParam(required = false) String stringValue, @RequestParam(required = false) Integer intValue) {
		List<PurchaseBean> result = new ArrayList<PurchaseBean>();
		result = purchaseService.findPurchaseByType(type, startDay, endDay, stringValue, intValue);
		if (result != null && result.size() > 0) {
			return result;
		}
		return null;
	}

	// Purchase List
	@GetMapping(value = "/shop/findPurchaseListByType")
	public List<PurchaseListBean> findPurchaseListByType(@RequestParam String type,
			@RequestParam(required = false) String stringValue, @RequestParam(required = false) Integer intValue) {
		List<PurchaseListBean> result = new ArrayList<PurchaseListBean>();
		result = purchaseService.findPurchaseListByType(type, stringValue, intValue);
		if (result != null && result.size() > 0) {
			return result;
		}
		return null;
	}

}
