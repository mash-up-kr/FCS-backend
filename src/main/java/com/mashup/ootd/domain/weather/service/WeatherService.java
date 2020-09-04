package com.mashup.ootd.domain.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

//@Service
@AllArgsConstructor
public class WeatherService {
    //사실상 current weather 만 불러옴
    public String getPostWeatherInfo(String lat, String lon) {
        //PostWeather postWeather = new postWeather();
        HashMap<String, Object> result = new HashMap<>();
        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders(); //header? body?
            HttpEntity<?> entity = new HttpEntity<>(header);
            //원랜 dt= 를 호출해서 historical date 를 넣어줘야함. 지금 포스트는 무저건 현재 시각으로만 생성 가능하게 했으므로 후에 리팩토링
            String apiURL = new StringBuilder().append("https://api.openweathermap.org/data/2.5/onecall?").append("lat=").append(lat).append("&lon=").append(lon).append("&exclude=minute,hourly,daily").append("&appid=b692c24a9d64e29901f2fa8c899d1b9f").toString();
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(apiURL).build();

            ResponseEntity<Map> postWeatherResponseEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);

            result.put("statusCode", postWeatherResponseEntity.getStatusCodeValue()); //http status code를 확인
            result.put("header", postWeatherResponseEntity.getHeaders()); //헤더 정보 확인
            result.put("body", postWeatherResponseEntity.getBody()); //실제 데이터 정보 확인

            //데이터를 제대로 전달 받았는지 확인 string 형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(postWeatherResponseEntity.getBody());
        } catch (Exception e) {
            //result.put("statusCode", "999");
            //result.put("body", "exception");
            System.out.println(e.toString());
        }

        return null;
    }
}