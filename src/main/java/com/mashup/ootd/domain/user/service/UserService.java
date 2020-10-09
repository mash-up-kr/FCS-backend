package com.mashup.ootd.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashup.ootd.domain.exception.DuplicateException;
import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.post.service.FileUploader;
import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.style.service.StyleService;
import com.mashup.ootd.domain.user.dto.AccessTokenInfoResponse;
import com.mashup.ootd.domain.user.dto.ChangeNicknameRequest;
import com.mashup.ootd.domain.user.dto.ChangeProfileImageRequest;
import com.mashup.ootd.domain.user.dto.ChangeProfileImageResponse;
import com.mashup.ootd.domain.user.dto.ChangeStylesRequest;
import com.mashup.ootd.domain.user.dto.SignInRequest;
import com.mashup.ootd.domain.user.dto.SignUpRequest;
import com.mashup.ootd.domain.user.entity.User;
import com.mashup.ootd.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final StyleService styleService;
	private final FileUploader fileUploader;
	
	private static final String DIRECTORY_NAME = "user";

	@Transactional
	public User signUp(SignUpRequest dto) {
		if (userRepository.findByUidAndAuthType(dto.getUid(), dto.getAuthType()).isPresent())
			throw new DuplicateException();
		
		checkDuplicate(dto.getNickname());

		User user = dto.toEntity();
		
		List<Style> styles = styleService.listByStyleIds(dto.getStyleIds());
		user.setStyles(styles);
		
		userRepository.save(user);
		
		return user;
	}

	public User signIn(SignInRequest dto) {
		User user = userRepository.findByUidAndAuthType(dto.getUid(), dto.getAuthType())
				.orElseThrow(NotFoundEntityException::new);

		return user;
	}
	
	public User get(Long id) {
		return userRepository.findById(id)
				.orElseThrow(NotFoundEntityException::new);
	}
	
	public AccessTokenInfoResponse getInfo(User user) {
		return AccessTokenInfoResponse.of(user.getId());
	}
	
	public boolean checkDuplicate(String nickname) {

		if (userRepository.findByNickname(nickname).isPresent())
			throw new DuplicateException("중복된 닉네임입니다.");

		return true;
	}
	
	@Transactional
	public void changeStyles(User user, ChangeStylesRequest dto) {
		List<Style> styles = styleService.listByStyleIds(dto.getStyleIds());
		
		user.setStyles(styles);
	}
	
	@Transactional
	public void changeNickname(User user, ChangeNicknameRequest dto) {
		checkDuplicate(dto.getNickname());
		
		user.setNickname(dto.getNickname());
	}

	@Transactional
	public ChangeProfileImageResponse changeProfileImage(User user, ChangeProfileImageRequest dto) {
		String url = fileUploader.upload(dto.getProfileImage(), DIRECTORY_NAME);

		user.setProfileImageUrl(url);

		return new ChangeProfileImageResponse(url);
	}

}