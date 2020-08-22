package com.mashup.ootd.domain.exception.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	private int errorCode;
	private String errorMsg;

	public static ErrorResponse of(int code, String message) {
		return new ErrorResponse(code, message);
	}

}
