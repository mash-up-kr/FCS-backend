package com.mashup.ootd.domain.post.dto;

import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.util.DateUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostGetResponse {

	private Long id;
	private String photoUrl;
	private String message;
	private String address;
	private String weather;
	private String temperature;
	private String date;

	public PostGetResponse(Post entity) {
		this.id = entity.getId();
		this.photoUrl = entity.getPhotoUrl();
		this.message = entity.getMessage();
		this.address = entity.getAddress();
		this.weather = entity.getWeather();
		this.temperature = entity.getTemperature();
		this.date = DateUtils.toDateStr(entity.getCreatedAt());
	}

}
