package com.mashup.ootd.domain.weather.model;

import java.util.List;

import com.mashup.ootd.domain.weather.service.WeatherService.Current;
import com.mashup.ootd.domain.weather.service.WeatherService.Daily;
import com.mashup.ootd.domain.weather.service.WeatherService.Minutely;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class OpenWeather {

	private Current current;
	private List<Minutely> minutely;
	private List<Daily> daily;
	
	private static final String ICON_URL_FORMAT = "http://openweathermap.org/img/wn/%s@2x.png";
	
	public int getWindChillTemp() {
		return current.getFeels_like();
	}

	public int getTemp() {
		return current.getTemp();
	}

	public int getMaxTemp() {
		return daily.get(0).getMax();
	}

	public int getMinTemp() {
		return daily.get(0).getMin();
	}

	public String getWeather() {
		return Weather.valueOf(current.getMain().toUpperCase()).getGroupCode();
	}

	public String getIconUrl() {
//		return current.getIcon();
		return String.format(ICON_URL_FORMAT, current.getIcon());
	}
	
	public double getPrecipitation() {
		return minutely.get(0).getPrecipitation();
	}

}
