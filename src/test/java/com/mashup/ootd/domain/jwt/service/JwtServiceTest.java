package com.mashup.ootd.domain.jwt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mashup.ootd.domain.exception.UnauthorizedException;
import com.mashup.ootd.domain.user.dto.SignInRequest;

public class JwtServiceTest {

	private JwtService jwtService;

	@BeforeEach
	void init() {
		jwtService = new JwtService();
		jwtService.setSecretKey("FCSOotdJwtServiceTestSecretKey20200822");
	}

	@Test
	void test_createUserJwtAndGet() {
		// given
		String uid = "1234";
		SignInRequest request = new SignInRequest(uid, "KAKAO");

		// when
		String jws = jwtService.createUserJwt(request);
		String jwsUid = (String) jwtService.getJwsClaims(jws).getBody().get("uid");

		// then
		assertThat(jwsUid).isEqualTo(uid);
	}

	@Test
	void test_getWithUnauthorizedException() {
		// given
		String jws = "abcdef1234567890";

		// when
		// then
		UnauthorizedException e = assertThrows(UnauthorizedException.class, () -> jwtService.getJwsClaims(jws));
		assertThat(e.getMessage()).isEqualTo(UnauthorizedException.JWT_TOKEN_ERROR_MSG);
	}

	@Test
	void test_isUsable() {
		// given
		SignInRequest request = new SignInRequest("1234", "KAKAO");

		// when
		String jws = jwtService.createUserJwt(request);

		// then
		assertThat(jwtService.isUsable(jws)).isTrue();
	}

}
