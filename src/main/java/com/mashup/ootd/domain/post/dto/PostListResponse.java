package com.mashup.ootd.domain.post.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostListResponse {

	private List<PostResponse> posts;
	private boolean hasNext;

	public static PostListResponse of(List<PostResponse> posts, boolean hasNext) {
		return PostListResponse.builder()
				.posts(posts)
				.hasNext(hasNext)
				.build();
	}
	
}
