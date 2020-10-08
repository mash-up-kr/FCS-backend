package com.mashup.ootd.domain.user.dto;

import java.util.List;

import com.mashup.ootd.domain.user.entity.AuthType;
import com.mashup.ootd.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpRequest {

	private String uid;
	private String authType;
	private String nickname;
	private String profileImageUrl;
	private List<Long> styleIds;
	
	public AuthType getAuthType() {
		return AuthType.valueOf(authType);
	}

	public User toEntity() {
		return User.builder()
				.uid(uid)
				.authType(authType)
				.nickname(nickname)
				.profileImageUrl(profileImageUrl)
				.build();
	}
}
