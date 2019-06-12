package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
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

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.PurchaseService;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.RefundService;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;

@RestController
public class RefundController {

	@Autowired
	private RefundService refundService;

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private MemberDAO memberDAO;
	private NewDate newDate = new NewDate();
	private Date currentTime = newDate.newCurrentTime();
	
	private static final String VALUENOTFOUND= "缺少必要值";
	private static final String NULLPOINTER = "無此退貨單";
	

	@PostMapping(value = "shop/newRefund")
	public ResponseEntity<?> newRefund(@RequestBody Map<String, Object> json, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>(VALUENOTFOUND, HttpStatus.BAD_REQUEST);
		}
		List<PurchaseListBean> purchaseListBeans = new ArrayList<>();
		RefundBean refundBean = new RefundBean();
		refundBean.setCreateTime(currentTime);
		refundBean.setUpdatedTime(currentTime);
		refundBean.setProcessStatus("created");
		Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.equalsIgnoreCase("purchaseListIds")) {
				String stringId = (String) value;
				Long purchaseListId = Long.valueOf(stringId);
				PurchaseListBean purchaseListBean = purchaseService.findPurchaseListById(purchaseListId, "purchaseList")
						.get(0);
				purchaseListBeans.add(purchaseListBean);
			} else if (key.equalsIgnoreCase("comment")) {
				refundBean.setComment((String) value);
			} else if (key.equalsIgnoreCase("memberID")) {
				Long memberId = Long.valueOf((String) value);
				refundBean.setMemberId(memberDAO.findByMemberId(memberId));
			}
		}
		RefundBean result = refundService.newRefund(purchaseListBeans, refundBean);
		if (result != null && result.isNotNull()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("建立失敗", HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "shop/updateRefundProcessStatus")
	public ResponseEntity<?> updateRefundProcessStatus(@RequestBody Map<String, Object> json,
			BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if ((json == null) || json.size() < 0) {
			return new ResponseEntity<>(VALUENOTFOUND, HttpStatus.BAD_REQUEST);
		}
		RefundBean refundBean = new RefundBean();
		String processStatus = null;
		Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.equalsIgnoreCase("redunfId")) {
				String stringId = (String) value;
				Long refundId = Long.valueOf(stringId);
				refundBean = refundService.findRefundsById("refund", refundId).get(0);
				refundBean.setUpdatedTime(currentTime);
			} else if (key.equalsIgnoreCase("processStatus")) {
				processStatus = (String) value;
			}
		}
		RefundBean result = refundService.updateRefundProcessStatus(refundBean, processStatus);
		if (result != null && result.isNotNull()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("修改失敗", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "shop/findRefundsById")
	public ResponseEntity<?> findRefundsById(@RequestParam(defaultValue = "refund") String idType,
			@RequestParam(defaultValue = "1") Long id) {
		List<RefundBean> result;
		if (id.longValue() <= 0) {
			return new ResponseEntity<>("ID不合法", HttpStatus.BAD_REQUEST);
		}
		try {
			result = refundService.findRefundsById(idType, id);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(NULLPOINTER, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "shop/findRefundsByType")
	public ResponseEntity<?> findRefundsByType(@RequestParam(defaultValue = "processStatus") String type,
			@RequestParam(defaultValue = "created") String stringValue) {
		List<RefundBean> result;
		try {
			result = refundService.findRefundsByType(type, stringValue);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(NULLPOINTER, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "shop/findRefunds")
	public ResponseEntity<?> findRefunds() {
		List<RefundBean> result;
		try {
			result = refundService.findRefunds();
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(NULLPOINTER, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "shop/findRefundListById")
	public ResponseEntity<?> findRefundListById(@RequestParam(defaultValue = "refundList") String idType,
			@RequestParam(defaultValue = "1") Long id) {
		List<RefundListBean> result;
		if (id.longValue() <= 0) {
			return new ResponseEntity<>("ID不合法", HttpStatus.BAD_REQUEST);
		}
		try {
			result = refundService.findRefundListById(idType, id);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(NULLPOINTER, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "shop/findAllRefundList")
	public ResponseEntity<?> findAllRefundList() {
		List<RefundListBean> result;
		try {
			result = refundService.findAllRefundList();
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("錯誤: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(NULLPOINTER, HttpStatus.NOT_FOUND);
		}
	}

}
