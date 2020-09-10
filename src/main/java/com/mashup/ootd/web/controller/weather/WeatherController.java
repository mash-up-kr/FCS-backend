package com.mashup.ootd.web.controller.weather;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashup.ootd.domain.weather.dto.WeatherRequest;
import com.mashup.ootd.domain.weather.dto.WeatherResponse;
import com.mashup.ootd.domain.weather.service.WeatherService;
import com.mashup.ootd.web.message.OotdResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/weather")
@RestController
public class WeatherController {
	
	private final WeatherService weatherService;
	
	@GetMapping
	public ResponseEntity<OotdResponse<WeatherResponse>> getWeather(WeatherRequest dto) {
		WeatherResponse response = weatherService.getWeather(dto);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(OotdResponse.<WeatherResponse>builder()
						.code(HttpStatus.OK.value())
						.msg("날씨 정보")
						.data(response)
						.build());
	}

}
