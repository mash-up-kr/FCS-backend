package com.mashup.ootd.web.message;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OotdResponse<T> {

	private int code;
	private String msg;
	private T data;

}
