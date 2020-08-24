package com.mashup.ootd.domain.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends BaseException {

	private static final long serialVersionUID = 4574651166712875323L;

	public DuplicateException(String errorMsg) {
		super(HttpStatus.CONFLICT, 4900, errorMsg);
	}

	public DuplicateException() {
		this("중복된 리소스입니다.");
	}

}
