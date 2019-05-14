package net.ddns.eeitdemo.eeit106team01.forum.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.ddns.eeitdemo.eeit106team01.forum.model.VideoBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.VideoService;
import net.ddns.eeitdemo.eeit106team01.model.StorageService;
import net.ddns.eeitdemo.eeit106team01.utils.FFmpegUtils;

@RestController
public class VideoController {
	@Autowired
	private VideoService videoService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private Environment env;

	@GetMapping(path = { "/video" }, produces = { "application/json" })
	public ResponseEntity<?> getVideoList() {
		return ResponseEntity.ok(videoService.findAll());
	}

	@GetMapping(path = { "/video/{id}" }, produces = { "application/json" })
	public ResponseEntity<?> getVideo(@PathVariable String id) {
		if (id != null && id.length() != 0) {
			try {
				Integer intId = Integer.valueOf(id);
				VideoBean videoBean = videoService.findByPrimaryKey(intId);
				if (videoBean != null) {
					return ResponseEntity.ok(videoBean);
				}
			} catch (NumberFormatException e) {
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(path = { "/video" })
	public ResponseEntity<?> uploadVideo(@RequestParam("videoFile") MultipartFile videoFile, Model model) {
		if (videoFile == null || videoFile.getSize() == 0 || !videoFile.getContentType().startsWith("video/")) {
			return ResponseEntity.noContent().header("errorMsg", "upload file not exist").build();
		} else {
			try {
				long uploadTime = System.currentTimeMillis();
				File outFile = storageService.createFile("video/" + uploadTime + videoFile.getOriginalFilename());
				try (InputStream is = videoFile.getInputStream(); OutputStream os = new FileOutputStream(outFile);) {
					byte[] bytes = new byte[8 * 1024];
					int len;
					while ((len = is.read(bytes)) != -1) {
						os.write(bytes, 0, len);
					}
				}
				FFmpegUtils ffu = null;
				String ffmpegPath = env.getProperty("path.ffmpeg");
				String ffprobePath = env.getProperty("path.ffprobe");
				if (ffmpegPath != null && ffmpegPath.length() != 0 && ffprobePath != null
						&& ffprobePath.length() != 0) {
					System.out.println("use specify path of ffmpeg and ffprobe");
					ffu = new FFmpegUtils(ffmpegPath, ffprobePath);
				} else {
					System.out.println("use default path of ffmpeg and ffprobe");
					ffu = new FFmpegUtils();
				}

				VideoBean videoBean = new VideoBean();
				videoBean.setUploadTime(new java.util.Date(uploadTime));
				videoBean.setVideoURI(outFile.getName());
				videoBean.setVideoStatus("processing");
				videoBean.setVideoLength(ffu.getVideoDuration(outFile));
				videoBean.setUpdateMessage("new insert");
				try {
					videoBean = videoService.insert(videoBean);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return ResponseEntity.noContent().header("errorMsg", "video insert failed : " + e.getMessage())
							.build();
				}

				File outFileJpg = storageService.createFile("jpg/" + outFile.getName() + ".jpg");
				File outFileGif = storageService.createFile("gif/" + outFile.getName() + ".gif");
				ffu.generateThumbnail(outFile, outFileJpg, -1);
				ffu.generateDefaultGifCut(outFile, outFileGif);

				videoBean.setThumbnailURI(outFileJpg.getName());
				videoBean.setGifURI(outFileGif.getName());
				videoBean.setUpdateMessage("new insert: Generating thumbnail and gif completed.");
				videoService.update(videoBean);

				String encodeName = new URLEncoder().encode(videoBean.getVideoURI(), Charset.forName("UTF-8"));
				return ResponseEntity.created(URI.create("storage/video/" + encodeName)).build();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return ResponseEntity.noContent().header("errorMsg", e.getMessage()).build();
			}
		}
	}

	@PutMapping(path = { "/video/{id}" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> updateVideo(@PathVariable String id, @RequestBody VideoBean videoBean,
			BindingResult bindingResult) {
		if (id != null && id.length() != 0) {
			if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
				Map<String, String> errors = new HashMap<String, String>();
				if (bindingResult.hasFieldErrors("uploadTime")) {
					errors.put("uploadTime", "uploadTime invalid.");
				}
				if (bindingResult.hasFieldErrors("videoStatus")) {
					errors.put("videoStatus", "videoStatus invalid.");
				}
				if (bindingResult.hasFieldErrors("videoURI")) {
					errors.put("videoURI", "videoURI invalid.");
				}
				if (bindingResult.hasFieldErrors("thumbnailURI")) {
					errors.put("thumbnailURI", "thumbnailURI invalid.");
				}
				if (bindingResult.hasFieldErrors("gifURI")) {
					errors.put("gifURI", "gifURI invalid.");
				}
				if (bindingResult.hasFieldErrors("videoLength")) {
					errors.put("videoLength", "videoLength invalid.");
				}
				if (bindingResult.hasFieldErrors("updateMessage")) {
					errors.put("updateMessage", "updateMessage invalid.");
				}
				return ResponseEntity.badRequest().body(errors);
			}
			try {
				Integer intId = Integer.valueOf(id);
				VideoBean result = videoService.findByPrimaryKey(intId);
				if (result != null) {
					result = videoService.update(videoBean);
					return ResponseEntity.ok(result);
				}
			} catch (NumberFormatException e) {
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = { "/video/{id}" }, produces = { "application/json" })
	public ResponseEntity<?> deleteVideo(@PathVariable String id) {
		if (id != null && id.length() != 0) {
			try {
				Integer intId = Integer.valueOf(id);
				VideoBean videoBean = videoService.findByPrimaryKey(intId);
				if (videoBean != null) {
					if(videoService.delete(intId)) {
						storageService.delete("jpg/" + videoBean.getThumbnailURI());
						storageService.delete("gif/" + videoBean.getGifURI());
						storageService.delete("video/" + videoBean.getVideoURI());
						return ResponseEntity.ok(videoBean);
					}
				}
			} catch (NumberFormatException e) {
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
}
