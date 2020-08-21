package com.mashup.ootd.web.controller.post;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static java.util.stream.Collectors.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.mashup.ootd.ApiDocumentUtils.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.mashup.ootd.config.JsonConfig;
import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.dto.PostCreateResponse;
import com.mashup.ootd.domain.post.dto.PostGetResponse;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.service.PostService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@AutoConfigureRestDocs
@Import(JsonConfig.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;
	
	@Test
	public void test_create() throws Exception {
		
		// given
		PostCreateResponse response = new PostCreateResponse(1L, ".../image.png");
		given(postService.create(any())).willReturn(response);
		
		// when
		ResultActions result = mockMvc.perform(fileUpload("/api/posts")
				.file("uploadFile", "image".getBytes())
				.param("userId", "1")
				.param("message", "메세지")
				.param("address", "주소")
				.param("weather", "날씨")
				.param("temperature", "온도")
				.param("styleIds", "1,2,3"))
				.andExpect(status().isCreated());

		// then
		result.andDo(print())
				.andExpect(status().isCreated())
				.andDo(document("upload",
						getDocumentRequest(),
						getDocumentResponse(),
						requestParts(
								partWithName("uploadFile").description("업로드 할 이미지")
						), 
						requestParameters(
								parameterWithName("userId").description("유저 id 입력"),
								parameterWithName("message").description("주소 입력"),
								parameterWithName("address").description("주소 입력"),
								parameterWithName("weather").description("날씨 입력"),
								parameterWithName("temperature").description("온도 입력"),
								parameterWithName("styleIds").description("스타일 id 입력")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메세지"),
								fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("포스트 고유 id"),
								fieldWithPath("data.photo_url").type(JsonFieldType.STRING).description("사진 url")
						)
				));
	}
	
	@Test
	void test_list() throws Exception {
		
		// given
		Post post1 = Post.builder()
				.id(1L)
				.photoUrl(".../image.png")
				.message("업로드 메세지")
				.weather("hot")
				.temperature("30")
				.createdAt(LocalDateTime.of(2020, 8, 16, 12, 0, 0))
				.build();
		
		Post post2 = Post.builder()
				.id(2L)
				.photoUrl(".../image2.png")
				.message("업로드 메세지2")
				.weather("cold")
				.temperature("0")
				.createdAt(LocalDateTime.of(2021, 1, 16, 12, 0, 0))
				.build();
		
		List<Post> posts = Arrays.asList(post1, post2);
		List<PostGetResponse> response = posts.stream().map(PostGetResponse::new).collect(toList());
		
		given(postService.listTop20()).willReturn(response);
		
		// when
		ResultActions result = mockMvc.perform(get("/api/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		result.andExpect(status().isOk())
				.andDo(document("post-list",
						getDocumentRequest(),
						getDocumentResponse(),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메세지"),
								fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("포스트 고유 id"),
								fieldWithPath("data[].photo_url").type(JsonFieldType.STRING).description("사진 url"),
								fieldWithPath("data[].message").type(JsonFieldType.STRING).description("메시지 내용"),
								fieldWithPath("data[].weather").type(JsonFieldType.STRING).description("날씨"),
								fieldWithPath("data[].temperature").type(JsonFieldType.STRING).description("온도"),
								fieldWithPath("data[].date").type(JsonFieldType.STRING).description("업로드 날짜")
						)
				));
	}

}
