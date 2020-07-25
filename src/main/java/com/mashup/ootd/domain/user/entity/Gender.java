package com.mashup.ootd.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {

	MAN("남자"),

	WOMAN("여자");

	private String descr;

}
