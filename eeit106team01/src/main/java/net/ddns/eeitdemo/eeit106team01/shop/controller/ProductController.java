package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.net.URI;
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

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.ProductService;

@RestController
public class ProductController {
	@Autowired private ServletContext application;
	@Autowired private ProductService productService;
	
	@GetMapping(
			path = { "/product/{id}" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getProductByPrimaryKey(@PathVariable Long id){
		if ((id != null) && (id.intValue() > 0)) {
			ProductBean result = productService.findProductByPrimaryKey(id);
			if(result != null) {
				return ResponseEntity.ok(result);
			}else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(
			path = { "/products/totalSold" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getProductsByTotalSold(@RequestParam Integer top){
		if ((top != null) && (top.intValue() > 0)) {
			List<ProductBean> result = productService.findProductsByTotalSold(top);
			return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(
			path = { "/products/status" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getProductStatus(
						@RequestParam(required = false) Long id,
						@RequestParam String status){
		if(status != null) {
			List<SerialNumberBean> result = productService.findProductStatus(id, status);
			if(result != null) {
				return new ResponseEntity<List<SerialNumberBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(
			path = { "/products" }, 
			produces = { "application/json" })  //待測試
	public ResponseEntity<?> getProductsByUpdatedTime(@RequestParam Integer day){
		if ((day != null) && (day.intValue() > 0)) {
			List<ProductBean> result = productService.findProductsByUpdatedTime(day);
			if(result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(
			path = { "/products/brand" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getProductsByBrand(@RequestParam String brand){
		if(brand != null) {
			List<ProductBean> result = productService.findProductsByBrand(brand);
			if(result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(
			path = { "/products/price" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getProductsByPrice(
				@RequestParam Integer minPrice,@RequestParam Integer maxPrice){
		if ((minPrice != null) && (minPrice.intValue() >= 0) &&
			(maxPrice != null) && (maxPrice.intValue() > 0)){
				List<ProductBean> result = 
						productService.findProductsByPrice(minPrice, maxPrice);
			if(result != null) {
					return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
				}
				return ResponseEntity.notFound().build();
			}
		return ResponseEntity.notFound().build();	
	}
	
	@GetMapping(
			path = { "/products/recommend" }, 
			produces = { "application/json" }) //待測試 %%有點怪
	public ResponseEntity<?> getRecommendProducts(@RequestParam String name){
		if(name != null) {
			List<ProductBean> result = productService.recommendProducts(name);
			if(result != null) {
				return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
		
	@PutMapping(path={"/products"},
			consumes={"application/json; charset=UTF-8"}, 
			produces = { "application/json" })
	public ResponseEntity<?> putProduct
			(@RequestBody ProductBean productBean,BindingResult bindingResult){
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		if(productBean != null) {
			ProductBean result = productService.updateProduct(productBean);
			if(result != null) {
				return ResponseEntity.ok(result);
			}else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping(
			path = { "/products/productsSN/{id}" }, 
			consumes = {"application/json" }, 
			produces = {"application/json" })
	public ResponseEntity<?> postProductsSN(    //待測試
			@PathVariable Long id,@RequestParam Integer stock,BindingResult bindingResult){
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		ProductBean temp = productService.findProductByPrimaryKey(id);
		if(temp !=null) {
			List<SerialNumberBean> result = productService.insertProductsSN(id, stock);
			if(result != null) {
				URI uri = URI.create(application.getContextPath()+"/products/"+result.get(0).getProductBean().getId());
				return ResponseEntity.created(uri).body(result);
			} else {
				System.out.println(" failed to insert SN ");
				return ResponseEntity.noContent().build();
			}
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping(
			path = { "/products/insert" }, 
			consumes = {"application/json" }, 
			produces = {"application/json" })
	public ResponseEntity<?> postProduct
				(@RequestBody ProductBean productBean,BindingResult bindingResult){
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		ProductBean result = productService.insertProduct(productBean);
		if(result != null) {
			URI uri = URI.create(application.getContextPath()+"/products/"+"insert/"+productBean.getId());
			return ResponseEntity.created(uri).body(result);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
