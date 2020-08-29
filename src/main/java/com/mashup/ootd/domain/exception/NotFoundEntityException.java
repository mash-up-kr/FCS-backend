package com.mashup.ootd.domain.exception;

import org.springframework.http.HttpStatus;

public class NotFoundEntityException extends BaseException {

	private static final long serialVersionUID = -3062874594016180246L;

	public NotFoundEntityException(String errorMsg) {
		super(HttpStatus.NOT_FOUND, 4400, errorMsg);
	}

	public NotFoundEntityException() {
		this("해당 데이터가 존재하지 않습니다.");
	}

}
