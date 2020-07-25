package com.mashup.ootd.domain.style.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.style.domain.PostStyle;
import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.style.repository.PostStyleRepository;
import com.mashup.ootd.domain.style.repository.StyleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostStyleService {

	private final StyleRepository styleRepository;
	private final PostStyleRepository postStyleRepository;

	public void save(Post post, List<Long> styleIds) {
		List<PostStyle> postStyles = styleIds.stream().map(id -> {
			Style style = styleRepository.findById(id).orElseThrow(RuntimeException::new);
			return PostStyle.create(post, style);
		}).collect(Collectors.toList());

		postStyleRepository.saveAll(postStyles);
	}

}
