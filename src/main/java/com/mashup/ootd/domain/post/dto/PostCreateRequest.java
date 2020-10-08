package com.mashup.ootd.domain.post.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.mashup.ootd.domain.post.entity.Post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostCreateRequest {

	private MultipartFile uploadFile;
	private String message;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	private String address;
	private String weather;
	private Integer temperature;
	// TODO 추후 임시 디폴트 값 제거
	private List<Long> styleIds = Arrays.asList(1L, 5L);

	public Post toEntity(String url) {
		return Post.builder()
				.photoUrl(url)
				.message(message)
				.date(date)
				.address(address)
				.weather(weather)
				.temperature(temperature)
				.build();
	}
	
}
