package com.mashup.ootd.domain.style.dto;

import com.mashup.ootd.domain.style.domain.Style;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class StyleResponse {
	
	private Long id;
	private String name;
	
	public static StyleResponse of(Style style) {
		
		return StyleResponse.builder()
				.id(style.getId())
				.name(style.getName())
				.build();
	}

}
