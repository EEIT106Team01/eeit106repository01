package net.ddns.eeitdemo.eeit106team01.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.forum.model.StorageService;

@RestController
public class StorageController {
	@Autowired
	private StorageService storageService;

	@GetMapping("/storage/{type}/{name}")
	public ResponseEntity<Resource> forwardResources(
			@PathVariable("type") String type,
			@PathVariable("name") String name
			) {
		if (type.equals("videos") || type.equals("jpgs") || type.equals("gifs") || type.equals("others")) {
			Resource resource = storageService.loadAsResource(type + "/" + name);
			if (resource != null) {
				return ResponseEntity.ok().body(resource);
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/storage/videos/{sprites}/{name}")
	public ResponseEntity<Resource> forwardSpritesResources(
			@PathVariable("sprites") String sprites,
			@PathVariable("name") String name
			) {
		Resource resource = storageService.loadAsResource("videos/" + sprites + "/" + name);
		if (resource != null) {
			return ResponseEntity.ok().body(resource);
		}
		return ResponseEntity.notFound().build();
	}
}
