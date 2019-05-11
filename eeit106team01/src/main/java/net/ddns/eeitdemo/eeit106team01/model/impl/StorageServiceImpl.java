package net.ddns.eeitdemo.eeit106team01.model.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.ddns.eeitdemo.eeit106team01.model.StorageService;

@Component
public class StorageServiceImpl implements StorageService {
	private String storagePath = "storage/";

	public StorageServiceImpl() {
		File storage = new File(storagePath);
		if (!storage.exists()) {
			storage.mkdirs();
		}
	}

	public File createFile(String filePath) throws IOException {
		if (filePath.startsWith("/") || filePath.startsWith("\\")) {
			filePath = filePath.substring(1);
		}
		File file = new File(storagePath + filePath);
		file.getParentFile().mkdirs();
		file.createNewFile();
		return file;
	}

	public File store(MultipartFile file, String fileName) throws IOException {

		File result = null;
		File outFile = new File("/" + System.currentTimeMillis() + file.getOriginalFilename());
		outFile.mkdirs();
		outFile.createNewFile();
		try (InputStream is = file.getInputStream(); OutputStream os = new FileOutputStream(outFile);) {
			byte[] bytes = new byte[8 * 1024];
			int len;
			while ((len = is.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
		}

		return result;
	}

	public File load(String filePath) {
		if (filePath.startsWith("/") || filePath.startsWith("\\")) {
			filePath = filePath.substring(1);
		}
		File file = new File(storagePath + filePath);
		if (file.exists()) {
			return file;
		} else {
			return null;
		}
	}

	public Resource loadAsResource(String filePath) {
		if (filePath.startsWith("/") || filePath.startsWith("\\")) {
			filePath = filePath.substring(1);
		}
		File file = new File(storagePath + filePath);
		if (file.exists()) {
			return new FileSystemResource(file);
		} else {
			return null;
		}
	}

	public boolean delete(String filePath) {
		return new File(storagePath + filePath).delete();
	}

}
