package net.ddns.eeitdemo.eeit106team01.forum.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;

//@RestController
public class postController {
	
	@PostMapping(path = {"/posts"})
	public String postArticle1(@RequestBody ArticleTopicCurrentBean articleTopicCurrentBean) {
//		System.out.println("postArticle1 ResponseEntity");
//		ProductBean result = productDAO.insert(requestBody);
//		if (result != null) {
//			return ResponseEntity.created(URI.create(application.getContextPath() + "/products/" + result.getId()))
//					.body(result);
//		} else {
//			return ResponseEntity.noContent().build();
//		}
		return "";
	}
}
