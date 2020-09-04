package com.mashup.ootd.domain.post.dto;

import java.util.List;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import com.mashup.ootd.domain.post.entity.Post;

@Getter
@Setter
@ToString
//new code
@AllArgsConstructor
@NoArgsConstructor
//end
public class PostCreateRequest {

	private Long userId;
	private MultipartFile uploadFile;
	private String message;
	private String address;
	private String weather;
	private String temperature;
	private String lat;
	private String lon;
	private List<Long> styleIds;

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
