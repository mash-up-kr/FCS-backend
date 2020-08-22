package com.mashup.ootd.domain.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
	
	private static final long serialVersionUID = -839257670118545983L;
	
	private int errorCode;
    private String errorMsg;
    
    public BaseException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
