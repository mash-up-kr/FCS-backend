package com.mashup.ootd.domain.user.service;

import org.springframework.stereotype.Service;

import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.user.dto.SignInRequest;
import com.mashup.ootd.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public void signIn(SignInRequest dto) {
		userRepository.findByUidAndAuthType(dto.getUid(), dto.getAuthType())
				.orElseThrow(NotFoundEntityException::new);
	}

}