package net.ddns.eeitdemo.eeit106team01.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentService;

@RestController
public class ArticleContentController {

	@Autowired
	private ArticleContentCurrentService articleContentCurrentService;

	@GetMapping(path = { "/articleTopics/{id}/articleContents" }, produces = {
			"application/json" })
	public ResponseEntity<?> getVideoList(
			@PathVariable String id,
			@RequestParam Integer begin, 
			@RequestParam Integer end,
			@RequestParam String orderType
			) {
		return ResponseEntity.ok(articleContentCurrentService.findAll());
	}
}
