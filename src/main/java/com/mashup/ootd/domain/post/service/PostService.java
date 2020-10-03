package com.mashup.ootd.domain.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashup.ootd.domain.exception.NotFoundEntityException;
import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.dto.PostCreateResponse;
import com.mashup.ootd.domain.post.dto.PostListRequest;
import com.mashup.ootd.domain.post.dto.PostListResponse;
import com.mashup.ootd.domain.post.dto.PostResponse;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.repository.PostRepository;
import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.style.service.StyleService;
import com.mashup.ootd.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final FileUploader fileUploader;
	private final StyleService styleService;

	private static final String DIRECTORY_NAME = "post";
	
	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final PageRequest PAGE_REQUEST = PageRequest.of(0, DEFAULT_PAGE_SIZE, Sort.by(Direction.DESC, "id"));

	@Transactional
	public PostCreateResponse create(User user, PostCreateRequest dto) {

		String url = fileUploader.upload(dto.getUploadFile(), DIRECTORY_NAME);

		Post post = dto.toEntity(url);
		post.setUser(user);

		List<Style> styles = styleService.listByStyleIds(dto.getStyleIds());
		post.addStyles(styles);

		postRepository.save(post);

		return new PostCreateResponse(post.getId(), post.getPhotoUrl());
	}

	public List<PostResponse> listTop20() {
		return postRepository.findTop20ByOrderByIdDesc().stream()
				.map(PostResponse::of)
				.collect(Collectors.toList());
	}

	public PostListResponse list(PostListRequest dto) {
		Page<Post> postPage = postRepository.findAllByFilter(
				dto.getStyleIds(), 
				dto.getWeather(), 
				dto.getMinTemp(),
				dto.getMaxTemp(), 
				dto.getLastPostId(), 
				PAGE_REQUEST);
		
		List<PostResponse> posts = postPage.getContent().stream().map(PostResponse::of).collect(Collectors.toList());

		return PostListResponse.of(posts, postPage.hasNext());
	}

	public Post get(Long postId) {
		return postRepository.findById(postId).orElseThrow(NotFoundEntityException::new);
	}

}
