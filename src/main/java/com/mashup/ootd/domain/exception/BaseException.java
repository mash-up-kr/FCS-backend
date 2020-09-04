package com.mashup.ootd.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
	
	private static final long serialVersionUID = -839257670118545983L;
	
	private final HttpStatus status;
	private final int code;
    private final String msg;
    
    public BaseException(HttpStatus status, int code, String msg) {
        super(msg);
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

}
