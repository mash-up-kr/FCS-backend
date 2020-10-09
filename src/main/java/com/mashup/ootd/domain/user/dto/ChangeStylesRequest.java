package com.mashup.ootd.domain.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChangeStylesRequest {
	
	private List<Long> styleIds;

}
