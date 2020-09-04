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
		Long id = 1L;

		// when
		String jws = jwtService.createUserJwt(id);
		Long jwsId = new Long((int)jwtService.getJwsClaims(jws).getBody().get("id"));

		// then
		assertThat(jwsId).isEqualTo(id);
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
		Long id = 1L;
		
		// when
		String jws = jwtService.createUserJwt(id);

		// then
		assertThat(jwtService.isUsable(jws)).isTrue();
	}

}
