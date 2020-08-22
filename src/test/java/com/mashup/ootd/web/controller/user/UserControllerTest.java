package com.mashup.ootd.web.controller.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.jwt.service.JwtService;
import com.mashup.ootd.domain.user.dto.SignInRequest;
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
	void test_signIn() throws JsonProcessingException, Exception {
		// given
		String jws = "eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y";
		
		given(jwtService.createUserJwt(any())).willReturn(jws);

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
								fieldWithPath("authType").type(JsonFieldType.STRING).description("OAuth 타입"),
								fieldWithPath("uid").type(JsonFieldType.STRING).description("OAuth 고유 id")
						),
						responseHeaders(
								headerWithName("Authorization").description("JWT 토큰")
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
						fieldWithPath("errorCode").type(JsonFieldType.NUMBER).description("에러 코드"),
						fieldWithPath("errorMsg").type(JsonFieldType.STRING).description("에러 메세지")
				)
		));
	}
}
