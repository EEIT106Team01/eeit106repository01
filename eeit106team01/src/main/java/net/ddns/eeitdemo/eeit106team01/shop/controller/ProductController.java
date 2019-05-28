package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
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

import net.ddns.eeitdemo.eeit106team01.shop.model.DataBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.ProductService;
import net.ddns.eeitdemo.eeit106team01.shop.util.NullChecker;

@RestController
public class ProductController {
	@Autowired
	private ServletContext application;
	@Autowired
	private ProductService productService;

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

	@GetMapping(path = { "/products/type" }, produces = { "application/json" })
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

	@GetMapping(path = { "/products/updatedTime" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByUpdatedTime(@RequestParam Date startDay, Date endDay) {
		if (startDay != null && endDay != null && startDay.equals(endDay) == false && startDay.compareTo(endDay) < 0) {
			List<ProductBean> result = productService.findProductsByUpdatedTime(startDay, endDay);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/products/brand" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByBrand(@RequestParam String brand) {
		if (brand != null) {
			List<ProductBean> result = productService.findProductsByBrand(brand);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/products/price" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByPrice(@RequestParam String byNameBrandType, @RequestParam String queryString,
			@RequestParam(defaultValue = "0") Integer minPrice, @RequestParam Integer maxPrice) {
		if (NullChecker.isEmpty(byNameBrandType) == false && NullChecker.isEmpty(byNameBrandType) == false
				&& (minPrice != null) && (minPrice.intValue() >= 0) && (maxPrice != null)
				&& (maxPrice.intValue() > 0)) {
			List<ProductBean> result = productService.findProductsByNameBrandTypeAndOrderByPriceBetween(byNameBrandType,
					queryString, minPrice, maxPrice);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/products/name" }, produces = { "application/json" })
	public ResponseEntity<?> getProductsByName(@RequestParam String productName) {
		if (productName != null) {
			List<ProductBean> result = productService.findProductsByName(productName);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/search/type" }, produces = { "application/json" })
	public ResponseEntity<?> getProductTypes() {
		List<DataBean> result = productService.findProductTypes();
		if (result != null) {
			return new ResponseEntity<List<DataBean>>(result, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = { "/products/recommend" }, produces = { "application/json" })
	public ResponseEntity<?> getRecommendProducts(@RequestParam String name) {
		if (name != null) {
			List<ProductBean> result = productService.recommendProducts(name);
			if (result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping(path = { "/products" }, consumes = { "application/json; charset=UTF-8" }, produces = {
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
		if (productBean != null) {
			ProductBean origin = productService.findProductByPrimaryKey(productBean.getId());
			if (origin != null) {
				ProductBean result = productService.updateProduct(productBean);
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
}
