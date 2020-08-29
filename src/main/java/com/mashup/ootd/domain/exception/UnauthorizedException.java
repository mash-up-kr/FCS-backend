package com.mashup.ootd.domain.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {

	private static final long serialVersionUID = 5437269450800063397L;

	public static final String JWT_TOKEN_ERROR_MSG = "올바르지 않은 토큰입니다.";

	public UnauthorizedException(String errorMsg) {
		super(HttpStatus.UNAUTHORIZED, 4100, errorMsg);
	}

}
