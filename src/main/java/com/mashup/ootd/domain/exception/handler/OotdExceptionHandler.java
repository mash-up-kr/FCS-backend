package com.mashup.ootd.domain.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mashup.ootd.domain.exception.BaseException;
import com.mashup.ootd.domain.exception.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class OotdExceptionHandler {
	
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
		log.error(e.getMessage(), e);
		
		return ResponseEntity
				.status(e.getStatus())
				.body(ErrorResponse.of(e.getCode(), e.getMsg()));
	}

}
