package com.mashup.ootd.domain.post.dto;

import org.springframework.web.multipart.MultipartFile;

import com.mashup.ootd.domain.post.entity.Post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostCreateRequest {

	private Long userId;
	private MultipartFile uploadFile;
	private String message;
	private String address;
	private String weather;
	private String temperature;
	
	public Post toEntity(String url) {
		return Post.builder()
				.photoUrl(url)
				.message(message)
				.address(address)
				.weather(weather)
				.temperature(temperature)
				.build();
	}
	
}
