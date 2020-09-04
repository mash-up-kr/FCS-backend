package com.mashup.ootd.web.message;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OotdResponse<T> {

	private final int code;
	private final String msg;
	private final T data;

}
