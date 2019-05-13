package net.ddns.eeitdemo.eeit106team01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.model.StorageService;

@RestController
public class StorageController {
	@Autowired
	private StorageService storageService;

	@GetMapping("/storage/{type}/{name}")
	public ResponseEntity<Resource> forwardResources(@PathVariable("type") String type,
			@PathVariable("name") String name) {
//		System.out.println("forwardResources......" + type + "/" + name);
		if (type.equals("video") || type.equals("jpg") || type.equals("gif")) {
			Resource resource = storageService.loadAsResource(type + "/" + name);
			if (resource != null) {
				return ResponseEntity.ok().body(resource);
			}
		}
		return ResponseEntity.notFound().build();
	}
}
