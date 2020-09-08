package com.mashup.ootd.domain;

import com.mashup.ootd.domain.weather.domain.PostWeather;
import com.mashup.ootd.domain.weather.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.junit4.SpringRunner;
/*
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
 */

@RunWith(SpringRunner.class)//이것도 종류 검색좀...
@RestClientTest(WeatherService.class)
public class WeatherServiceTest {
    /*
    @Test
    public void getPostWeatherInfoTest() {
        WeatherService weatherService = new WeatherService();
        PostWeather result = weatherService.getPostWeatherInfo("40.12", "-96.66");
        System.out.println(result);
    }
     */

    @Test
    public void getGeoInfoTest() {
        WeatherService weatherService = new WeatherService();
        String location = weatherService.getLocationInfo("127.1054065","37.3595669");
        System.out.println(location);
    }
}