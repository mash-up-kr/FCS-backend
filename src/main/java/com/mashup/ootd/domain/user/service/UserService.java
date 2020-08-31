package com.mashup.ootd.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashup.ootd.domain.exception.DuplicateException;
import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.style.service.UserStyleService;
import com.mashup.ootd.domain.user.dto.SignInRequest;
import com.mashup.ootd.domain.user.dto.SignInResponse;
import com.mashup.ootd.domain.user.dto.SignUpRequest;
import com.mashup.ootd.domain.user.entity.User;
import com.mashup.ootd.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserStyleService userStyleService;

	@Transactional
	public void signUp(SignUpRequest dto) {
		userRepository.findByUidAndAuthType(dto.getUid(), dto.getAuthType())
				.ifPresent(user -> new DuplicateException());

		User user = dto.toEntity();
		userRepository.save(user);

		userStyleService.save(user, dto.getStyleIds());
	}

	public SignInResponse signIn(SignInRequest dto) {
		User user = userRepository.findByUidAndAuthType(dto.getUid(), dto.getAuthType())
				.orElseThrow(NotFoundEntityException::new);
		
		return new SignInResponse(user.getNickname(), user.getStyleIds());
	}

}