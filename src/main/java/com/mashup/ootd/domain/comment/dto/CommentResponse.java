package com.mashup.ootd.domain.comment.dto;

import com.mashup.ootd.domain.comment.entity.Comment;
import com.mashup.ootd.util.DateUtils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponse {

	private Long id;
	private String message;
	private String userNickname;
	private String createdDate;
	
	public static CommentResponse of(Comment comment) {
		return CommentResponse.builder()
				.id(comment.getId())
				.message(comment.getMessage())
				.userNickname(comment.getUser().getNickname())
				.createdDate(DateUtils.toDateStr(comment.getCreatedAt()))
				.build();
	}

}
