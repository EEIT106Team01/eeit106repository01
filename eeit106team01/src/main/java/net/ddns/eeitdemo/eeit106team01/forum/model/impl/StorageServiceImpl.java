package net.ddns.eeitdemo.eeit106team01.forum.model.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import net.ddns.eeitdemo.eeit106team01.model.StorageService;

/**
 * 
 * @author Chiayen
 *
 */
@Component
public class StorageServiceImpl implements StorageService {
	/**
	 * Specify the root path of storage directory.
	 */
	private static final String STORAGE_PATH = "storage/";

	/**
	 * Generate root storage directory.
	 */
	public StorageServiceImpl() {
		File storage = new File(STORAGE_PATH);
		if (!storage.exists()) {
			storage.mkdirs();
		}
	}

	/**
	 * Create file with specific path.<br>
	 * If file exist, will not override it.
	 * @param filePath File path, must include the file name.
	 * @return The created file.
	 */
	public File createFile(String filePath) throws IOException {
		if (filePath.startsWith("/") || filePath.startsWith("\\")) {
			filePath = filePath.substring(1);
		}
		File file = new File(STORAGE_PATH + filePath);
		file.getParentFile().mkdirs();
		file.createNewFile();
		return file;
	}
	
	/**
	 * Load file with specific path.
	 * @param filePath File path.
	 * @return Null if file not exist.
	 */
	public File load(String filePath) {
		if (filePath.startsWith("/") || filePath.startsWith("\\")) {
			filePath = filePath.substring(1);
		}
		File file = new File(STORAGE_PATH + filePath);
		if (file.exists()) {
			return file;
		} else {
			return null;
		}
	}

	/**
	 * Load file with specific path as resource.
	 * @param filePath File path.
	 * @return Null if file not exist.
	 */
	public Resource loadAsResource(String filePath) {
		if (filePath.startsWith("/") || filePath.startsWith("\\")) {
			filePath = filePath.substring(1);
		}
		File file = new File(STORAGE_PATH + filePath);
		if (file.exists()) {
			return new FileSystemResource(file);
		} else {
			return null;
		}
	}

	/**
	 * Delete file.
	 * @param filePath File path.
	 * @return True if and only if the file or directory is successfully deleted; false otherwise.
	 */
	public boolean delete(String filePath) {
		return new File(STORAGE_PATH + filePath).delete();
	}
}
