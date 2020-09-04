package com.mashup.ootd.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthType {
	
	KAKAO("카카오"),
	GOOGLE("구글"),
	APPLE("애플");

	private final String desc;

}
