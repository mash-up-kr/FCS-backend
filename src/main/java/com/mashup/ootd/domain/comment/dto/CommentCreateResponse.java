package com.mashup.ootd.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentCreateResponse {

	private Long commentId;
	private String message;

}
