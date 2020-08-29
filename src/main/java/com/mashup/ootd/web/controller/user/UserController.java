package com.mashup.ootd.web.controller.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashup.ootd.domain.jwt.service.JwtService;
import com.mashup.ootd.domain.user.dto.SignInRequest;
import com.mashup.ootd.domain.user.dto.SignUpRequest;
import com.mashup.ootd.domain.user.service.UserService;
import com.mashup.ootd.web.message.OotdResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
	
	private final UserService userService;
	private final JwtService jwtService;
	
	@PostMapping("/sign-up")
	public ResponseEntity<OotdResponse<Void>> signUp(@RequestBody SignUpRequest dto) {
		userService.signUp(dto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, jwtService.createUserJwt(dto.getUid()));
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.headers(headers)
				.body(OotdResponse.<Void>builder()
						.code(HttpStatus.OK.value())
						.msg("회원가입 성공")
						.build());
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<OotdResponse<Void>> signIn(@RequestBody SignInRequest dto) {
		userService.signIn(dto);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, jwtService.createUserJwt(dto.getUid()));

		return ResponseEntity
				.status(HttpStatus.OK)
				.headers(headers)
				.body(OotdResponse.<Void>builder()
						.code(HttpStatus.OK.value())
						.msg("로그인 성공")
						.build());
	}

}
