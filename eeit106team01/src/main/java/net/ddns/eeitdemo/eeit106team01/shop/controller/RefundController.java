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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.MemberDAO;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.RefundService;
import net.ddns.eeitdemo.eeit106team01.shop.util.NewDate;

@RestController
public class RefundController {

	@Autowired
	private RefundService refundService;

	@Autowired
	private MemberDAO memberDAO;

	private Date currentTime = NewDate.newCurrentTime();

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
			return new ResponseEntity<>("缺少必要值", HttpStatus.BAD_REQUEST);
		}
		List<PurchaseListBean> purchaseListBeans = new ArrayList<>();
		RefundBean refundBean = new RefundBean();
		refundBean.setCreateTime(currentTime);
		refundBean.setUpdatedTime(currentTime);
		Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.equalsIgnoreCase("purchaseListBeans")) {
				purchaseListBeans.add((PurchaseListBean) value);
			} else if (key.equalsIgnoreCase("comment")) {
				refundBean.setComment((String) value);
			} else if (key.equalsIgnoreCase("processStatus")) {
				refundBean.setProcessStatus((String) value);
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

	public void updateRefundProcessStatus() {

	}

	public void findRefundsById() {

	}

	public void findRefundsByType() {

	}

	public void findRefunds() {

	}

	public void findRefundListById() {

	}

	public void findAllRefundList() {

	}

}
