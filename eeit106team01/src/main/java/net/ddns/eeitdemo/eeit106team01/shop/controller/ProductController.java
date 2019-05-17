package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.ProductService;

@Controller
public class ProductController {
	@Autowired private ServletContext application;
	@Autowired private ProductService productService;
	
	@GetMapping(
			path = { "/products/{id}" }, 
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
			path = { "/products" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getProductsByTotalSold(@RequestParam Integer top){
		if ((top != null) && (top.intValue() > 0)) {
			List<ProductBean> result = productService.findProductsByTotalSold(top);
			return new ResponseEntity<List<ProductBean>>(result, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(
			path = { "/products" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getProductStatus(
						@RequestParam Long id,@RequestParam Integer stock){
		if((id != null) && (id.intValue() > 0) 
			&& (stock != null) && (stock.intValue()> 0 )){
			List<SerialNumberBean> result = productService.insertProductsSN(id, stock);
			if(result != null) {
				return new ResponseEntity<List<SerialNumberBean>>(result, HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(
			path = { "/products" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getProductStatus(
						@RequestParam Long id,@RequestParam String status){
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
			produces = { "application/json" })
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
			
	@PutMapping(path={"/products"},
			consumes={"application/json; charset=UTF-8"})
	public ResponseEntity<?> putProduct(@RequestBody ProductBean productBean){
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
			path = { "/products" }, 
			consumes = {"application/json" }, 
			produces = {"application/json" })
	public ResponseEntity<?> postProduct(@RequestBody ProductBean productBean){
		ProductBean result = productService.insertProduct(productBean);
		if(result != null) {
			URI uri = URI.create(application.getContextPath()+"/products/"+productBean.getId());
			return ResponseEntity.created(uri).body(result);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
