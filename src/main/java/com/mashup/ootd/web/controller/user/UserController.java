package com.mashup.ootd.web.controller.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashup.ootd.domain.jwt.service.JwtService;
import com.mashup.ootd.domain.user.dto.AccessTokenInfoResponse;
import com.mashup.ootd.domain.user.dto.SignInRequest;
import com.mashup.ootd.domain.user.dto.SignUpRequest;
import com.mashup.ootd.domain.user.dto.UserResponse;
import com.mashup.ootd.domain.user.entity.User;
import com.mashup.ootd.domain.user.service.UserService;
import com.mashup.ootd.web.message.OotdResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
	
	private final UserService userService;
	private final JwtService jwtService;
	
	public static final String ACCESS_TOKEN_HEADER_NAME = "Access-Token";
	
	@PostMapping("/sign-up")
	public ResponseEntity<OotdResponse<UserResponse>> signUp(@RequestBody SignUpRequest dto) {
		User user = userService.signUp(dto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(ACCESS_TOKEN_HEADER_NAME, jwtService.createUserJwt(user.getId()));
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.headers(headers)
				.body(OotdResponse.<UserResponse>builder()
						.code(HttpStatus.OK.value())
						.msg("회원가입 성공")
						.data(new UserResponse(user))
						.build());
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<OotdResponse<UserResponse>> signIn(@RequestBody SignInRequest dto) {
		User user = userService.signIn(dto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(ACCESS_TOKEN_HEADER_NAME, jwtService.createUserJwt(user.getId()));

		return ResponseEntity
				.status(HttpStatus.OK)
				.headers(headers)
				.body(OotdResponse.<UserResponse>builder()
						.code(HttpStatus.OK.value())
						.msg("로그인 성공")
						.data(new UserResponse(user))
						.build());
	}
	
	@GetMapping("/access-token-info")
	public ResponseEntity<OotdResponse<AccessTokenInfoResponse>> getInfo(User user) {
		AccessTokenInfoResponse response = userService.getInfo(user);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(OotdResponse.<AccessTokenInfoResponse>builder()
						.code(HttpStatus.OK.value())
						.msg("Access-Token 정보")
						.data(response)
						.build());
	}
	
	@GetMapping("/nickname/check/{nickname}")
	public ResponseEntity<OotdResponse<UserResponse>> checkDuplicate(@PathVariable(name = "nickname") String nickname) {
		
		userService.checkDuplicate(nickname);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(OotdResponse.<UserResponse>builder()
						.code(HttpStatus.OK.value())
						.msg("사용 가능한 닉네임")
						.build());
	}

}
