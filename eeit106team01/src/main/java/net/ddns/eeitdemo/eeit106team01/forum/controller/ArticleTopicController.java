package net.ddns.eeitdemo.eeit106team01.forum.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentService;

@RestController
public class ArticleTopicController {

	@Autowired
	private ServletContext application;

	@Autowired
	private ArticleTopicCurrentService articleTopicCurrentService;
	
	@GetMapping(path = { "/articleTopics" }, produces = { "application/json" })
	public ResponseEntity<?> getTopicList(
			@RequestParam(required = false) Integer begin,
			@RequestParam(required = false) Integer end, 
			@RequestParam(required = false) String orderType,	//orderByTime orderByLike
			@RequestParam(required = false) Double lowerLatitude,
			@RequestParam(required = false) Double upperLatitude,
			@RequestParam(required = false) Double lowerLongitude,
			@RequestParam(required = false) Double upperLongitude
			) {
		System.out.println("getTopicList method running");
		
		List<ArticleTopicCurrentBean> findRange = null;
		if((begin != null) && (end != null) && 
			(lowerLatitude == null) && (upperLatitude == null) && 
			(lowerLongitude == null) && (upperLongitude == null)) {
			if("orderByTime".equals(orderType)) {
				findRange = articleTopicCurrentService.findByTopRange(begin, end, "orderByTime");
			}else if("orderByLike".equals(orderType)) {
				findRange = articleTopicCurrentService.findByTopRange(begin, end, "orderByLike");
			}
		} else if ((begin == null) && (end == null) && ("".equals(orderType)) &&
				(lowerLatitude != null) && (upperLatitude != null) && 
				(lowerLongitude != null) && (upperLongitude != null)){
			findRange = articleTopicCurrentService.findByCoordinateRange(lowerLatitude, upperLatitude, lowerLongitude, upperLongitude);
		}
		
		if (findRange != null) {
			return ResponseEntity.ok(findRange);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = { "/articleTopics/{id}" }, produces = { "application/json" })
	public ResponseEntity<?> getTopic(@PathVariable(name = "id") int id) {
		System.out.println("getTopic method running");
		ArticleTopicCurrentBean findOne = articleTopicCurrentService.findByPrimaryKey(id);
		if (findOne != null) {
			return ResponseEntity.ok(findOne);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(path = { "/articleTopics" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> postTopic(@RequestBody ArticleTopicCurrentBean requestbody) {
		ArticleTopicCurrentBean insertResult = articleTopicCurrentService.insert(requestbody);
		System.out.println("postTopic method running");
		if (insertResult != null) {
			return ResponseEntity
					.created(URI.create(application.getContextPath() + "/articleTopics/" + insertResult.getId()))
					.body(insertResult);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@PutMapping(path = { "/articleTopics/{id}" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> updateTopic(@PathVariable(name = "id") int id,
			@RequestBody ArticleTopicCurrentBean requestbody) {
		System.out.println("updateTopic method running");
		if (id == requestbody.getId().intValue()) {
			ArticleTopicCurrentBean updateResult = articleTopicCurrentService.updateIgnoreNullColumn(requestbody);
			if (updateResult != null) {
				return ResponseEntity.ok(updateResult);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = { "/articleTopics/{id}" })
	public ResponseEntity<?> deleteTopic(@PathVariable(name = "id") int id) {
		System.out.println("deleteTopic method running");
		boolean deleteResult = articleTopicCurrentService.delete(id);
		if (deleteResult) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
