package com.mashup.ootd.domain.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.exception.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class OotdExceptionHandler {
	
	@ExceptionHandler(NotFoundEntityException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundEntityException(NotFoundEntityException e) {
		log.error("NotFoundEntityException", e);

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponse.of(e.getErrorCode(), e.getErrorMsg()));
	}

}
