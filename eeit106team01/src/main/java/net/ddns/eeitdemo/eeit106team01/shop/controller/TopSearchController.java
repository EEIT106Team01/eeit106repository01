package net.ddns.eeitdemo.eeit106team01.shop.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.shop.model.TopSearchBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.service.TopSearchService;

@RestController
public class TopSearchController {
	@Autowired private ServletContext application;
	@Autowired private TopSearchService topSearchService;
	
	@GetMapping(
			path = { "/keyWord/{id}" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getTopSearchByPrimaryKey(@PathVariable Long id){
		if ((id != null) && (id.intValue() > 0)) {
			TopSearchBean result = topSearchService.findTopSearchByPrimaryKey(id);
			if(result != null) {
				return ResponseEntity.ok(result);
			}else {
				return ResponseEntity.notFound().build();
			}
		}return ResponseEntity.notFound().build();
	}
	
	@GetMapping(
			path = { "/keyWords" }, 
			produces = { "application/json" })
	public ResponseEntity<?> getTopSearchs(){
		List<TopSearchBean> result = topSearchService.findTopSearchs();
		if(result != null) {
			return ResponseEntity.ok(result);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(
			path = { "/keyWord/insert" }, 
			consumes = {"application/json" }, 
			produces = {"application/json" })
	public ResponseEntity<?> postTopSearch(
			@RequestBody TopSearchBean topSearchBean,BindingResult bindingResult){
		if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
			Map<String, String> errors = new HashMap<String, String>();
			List<ObjectError> bindingErrors = bindingResult.getAllErrors();
			for (ObjectError bindingError : bindingErrors) {
				errors.put(bindingError.getObjectName(), bindingError.toString());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		TopSearchBean result = topSearchService.insertTopSearch(topSearchBean.getKeyword());
		if(result != null) {
			URI uri = URI.create(application.getContextPath()+"/keyWord/"+result.getId());
			return ResponseEntity.created(uri).body(result);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
}
