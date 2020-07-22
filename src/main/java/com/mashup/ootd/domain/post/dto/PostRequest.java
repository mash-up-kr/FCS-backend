package com.mashup.ootd.domain.post.dto;

import org.springframework.web.multipart.MultipartFile;

import com.mashup.ootd.domain.post.entity.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

	private Long userId;
	private MultipartFile uploadFile;
	private String message;
	private String address;
	private String weather;
	private String temperature;
	
	public Post toEntity(String url) {
		return Post.builder()
				.userId(userId)
				.photoUrl(url)
				.message(message)
				.address(address)
				.weather(weather)
				.temperature(temperature)
				.build();
	}
	
}
