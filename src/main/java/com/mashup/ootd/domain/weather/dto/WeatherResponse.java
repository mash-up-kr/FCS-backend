package com.mashup.ootd.domain.weather.dto;

import com.mashup.ootd.domain.weather.model.OpenWeather;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WeatherResponse {

	private int windChillTemp;
	private int temp;
	private int maxTemp;
	private int minTemp;
	private String weather;
	private String iconUrl;
	private String precipitation;

	public static WeatherResponse of(OpenWeather openWeather) {
		return WeatherResponse.builder()
				.windChillTemp(openWeather.getWindChillTemp())
				.temp(openWeather.getTemp())
				.maxTemp(openWeather.getMaxTemp())
				.minTemp(openWeather.getMinTemp())
				.weather(openWeather.getWeather())
				.iconUrl(openWeather.getIconUrl())
				.precipitation(String.format("%.1f", openWeather.getPrecipitation()))
				.build();
	}
}
