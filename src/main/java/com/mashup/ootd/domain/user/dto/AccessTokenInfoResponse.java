package com.mashup.ootd.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccessTokenInfoResponse {

	private Long userId;

	public static AccessTokenInfoResponse of(Long userId) {
		return new AccessTokenInfoResponse(userId);
	}

}
