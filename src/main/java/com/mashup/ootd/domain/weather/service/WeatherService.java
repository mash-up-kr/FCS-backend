package com.mashup.ootd.domain.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashup.ootd.domain.location.entity.Location;
import com.mashup.ootd.domain.weather.domain.PostWeather;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
//@AllArgsConstructor
@NoArgsConstructor
public class WeatherService {
    // 사실상 current weather 만 불러옴
    PostWeather postWeather = new PostWeather();

    public PostWeather getPostWeatherInfo(String lat, String lon) {
        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders(); //header? body?
            HttpEntity<?> entity = new HttpEntity<>(header);
            //원랜 dt= 를 호출해서 historical date 를 넣어줘야함. 지금 포스트는 무저건 현재 시각으로만 생성 가능하게 했으므로 후에 리팩토링
            String apiURL = new StringBuilder().append("https://api.openweathermap.org/data/2.5/onecall?").append("lat=").append(lat).append("&lon=").append(lon).append("&exclude=minutely,hourly,daily").append("&appid=b692c24a9d64e29901f2fa8c899d1b9f").toString();
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(apiURL).build();

            ResponseEntity<Map> postWeatherResponseEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);

            JSONObject bigObj = new JSONObject(Objects.requireNonNull(postWeatherResponseEntity.getBody()));
            LinkedHashMap smallObj = (LinkedHashMap) bigObj.get("current");
            //temp는 원래 double 타입으로 전달
            double element = (double) smallObj.get("temp");
            String convertedElement = Double.toString(element);
            postWeather.setApparentTemp(convertedElement);

            element = (double) smallObj.get("feels_like");
            convertedElement = Double.toString(element);
            postWeather.setWindChillTemp(convertedElement);

            //weather 안엔 제이슨 배열이 오므로(제이슨 여러개) 이거 처리법 연구해보삼
            ArrayList weatherObjList = (ArrayList) smallObj.get("weather");
            smallObj = (LinkedHashMap) weatherObjList.get(0);
            convertedElement = (String) smallObj.get("main");
            postWeather.setDescription(convertedElement);

            return postWeather;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }


    public String getLocationInfo(String lat, String lon) {
        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders(); //header? body?
            HttpEntity<?> entity = new HttpEntity<>(header);
            //원랜 dt= 를 호출해서 historical date 를 넣어줘야함. 지금 포스트는 무저건 현재 시각으로만 생성 가능하게 했으므로 후에 리팩토링
            String apiURL = new StringBuilder().append("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc").append("request=coordsToaddr&coords=").append(lat).append("&orders=legalcode&output=json").toString();
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(apiURL).build();

            ResponseEntity<Map> postWeatherResponseEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);

            JSONObject bigObj = new JSONObject(Objects.requireNonNull(postWeatherResponseEntity.getBody()));
            LinkedHashMap smallObj = (LinkedHashMap) bigObj.get("current");
            //temp는 원래 double 타입으로 전달
            double element = (double) smallObj.get("temp");
            String convertedElement = Double.toString(element);
            postWeather.setApparentTemp(convertedElement);

            element = (double) smallObj.get("feels_like");
            convertedElement = Double.toString(element);
            postWeather.setWindChillTemp(convertedElement);

            //weather 안엔 제이슨 배열이 오므로(제이슨 여러개) 이거 처리법 연구해보삼
            ArrayList weatherObjList = (ArrayList) smallObj.get("weather");
            smallObj = (LinkedHashMap) weatherObjList.get(0);
            convertedElement = (String) smallObj.get("main");
            postWeather.setDescription(convertedElement);

            return postWeather;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }
}