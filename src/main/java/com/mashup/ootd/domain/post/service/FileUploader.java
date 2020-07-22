package com.mashup.ootd.domain.post.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {

	String upload(MultipartFile uploadFile, String dirName);

}
