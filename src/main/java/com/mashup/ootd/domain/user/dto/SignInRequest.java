package com.mashup.ootd.domain.user.dto;

import com.mashup.ootd.domain.user.entity.AuthType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignInRequest {

	private String uid;
	private String authType;

	public AuthType getAuthType() {
		return AuthType.valueOf(authType);
	}
	
}
