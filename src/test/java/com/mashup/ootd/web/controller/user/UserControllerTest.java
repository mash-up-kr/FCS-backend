package com.mashup.ootd.web.controller.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static com.mashup.ootd.ApiDocumentUtils.*;
import static java.util.stream.Collectors.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashup.ootd.domain.exception.DuplicateException;
import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.exception.UnauthorizedException;
import com.mashup.ootd.domain.post.dto.PostCreateResponse;
import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.user.dto.AccessTokenInfoResponse;
import com.mashup.ootd.domain.user.dto.ChangeNicknameRequest;
import com.mashup.ootd.domain.user.dto.ChangeProfileImageRequest;
import com.mashup.ootd.domain.user.dto.ChangeProfileImageResponse;
import com.mashup.ootd.domain.user.dto.ChangeStylesRequest;
import com.mashup.ootd.domain.user.dto.SignInRequest;
import com.mashup.ootd.domain.user.dto.SignUpRequest;
import com.mashup.ootd.domain.user.entity.AuthType;
import com.mashup.ootd.domain.user.entity.User;
import com.mashup.ootd.web.controller.ControllerTest;

@WebMvcTest(UserController.class)
public class UserControllerTest extends ControllerTest {

	@Test
	void test_SignUp() throws JsonProcessingException, Exception {
		// given
		String jws = "eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y";
		given(jwtService.createUserJwt(any())).willReturn(jws);
		
		User user = getMockUser();
		
		given(userService.signUp(any())).willReturn(user);
		
		// when
		SignUpRequest dto = new SignUpRequest("123", "KAKAO", "닉네임", "profileImageUrl", Arrays.asList(1L, 2L, 3L));
		
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
								fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("유저 프로필 이미지 url"),
								fieldWithPath("styleIds").type(JsonFieldType.ARRAY).description("선택한 스타일 Id 목록")
						),
						responseHeaders(
								headerWithName(UserController.ACCESS_TOKEN_HEADER_NAME).description("JWT 토큰")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메시지"),
								fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
								fieldWithPath("data.profileImageUrl").type(JsonFieldType.STRING).description("유저 프로필 이미지 url"),
								fieldWithPath("data.styleIds").type(JsonFieldType.ARRAY).description("스타일 정보")
						)
				));
	}
	
	@Test
	void test_signUp_duplicateException() throws JsonProcessingException, Exception {
		// given
		doThrow(new DuplicateException()).when(userService).signUp(any());
		
		// when
		SignUpRequest dto = new SignUpRequest("123", "KAKAO", "닉네임", "profileImageUrl", Arrays.asList(1L, 2L, 3L));
		
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
		
		User user = getMockUser();
		given(userService.signIn(any())).willReturn(user);
		
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
								headerWithName(UserController.ACCESS_TOKEN_HEADER_NAME).description("JWT 토큰")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메시지"),
								fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
								fieldWithPath("data.profileImageUrl").type(JsonFieldType.STRING).description("유저 프로필 이미지 url"),
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
	
	@Test
	void test_getInfo() throws Exception {
		
		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		given(userService.getInfo(any())).willReturn(AccessTokenInfoResponse.of(1L));
		
		// when
		String jwt = "eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y";
		ResultActions result = mockMvc.perform(get("/api/users/access-token-info")
				.header(HttpHeaders.AUTHORIZATION, jwt)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		result.andDo(print())
				.andExpect(status().isOk())
				.andDo(document("access-token-info",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메시지"),
								fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("유저 Id")
						)
				));
	}
	
	@Test
	void test_UnauthorizedException() throws JsonProcessingException, Exception {
		// given
		doThrow(new UnauthorizedException(UnauthorizedException.JWT_TOKEN_ERROR_MSG))
			.when(jwtService).isUsable(any());
		
		// when
		ResultActions result = mockMvc.perform(get("/api/users/access-token-info")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		result.andDo(print())
				.andExpect(status().isUnauthorized())
				.andDo(document("UnauthorizedException",
						getDocumentRequest(),
						getDocumentResponse(),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("에러 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("에러 메세지")
						)
				));
	}
	
	@DisplayName("닉네임 중복검사 요청")
	@Test
	void test_checkDuplicate() throws Exception {
		// given
		String nickname = "ootd";
		given(userService.checkDuplicate(nickname)).willReturn(true);

		// when
		ResultActions result = mockMvc.perform(get("/api/users/nickname/check/{nickname}", nickname)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		result.andDo(print())
				.andExpect(status().isOk())
				.andDo(document("user-nickname-checkDuplicate",
						getDocumentRequest(),
						getDocumentResponse(),
						pathParameters(
								parameterWithName("nickname").description("닉네임")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메시지")
						)
				));
	}
	
	@Test
	@DisplayName("닉네임 중복인 경우")
	void test_checkDuplicate_conflict() throws Exception {
		// given
		String nickname = "ootd";
		given(userService.checkDuplicate(nickname)).willReturn(true);

		doThrow(new DuplicateException("중복된 닉네임입니다."))
			.when(userService).checkDuplicate(nickname);
		
		// when
		ResultActions result = mockMvc.perform(get("/api/users/nickname/check/{nickname}", nickname)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		// then
		result.andDo(print())
				.andExpect(status().isConflict())
				.andDo(document("user-nickname-checkDuplicate-conflict",
						getDocumentRequest(),
						getDocumentResponse(),
						pathParameters(
								parameterWithName("nickname").description("닉네임")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("에러 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("에러 메세지")
						)
				));
	}
	
	@Test
	@DisplayName("유저 스타일 변경")
	void test_changeStyles() throws Exception {

		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		given(userService.getInfo(any())).willReturn(AccessTokenInfoResponse.of(1L));
		
		// when
		ChangeStylesRequest dto = new ChangeStylesRequest(Arrays.asList(1L, 3L));
		
		String jwt = "eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y";
		ResultActions result = mockMvc.perform(put("/api/users/styles")
				.header(HttpHeaders.AUTHORIZATION, jwt)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
		
		// then
		result.andDo(print())
				.andExpect(status().isOk())
				.andDo(document("user-changeStyle",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						requestFields(
								fieldWithPath("styleIds").type(JsonFieldType.ARRAY).description("변경할 스타일 Id 목록")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메시지")
						)
				));
	}
	
	@Test
	@DisplayName("유저 닉네임 변경")
	void test_changeNickname() throws Exception {

		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		given(userService.getInfo(any())).willReturn(AccessTokenInfoResponse.of(1L));
		
		// when
		ChangeNicknameRequest dto = new ChangeNicknameRequest("닉네임");
		
		String jwt = "eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y";
		ResultActions result = mockMvc.perform(put("/api/users/nickname")
				.header(HttpHeaders.AUTHORIZATION, jwt)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
		
		// then
		result.andDo(print())
				.andExpect(status().isOk())
				.andDo(document("user-changeNickname",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						requestFields(
								fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메시지")
						)
				));
	}
	
	@Test
	@DisplayName("프로필 사진 변경")
	void test_changeProfileImage() throws Exception {

		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		ChangeProfileImageResponse response = new ChangeProfileImageResponse(".../image.png");
		given(userService.changeProfileImage(any(), any())).willReturn(response);
		
		// when
		String jwt = "eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y";
		ResultActions result = mockMvc.perform(fileUpload("/api/users/profile-image")
				.file("profileImage", "image".getBytes())
				.header(HttpHeaders.AUTHORIZATION, jwt))
				.andExpect(status().isOk());

		// then
		result.andDo(print())
				.andExpect(status().isOk())
				.andDo(document("user-changeProfileImage",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						requestParts(
								partWithName("profileImage").description("변경할 프로필 사진")
						), 
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메세지"),
								fieldWithPath("data.profileImageUrl").type(JsonFieldType.STRING).description("프로필 사진 url")
						)
				));
		
	}
	
	private User getMockUser() {
		User user = new User("1234", AuthType.KAKAO.toString(), "오늘옷", "profileImageUrl");
		
		List<Style> styles = new ArrayList<>();
		styles.add(Style.builder().id(1L).build());
		styles.add(Style.builder().id(2L).build());
		styles.add(Style.builder().id(3L).build());
		user.setStyles(styles);
		
		return user;
	}
}
