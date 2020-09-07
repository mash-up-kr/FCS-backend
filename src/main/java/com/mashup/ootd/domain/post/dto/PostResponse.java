package com.mashup.ootd.domain.post.dto;

import java.util.List;

import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.util.DateUtils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponse {

	private Long id;
	private String photoUrl;
	private String message;
	private String address;
	private String weather;
	private Integer temperature;
	private List<Long> styleIds;
	private String date;
	private String nickname;

	public static PostResponse of(Post post) {
		return PostResponse.builder()
				.id(post.getId())
				.photoUrl(post.getPhotoUrl())
				.message(post.getMessage())
				.address(post.getAddress())
				.weather(post.getWeather())
				.temperature(post.getTemperature())
				.styleIds(post.getStyleIds())
				.date(DateUtils.toDateStr(post.getDate()))
				.nickname(post.getUser().getNickname())
				.build();
	}
}
