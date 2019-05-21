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

import javax.servlet.http.HttpSession;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.VideoBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.VideoService;
import net.ddns.eeitdemo.eeit106team01.model.StorageService;
import net.ddns.eeitdemo.eeit106team01.utils.FFmpegUtils;

//@RestController
public class VideoController {
	@Autowired
	private VideoService videoService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private FFmpegUtils ffu;

	@GetMapping(path = { "/videos" }, produces = { "application/json" })
	public ResponseEntity<?> getVideoList() {
		return ResponseEntity.ok(videoService.findAll());
	}

	@GetMapping(path = { "/videos/{id}" }, produces = { "application/json" })
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

	@PostMapping(path = { "/videos" }, produces = { "application/json" })
	public ResponseEntity<?> uploadVideo(@RequestParam("videoFile") MultipartFile videoFile, HttpSession httpSession) {
		if (videoFile == null || videoFile.getSize() == 0 || !videoFile.getContentType().startsWith("video/")) {
			return ResponseEntity.noContent().header("errorMsg", "upload file not exist").build();
		} else {
			try {
				long uploadTime = System.currentTimeMillis();
				File outFile = storageService.createFile("videos/" + uploadTime + videoFile.getOriginalFilename());
				try (InputStream is = videoFile.getInputStream(); OutputStream os = new FileOutputStream(outFile);) {
					byte[] bytes = new byte[8 * 1024];
					int len;
					while ((len = is.read(bytes)) != -1) {
						os.write(bytes, 0, len);
					}
				}
				MemberBean memberBean = (MemberBean) httpSession.getAttribute("MemberBean");
				VideoBean videoBean = new VideoBean();
				videoBean.setUploadTime(new java.util.Date(uploadTime));
				videoBean.setVideoURI(outFile.getName());
				videoBean.setVideoStatus("processing");
				videoBean.setVideoLength(ffu.getVideoDuration(outFile));
				videoBean.setUpdateMessage("new insert");
				videoBean.setMemberBean(memberBean);
				try {
					videoBean = videoService.insert(videoBean);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return ResponseEntity.noContent().header("errorMsg", "video insert failed : " + e.getMessage())
							.build();
				}
				ProcessVideo processVideo = new ProcessVideo(outFile, videoBean);
				Thread thread = new Thread(processVideo);
				thread.start();

				String encodeName = new URLEncoder().encode(videoBean.getVideoURI(), Charset.forName("UTF-8"));
				return ResponseEntity.created(URI.create("storage/videos/" + encodeName)).body(videoBean);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return ResponseEntity.noContent().header("errorMsg", e.getMessage()).build();
			}
		}
	}

	@PutMapping(path = { "/videos/{id}" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> updateVideo(@PathVariable Integer id, @RequestBody VideoBean videoBean,
			BindingResult bindingResult) {
		if (id != null && id.intValue() != 0) {
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
			if ((videoBean != null) && (id == videoBean.getId())) {
				VideoBean result = videoService.update(videoBean);
				if (result != null) {
					return ResponseEntity.ok(result);
				}
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = { "/videos/{id}" }, produces = { "application/json" })
	public ResponseEntity<?> deleteVideo(@PathVariable String id) {
		if (id != null && id.length() != 0) {
			try {
				Integer intId = Integer.valueOf(id);
				VideoBean videoBean = videoService.findByPrimaryKey(intId);
				if (videoBean != null) {
					if (videoService.delete(intId)) {
						storageService.delete("jpgs/" + videoBean.getThumbnailURI());
						storageService.delete("gifs/" + videoBean.getGifURI());
						storageService.delete("videos/" + videoBean.getVideoURI());
						return ResponseEntity.ok(videoBean);
					}
				}
			} catch (NumberFormatException e) {
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	class ProcessVideo implements Runnable {
		private File outFile;
		private VideoBean videoBean;
		
		public ProcessVideo(File outFile, VideoBean videoBean) {
			this.outFile = outFile;
			this.videoBean = videoBean;
		}
		
		@Override
		public void run() {
			try {
				File outFileJpg = storageService.createFile("jpgs/" + outFile.getName() + ".jpg");
				File outFileGif = storageService.createFile("gifs/" + outFile.getName() + ".gif");
				ffu.generateThumbnail(outFile, outFileJpg, -1);
				ffu.generateDefaultGifCut(outFile, outFileGif);

				videoBean.setThumbnailURI(outFileJpg.getName());
				videoBean.setGifURI(outFileGif.getName());
				videoBean.setUpdateMessage("new insert: Generating thumbnail and gif completed.");
				videoService.update(videoBean);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
