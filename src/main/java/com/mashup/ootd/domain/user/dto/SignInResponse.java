package com.mashup.ootd.domain.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponse {

	private String nickname; 
	private List<Long> styleIds;
	
}
