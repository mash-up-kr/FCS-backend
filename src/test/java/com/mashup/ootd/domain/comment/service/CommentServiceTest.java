package com.mashup.ootd.domain.comment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mashup.ootd.domain.comment.dto.CommentCreateRequest;
import com.mashup.ootd.domain.comment.dto.CommentCreateResponse;
import com.mashup.ootd.domain.comment.entity.Comment;
import com.mashup.ootd.domain.comment.repository.CommentRepository;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.service.PostService;
import com.mashup.ootd.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
	
	@Mock
	private PostService postService;
	
	@Mock
	private CommentRepository commentRepository;
	
	@Test
	void test_save() {
		
		// given
		Post post = Post.builder()
				.message("포스팅")
				.build();
		Long postId = 1L;
		User user = new User("1234", "KAKAO", "닉네임");
		String message = "댓글";

		Comment comment = new Comment(message, user, post);
		given(commentRepository.save(any())).willReturn(comment);
		
		// when
		CommentService commentService = new CommentService(postService, commentRepository);
		CommentCreateRequest dto = new CommentCreateRequest(message);
		
		CommentCreateResponse response = commentService.save(user, postId, dto);
		
		// then
		assertThat(response).isNotNull();
		assertThat(response.getMessage()).isEqualTo(message);		
	}

}
