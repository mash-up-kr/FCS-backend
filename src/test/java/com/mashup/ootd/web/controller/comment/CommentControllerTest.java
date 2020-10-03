package com.mashup.ootd.web.controller.comment;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static com.mashup.ootd.ApiDocumentUtils.getDocumentRequest;
import static com.mashup.ootd.ApiDocumentUtils.getDocumentResponse;
import static java.util.stream.Collectors.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.mashup.ootd.domain.comment.dto.CommentCreateRequest;
import com.mashup.ootd.domain.comment.dto.CommentCreateResponse;
import com.mashup.ootd.domain.comment.dto.CommentResponse;
import com.mashup.ootd.domain.comment.service.CommentService;
import com.mashup.ootd.web.controller.ControllerTest;

@WebMvcTest(CommentController.class)
public class CommentControllerTest extends ControllerTest {
	
	@MockBean
	private CommentService commentService;
	
	@Test
	void test_create() throws Exception {
		
		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		CommentCreateResponse response = new CommentCreateResponse(1L, "댓글 작성");
		given(commentService.save(any(), anyLong(), any())).willReturn(response);
		
		// when
		CommentCreateRequest dto = new CommentCreateRequest("댓글 작성");
		
		ResultActions result = mockMvc.perform(post("/api/comments/{post-id}", 1)
				.header(HttpHeaders.AUTHORIZATION,
						"eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
		
		// then
		result.andExpect(status().isCreated())
				.andDo(document("comment-create",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						pathParameters(
								parameterWithName("post-id").description("포스트 고유 Id")
						),
						requestFields(
								fieldWithPath("message").type(JsonFieldType.STRING).description("작성한 댓글")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메세지"),
								fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("댓글 고유 id"),
								fieldWithPath("data.message").type(JsonFieldType.STRING).description("작성한 댓글")
						)
				));
	}
	
	@Test
	void test_list() throws Exception {
		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		CommentResponse comment1 = CommentResponse.builder()
				.id(1L)
				.message("댓글1")
				.userNickname("닉네임1")
				.createdDate("20년 10월 2일 금요일")
				.build();
		
		CommentResponse comment2 = CommentResponse.builder()
				.id(1L)
				.message("댓글2")
				.userNickname("닉네임2")
				.createdDate("20년 10월 3일 토요일")
				.build();
		
		List<CommentResponse> response = Arrays.asList(comment1, comment2);
		
		Long postId = 1L;
		given(commentService.list(postId)).willReturn(response);
		
		// when
		ResultActions result = mockMvc.perform(get("/api/comments/{post-id}", 1)
				.header(HttpHeaders.AUTHORIZATION,
						"eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		result.andExpect(status().isOk())
				.andDo(document("comment-list",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						pathParameters(
								parameterWithName("post-id").description("포스트 고유 Id")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메세지"),
								fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("포스트 고유 id"),
								fieldWithPath("data[].message").type(JsonFieldType.STRING).description("댓글"),
								fieldWithPath("data[].userNickname").type(JsonFieldType.STRING).description("유저 낙내암"),
								fieldWithPath("data[].createdDate").type(JsonFieldType.STRING).description("댓글 작성 날짜")
						)
				));
	}

}
