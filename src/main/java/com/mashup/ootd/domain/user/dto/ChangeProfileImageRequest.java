package com.mashup.ootd.domain.user.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeProfileImageRequest {

	private MultipartFile profileImage;

}
