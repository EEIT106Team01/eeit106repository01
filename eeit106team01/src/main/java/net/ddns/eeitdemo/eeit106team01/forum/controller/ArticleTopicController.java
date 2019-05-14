package net.ddns.eeitdemo.eeit106team01.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentService;

@RestController
public class ArticleTopicController {
	
	@Autowired
	private ArticleTopicCurrentService articleTopicCurrentService;
	
	@GetMapping("/articleTopics")
	public ResponseEntity<?> method() {
		return ResponseEntity.ok(articleTopicCurrentService.findAll()) ;
	}
}
