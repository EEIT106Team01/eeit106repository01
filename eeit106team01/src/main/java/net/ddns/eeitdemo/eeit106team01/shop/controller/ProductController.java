package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.awt.Color;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberTempBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.DataBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.ProductService;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;
import net.ddns.eeitdemo.eeit106team01.websocket.model.NotificationMsg;
import net.ddns.eeitdemo.eeit106team01.websocket.model.NotificationService;

@RestController
public class ProductController {
	@Autowired
	private ServletContext application;
	@Autowired
	private ProductService productService;
	@Autowired
	private NotificationService notificationService;
	
	
	@GetMapping(path = { "/product/{id}" }, produces = { "application/json" })
	public ResponseEntity<?> getProductByPrimaryKey(@PathVariable Long id) {
		if ((id != null) && (id.intValue() > 0)) {
			ProductBean result = productService.findProductByPrimaryKey(id);
			if (result != null) {
				return ResponseEntity.ok(result);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/randomProduct" }, produces = { "application/json" })
	public ResponseEntity<?> getRandomProduct() {
		ProductBean result = productService.getRandomProduct();
		if (result != null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/products" }, produces = { "application/json" })
	public ResponseEntity<?> getProducts() {
		List<ProductBean> result = productService.findProducts();
		if (result != null) {
			return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/products/totalSold" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByTotalSold(@RequestParam Integer top) {
		if ((top != null) && (top.intValue() > 0)) {
			List<ProductBean> result = productService.findProductsByTotalSold(top);
			return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/search/type" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByType(@RequestParam String type) {
		if (type != null) {
			List<ProductBean> result = productService.findProductsByType(type);
			return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/products/status" }, produces = { "application/json" })
	public ResponseEntity<?> getProductStatus(@RequestParam(required = false) Long id, @RequestParam String status) {
		if (status != null) {
			List<SerialNumberBean> result = productService.findProductStatus(id, status);
			if (result != null) {
				return new ResponseEntity<List<SerialNumberBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/search/updatedTime" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByUpdatedTime(@RequestParam(required = false) String dataName,
			@RequestParam String queryString, @RequestParam(required = false) String brandType,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDay,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDay) {
			System.out.println("starDay="+startDay+"endDay="+endDay);
		if (startDay != null && endDay != null && startDay.equals(endDay) == false && startDay.compareTo(endDay) < 0) {
			List<ProductBean> result = productService.findProductsByUpdatedTime(dataName, queryString, startDay, endDay,
					brandType);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			System.out.println("result");
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/search/brand" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByBrand(@RequestParam String brand,
			@RequestParam(required = false) String type) {
		if (brand != null) {
			List<ProductBean> result = productService.findProductsByBrand(brand, type);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/search/price" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByPrice(@RequestParam String byNameBrandType, @RequestParam String queryString,
			@RequestParam(required = false) String type,
			@RequestParam(defaultValue = "0", required = false) Integer minPrice,
			@RequestParam(defaultValue = "999999", required = false) Integer maxPrice) {
		if (NullChecker.isEmpty(byNameBrandType) == false && NullChecker.isEmpty(byNameBrandType) == false
				&& (minPrice != null) && (minPrice.intValue() >= 0) && (maxPrice != null)
				&& (maxPrice.intValue() > 0)) {
			List<ProductBean> result = productService.findProductsByNameBrandTypeAndOrderByPriceBetween(byNameBrandType,
					queryString, type, minPrice, maxPrice);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/search/TypeName" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByName(@RequestParam String productType, @RequestParam String productName) {
		if (productName != null && NullChecker.isEmpty(productType) == true) {
			List<ProductBean> result = productService.findProductsByName(productName);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		} else if ((productName != null && NullChecker.isEmpty(productType) == false)) {
			List<ProductBean> result = productService.findProductsByTypeName(productType, productName);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/search/data" }, produces = { "application/json" })
	public ResponseEntity<?> getProductTypes(@RequestParam String dataName,
			@RequestParam(required = false) String type) {
		if (NullChecker.isEmpty(dataName) == false) {
			List<DataBean> result = productService.findProductData(dataName, type);
			if (result != null) {
				return new ResponseEntity<List<DataBean>>(result, HttpStatus.OK);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/search/sort" }, produces = { "application/json" })
	public ResponseEntity<?> findProductsSort(@RequestParam String dataName, @RequestParam String queryString,
			@RequestParam String type, @RequestParam String sort, @RequestParam(required = false) String brandType) {
		if (dataName != null && queryString != null && type != null && sort != null) {
			List<ProductBean> result = productService.findProductsSort(dataName, queryString, type, sort, brandType);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/products/recommend" }, produces = { "application/json" })
	public ResponseEntity<?> getRecommendProducts(@RequestParam String type) {
		if (type != null) {
			List<ProductBean> result = productService.recommendProducts(type);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping(path = { "/product/update" }, consumes = { "application/json; charset=UTF-8" }, produces = {
			"application/json" })
	public ResponseEntity<?> putProduct(@RequestBody ProductBean productBean, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		System.err.println(productBean);
		if (productBean != null) {
			System.err.println(productBean.getId());
			ProductBean origin = productService.findProductByPrimaryKey(productBean.getId());
			if (origin != null) {
				ProductBean result = productService.updateProduct(productBean);
				System.err.println(result);
				if (origin.getStock() == productBean.getStock()) {
					if (result != null) {
						return ResponseEntity.ok(result);
					} else {
						return ResponseEntity.notFound().build();
					}
				} else if (origin.getStock() < productBean.getStock()) {
					List<SerialNumberBean> insertSN = productService.insertProductsSN(productBean.getId(),
							(productBean.getStock() - origin.getStock()));
					if (insertSN != null) {
						URI uri = URI.create(application.getContextPath() + "/products/"
								+ insertSN.get(0).getProductId().getId() + "/" + productBean.getStock());
						return ResponseEntity.created(uri).body(result);
					} else {
						return ResponseEntity.noContent().build();
					}
				} else if ((origin.getStock() > productBean.getStock())) {
					List<SerialNumberBean> sNList = productService.findProductStatus(productBean.getId(), "available");
					for (int i = 0; i < (origin.getStock() - productBean.getStock()); i++) {
						sNList.get(i).setAvailabilityStatus("sold");
						productService.updateSNStatus(sNList.get(i));
					}
					URI uri = URI
							.create(application.getContextPath() + "/products/" + sNList.get(0).getProductId().getId());
					return ResponseEntity.created(uri).body(result);
				}
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(path = { "/products/insert" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> postProduct(@RequestBody ProductBean productBean, BindingResult bindingResult) {
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		ProductBean result = productService.insertProduct(productBean);
		if (result != null) {
			URI uri = URI.create(application.getContextPath() + "/products/" + result.getId());
			return ResponseEntity.created(uri).body(result);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping(path = { "/sendMsg" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> sendMsg(@RequestBody NotificationMsg notificationMsgTemp, BindingResult bindingResult){
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if(notificationMsgTemp != null) {
			System.err.println(notificationMsgTemp.getMessage());
			System.err.println(notificationMsgTemp.getUrl());
			NotificationMsg notificationMsg = new NotificationMsg();
			notificationMsg.setMessage(notificationMsgTemp.getMessage());
			notificationMsg.setUrl(notificationMsgTemp.getUrl());
			notificationMsg.setIcon("entypo-info");
			notificationMsg.setColor(Color.RED);
			notificationService.sendNotificationToAllUser(notificationMsg);
			return new ResponseEntity<NotificationMsg>(notificationMsgTemp, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
}
