package com.mashup.ootd.domain.post.dto;

import lombok.Getter;

@Getter
public class PostCreateResponse {

	private Long id;
	private String photoUrl;

	public PostCreateResponse(Long id, String photoUrl) {
		this.id = id;
		this.photoUrl = photoUrl;
	}
}
