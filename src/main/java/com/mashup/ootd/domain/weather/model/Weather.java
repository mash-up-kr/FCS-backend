package com.mashup.ootd.domain.weather.model;

import io.jsonwebtoken.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Weather {

	CLEAR("맑음", "CLEAR"),

	CLOUDS("구름", "CLOUDS"),

	RAIN("비", "RAIN"),

	SNOW("눈", "SNOW"),

	DRIZZLE("이슬비", "RAIN"),

	THUNDERSTORM("천둥", "THUNDERSTORM");

	private String description;
	private String groupCode;
	
}
