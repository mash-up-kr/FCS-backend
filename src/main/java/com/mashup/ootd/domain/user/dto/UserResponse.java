package com.mashup.ootd.domain.user.dto;

import java.util.List;

import com.mashup.ootd.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserResponse {

	private String nickname;
	private List<Long> styleIds;

	public UserResponse(User user) {
		this(user.getNickname(), user.getStyleIds());
	}

	public UserResponse(String nickname, List<Long> styleIds) {
		this.nickname = nickname;
		this.styleIds = styleIds;
	}

}
