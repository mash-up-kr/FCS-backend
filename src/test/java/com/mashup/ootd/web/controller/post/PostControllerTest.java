package com.mashup.ootd.web.controller.post;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.mashup.ootd.config.JsonConfig;
import com.mashup.ootd.domain.jwt.service.JwtService;
import com.mashup.ootd.domain.post.dto.PostCreateResponse;
import com.mashup.ootd.domain.post.dto.PostListResponse;
import com.mashup.ootd.domain.post.dto.PostResponse;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.service.PostService;
import com.mashup.ootd.domain.user.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@AutoConfigureRestDocs
@Import(JsonConfig.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;
	
	@MockBean
	private JwtService jwtService;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void test_create() throws Exception {
		
		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		PostCreateResponse response = new PostCreateResponse(1L, ".../image.png");
		given(postService.create(any(), any())).willReturn(response);
		
		// when
		ResultActions result = mockMvc.perform(fileUpload("/api/posts")
				.file("uploadFile", "image".getBytes())
				.header(HttpHeaders.AUTHORIZATION,
						"eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y")
				.param("message", "메세지")
				.param("date", "2020-09-05")
				.param("address", "주소")
				.param("weather", "날씨")
				.param("temperature", "20")
				.param("styleIds", "1,2,3"))
				.andExpect(status().isCreated());

		// then
		result.andDo(print())
				.andExpect(status().isCreated())
				.andDo(document("upload",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						requestParts(
								partWithName("uploadFile").description("업로드 할 이미지")
						), 
						requestParameters(
								parameterWithName("message").description("피드 문구 입력"),
								parameterWithName("date").description("피드 업로드 설정 날짜"),
								parameterWithName("address").description("주소 입력"),
								parameterWithName("weather").description("날씨 입력"),
								parameterWithName("temperature").description("온도 입력"),
								parameterWithName("styleIds").description("스타일 id 입력")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메세지"),
								fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("포스트 고유 id"),
								fieldWithPath("data.photoUrl").type(JsonFieldType.STRING).description("사진 url")
						)
				));
	}
	
	@Test
	void test_list() throws Exception {
		
		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		PostResponse post1 = PostResponse.builder()
				.id(1L)
				.photoUrl(".../image.png")
				.message("업로드 메시지")
				.address("서울")
				.weather("hot")
				.temperature(30)
				.styleIds(Arrays.asList(1L, 2L, 3L))
				.date("20년 8월 16일 일요일")
				.nickname("유저1")
				.build();
		
		PostResponse post2 = PostResponse.builder()
				.id(1L)
				.photoUrl(".../image2.png")
				.message("업로드 메시지2")
				.address("부산")
				.weather("cold")
				.temperature(10)
				.styleIds(Arrays.asList(1L, 2L, 3L))
				.date("21년 1월 16일 일요일")
				.nickname("유저2")
				.build();
		
		List<PostResponse> posts = Arrays.asList(post1, post2);
		
		PostListResponse response = PostListResponse.of(posts, true);
		given(postService.list(any())).willReturn(response);
		
		// when
		ResultActions result = mockMvc.perform(get("/api/posts?styleIds=1,2&weather=CLEAR&minTemp=21&maxTemp=23&lastPostId=10")
				.header(HttpHeaders.AUTHORIZATION,
						"eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		result.andExpect(status().isOk())
				.andDo(document("post-list",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						requestParameters(
								parameterWithName("styleIds").description("스타일 Id 목록"),
								parameterWithName("weather").description("날씨"),
								parameterWithName("minTemp").description("최소 온도"),
								parameterWithName("maxTemp").description("최대 온도"),
								parameterWithName("lastPostId").description("마지막 피드 번호(첫 페이지에선 해당 파라미터 없이 요청)")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메세지"),
								fieldWithPath("data.posts[].id").type(JsonFieldType.NUMBER).description("포스트 고유 id"),
								fieldWithPath("data.posts[].photoUrl").type(JsonFieldType.STRING).description("사진 url"),
								fieldWithPath("data.posts[].message").type(JsonFieldType.STRING).description("피드 문구 내용"),
								fieldWithPath("data.posts[].address").type(JsonFieldType.STRING).description("위치"),
								fieldWithPath("data.posts[].weather").type(JsonFieldType.STRING).description("날씨"),
								fieldWithPath("data.posts[].temperature").type(JsonFieldType.NUMBER).description("온도"),
								fieldWithPath("data.posts[].styleIds").type(JsonFieldType.ARRAY).description("스타일 정보"),
								fieldWithPath("data.posts[].date").type(JsonFieldType.STRING).description("업로드 날짜"),
								fieldWithPath("data.posts[].nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
								fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 유무")
						)
				));
	}

}
