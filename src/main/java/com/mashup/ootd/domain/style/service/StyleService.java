package com.mashup.ootd.domain.style.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.style.dto.StyleResponse;
import com.mashup.ootd.domain.style.repository.StyleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StyleService {

	private final StyleRepository styleRepository;

	public List<StyleResponse> list() {
		List<Style> styles = styleRepository.findAll();

		return styles.stream().map(StyleResponse::of).collect(Collectors.toList());
	}

	public List<Style> listByStyleIds(List<Long> styleIds) {
		return styleRepository.findAllById(styleIds);
	}

}
