package net.ddns.eeitdemo.eeit106team01.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.ddns.eeitdemo.eeit106team01.model.StorageService;
import net.ddns.eeitdemo.eeit106team01.utils.FFmpegUtils;

@RestController
public class UploadController {
	@Autowired
	private StorageService storageService;
	@Autowired
	private Environment env;

	@GetMapping(path = { "/video" }, produces = { "application/json" })
	public MultiValueMap<?, ?> getVideoList() {
		File videoDir = storageService.load("video");
		List<String> videoList = new ArrayList<String>();
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
		if (videoDir != null) {
			File[] files = videoDir.listFiles();
			for (File file : files) {
				if (!file.isDirectory()) {
					videoList.add(file.getName());
				}
			}
		}
		mvm.put("name", videoList);
		return mvm;
	}

	@PostMapping(path = { "/video" })
	public ResponseEntity<?> uploadVideo(@RequestParam("videoFile") MultipartFile videoFile, Model model) {
		if (videoFile == null || videoFile.getSize() == 0 || !videoFile.getContentType().startsWith("video/")) {
			return ResponseEntity.noContent().header("errorMsg", "upload file not exist").build();
		} else {
			try {
				File outFile = storageService
						.createFile("video/" + System.currentTimeMillis() + videoFile.getOriginalFilename());
				try (InputStream is = videoFile.getInputStream(); OutputStream os = new FileOutputStream(outFile);) {
					byte[] bytes = new byte[8 * 1024];
					int len;
					while ((len = is.read(bytes)) != -1) {
						os.write(bytes, 0, len);
					}
				}
				String ffmpegPath = env.getProperty("path.ffmpeg");
				String ffprobePath = env.getProperty("path.ffprobe");
				FFmpegUtils ffu = null;
				if (ffmpegPath != null && ffmpegPath.length() != 0 && ffprobePath != null
						&& ffprobePath.length() != 0) {
					System.out.println("use specify path of ffmpeg and ffprobe");
					ffu = new FFmpegUtils(ffmpegPath, ffprobePath);
				} else {
					System.out.println("use default path of ffmpeg and ffprobe");
					ffu = new FFmpegUtils();
				}
				File outFileJpg = storageService.createFile("jpg/" + outFile.getName() + ".jpg");
				File outFileGif = storageService.createFile("gif/" + outFile.getName() + ".gif");
				ffu.generateThumbnail(outFile, outFileJpg, -1);
				ffu.generateDefaultGifCut(outFile, outFileGif);
				String encodeName = new URLEncoder().encode(outFile.getName(), Charset.forName("UTF-8"));
				return ResponseEntity.created(URI.create("files/video/" + encodeName)).build();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return ResponseEntity.noContent().header("errorMsg", e.getMessage()).build();
			}
		}
	}
}
