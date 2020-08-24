package com.mashup.ootd.domain.style.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.style.domain.UserStyle;
import com.mashup.ootd.domain.style.repository.StyleRepository;
import com.mashup.ootd.domain.style.repository.UserStyleRepository;
import com.mashup.ootd.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserStyleService {

	private final StyleRepository styleRepository;
	private final UserStyleRepository userStyleRepository;
	
	public void save(User user, List<Long> styleIds) {
		List<UserStyle> userStyles = styleIds.stream().map(id -> {
			Style style = styleRepository.findById(id).orElseThrow(NotFoundEntityException::new);
			return UserStyle.of(user, style);
		}).collect(Collectors.toList());

		userStyleRepository.saveAll(userStyles);
	}

}
