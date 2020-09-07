package com.mashup.ootd.domain.post.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PostListRequest {

	private Set<Long> styleIds;
	private String weather;
	private int minTemp;
	private int maxTemp;
	private Long lastPostId;

	public Long getLastPostId() {
		if (lastPostId == null)
			return Long.MAX_VALUE;

		return lastPostId;
	}

}
