package me.seonkyukim.springbootdeveloper.service;

import me.seonkyukim.springbootdeveloper.dto.UploadResponse;

public interface FileStorageService {
	UploadResponse store(byte[] bytes, String filename); 
}
