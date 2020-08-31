package com.mashup.ootd.web.controller.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static java.util.stream.Collectors.*;
import static org.mockito.BDDMockito.*;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashup.ootd.config.JsonConfig;
import com.mashup.ootd.domain.exception.DuplicateException;
import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.jwt.service.JwtService;
import com.mashup.ootd.domain.user.dto.SignInRequest;
import com.mashup.ootd.domain.user.dto.SignInResponse;
import com.mashup.ootd.domain.user.dto.SignUpRequest;
import com.mashup.ootd.domain.user.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
@Import(JsonConfig.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private JwtService jwtService;
	
	@Test
	void test_SignUp() throws JsonProcessingException, Exception {
		// given
		String jws = "eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y";

		given(jwtService.createUserJwt(any())).willReturn(jws);
		
		// when
		SignUpRequest dto = new SignUpRequest("123", "KAKAO", "닉네임", Arrays.asList(1L, 2L, 3L));
		
		ResultActions result = mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
		
		// then
		result.andDo(print())
				.andExpect(status().isOk())
				.andDo(document("user-signUp",
						getDocumentRequest(),
						getDocumentResponse(),
						requestFields(
								fieldWithPath("uid").type(JsonFieldType.STRING).description("OAuth 고유 id"),
								fieldWithPath("authType").type(JsonFieldType.STRING).description("OAuth 타입"),
								fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
								fieldWithPath("styleIds").type(JsonFieldType.ARRAY).description("선택한 스타일 Id 목록")
						),
						responseHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						)
				));
	}
	
	@Test
	void test_signUp_duplicateException() throws JsonProcessingException, Exception {
		// given
		doThrow(new DuplicateException()).when(userService).signUp(any());
		
		// when
		SignUpRequest dto = new SignUpRequest("123", "KAKAO", "닉네임", Arrays.asList(1L, 2L, 3L));
		
		ResultActions result = mockMvc.perform(post("/api/users/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
				
		// then
		result.andDo(print())
				.andExpect(status().isConflict())
				.andDo(document("DuplicateException",
						getDocumentRequest(),
						getDocumentResponse(),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("에러 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("에러 메세지")
						)
				));
		
	}

	@Test
	void test_signIn() throws JsonProcessingException, Exception {
		// given
		String jws = "eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y";
		given(jwtService.createUserJwt(any())).willReturn(jws);
		
		SignInResponse response = new SignInResponse("오늘옷", Arrays.asList(1L, 2L, 3L));
		given(userService.signIn(any())).willReturn(response);
		
		// when
		SignInRequest dto = new SignInRequest("123", "KAKAO");
		
		ResultActions result = mockMvc.perform(post("/api/users/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
		
		// then
		result.andDo(print())
				.andExpect(status().isOk())
				.andDo(document("user-signIn",
						getDocumentRequest(),
						getDocumentResponse(),
						requestFields(
								fieldWithPath("uid").type(JsonFieldType.STRING).description("OAuth 고유 id"),
								fieldWithPath("authType").type(JsonFieldType.STRING).description("OAuth 타입")
						),
						responseHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메시지"),
								fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
								fieldWithPath("data.styleIds").type(JsonFieldType.ARRAY).description("스타일 정보")
						)
				));
	}
	
	@Test
	void test_signIn_notFoundEntityException() throws JsonProcessingException, Exception {
		// given
		doThrow(new NotFoundEntityException()).when(userService).signIn(any());
		
		// when
		SignInRequest dto = new SignInRequest("123", "KAKAO");
		
		ResultActions result = mockMvc.perform(post("/api/users/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
		
		// then
		result.andDo(print())
				.andExpect(status().isNotFound())
				.andDo(document("NotFoundEntityException",
						getDocumentRequest(),
						getDocumentResponse(),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("에러 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("에러 메세지")
						)
				));
	}
}
