package com.mashup.ootd.domain.weather.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.mashup.ootd.domain.weather.dto.WeatherRequest;
import com.mashup.ootd.domain.weather.dto.WeatherResponse;
import com.mashup.ootd.domain.weather.model.OpenWeather;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Service
public class WeatherService {
	
	private final RestTemplate restTemplate;
	
	@Value("${openWeatherMap.app-id}")
	private String openWeatherMapAppId;

	public WeatherResponse getWeather(WeatherRequest dto) {
		
		String url = getWeatherOncallUrl(dto);

		OpenWeather openWeather = restTemplate.getForObject(url, OpenWeather.class);
		
		return WeatherResponse.of(openWeather);
	}
	
	@Getter
	@Setter
	public static class Current {
		private int temp;
		private int feels_like;
		private List<Weather> weather;
	
		public String getMain() {
			return weather.get(0).getMain();
		}
		
		public String getIcon() {
			return weather.get(0).getIcon();
		}

		@Getter
		@Setter
		public static class Weather {
			private int id;
			private String main;
			private String icon;
		}
	}

	@Getter
	@Setter
	public static class Minutely {
		private double precipitation;
	}
	
	@Getter
	@Setter
	public static class Daily {
		private Temp temp;
		
		public int getMax() {
			return temp.getMax();
		}
		
		public int getMin() {
			return temp.getMin();
		}
		
		@Getter
		@Setter
		public static class Temp {
			private int min;
			private int max;
		}
	}
	
	private String getWeatherOncallUrl(WeatherRequest dto) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.openweathermap.org")
				.path("data/2.5/onecall")
				.queryParam("lat", dto.getLat())
				.queryParam("lon", dto.getLon())
				.queryParam("exclude", "hourly")
				.queryParam("units", "metric")
				.queryParam("appid", openWeatherMapAppId)
				.build();
		
		return uriComponents.toUriString();
	}

}