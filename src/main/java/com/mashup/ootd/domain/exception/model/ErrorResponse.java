package com.mashup.ootd.domain.exception.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	private int code;
	private String msg;

	public static ErrorResponse of(int code, String msg) {
		return new ErrorResponse(code, msg);
	}

}
