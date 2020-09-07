package com.mashup.ootd.domain.post.service;

import com.mashup.ootd.domain.post.dto.PostGetResponse;
import com.mashup.ootd.domain.weather.domain.PostWeather;
import com.mashup.ootd.domain.weather.service.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashup.ootd.domain.post.dto.PostCreateRequest;
import com.mashup.ootd.domain.post.dto.PostCreateResponse;
import com.mashup.ootd.domain.post.entity.Post;
import com.mashup.ootd.domain.post.repository.PostRepository;
import com.mashup.ootd.domain.style.service.PostStyleService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final FileUploader fileUploader;
	private final PostStyleService postStyleService;
	private final WeatherService weatherService;

	private static final String DIRECTORY_NAME = "post";

	//for test
	public PostWeather postWeather;

	@Transactional
	public PostCreateResponse create(PostCreateRequest dto) {
		String url = fileUploader.upload(dto.getUploadFile(), DIRECTORY_NAME);

		String lat = dto.getLat();
		String lon = dto.getLon();
		postWeather = weatherService.getPostWeatherInfo(lat, lon);
		//post.setWeather(postweather);
		//포스트를 생성해야 하기 때문에 이 코드가 필요함. 또한
		Post post = dto.toEntity(url);


		postRepository.save(post);
		postStyleService.save(post, dto.getStyleIds());

		return new PostCreateResponse(post.getId(), post.getPhotoUrl());
	}

	public List<PostGetResponse> listTop20(){
		return postRepository.findTop20ByOrderByIdDesc().stream()
				.map(PostGetResponse::new)
				.collect(Collectors.toList());
	}

}
