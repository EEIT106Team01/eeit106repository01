package net.ddns.eeitdemo.eeit106team01.model;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.Resource;

public interface StorageService {

	public File createFile(String filePath) throws IOException;

	public File load(String filePath);

	public Resource loadAsResource(String filePath);

	public boolean delete(String filePath);
}
