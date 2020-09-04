package com.mashup.ootd.domain;

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
    @Test
    public void test() {
        WeatherService weatherService = new WeatherService();
        String result = weatherService.getPostWeatherInfo("40.12", "-96.66");
        System.out.println(result);
    }
}