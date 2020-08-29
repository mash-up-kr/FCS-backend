package com.mashup.ootd.domain.weathergeo.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class GetWeatherInfo {


    public HashMap<String, Object> getCurrentWeatherInfo(@RequestParam("lat") double lat, @RequestParam("lon") double lon) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            String apiURL = new StringBuilder().append("api.openweathermap.org/data/2.5/weather?").append("lat=").append(lat).append("lon=").append(lon).append("appid=b692c24a9d64e29901f2fa8c899d1b9f").toString();
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(apiURL).build();

            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            //우리가 필요한 정보는 없는 것 같아서 안넣었
            /*
            result.put("statusCode", resultMap.getStatusCodeValue());
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody());
             */

            JSONObject bigObj = new JSONObject(Objects.requireNonNull(resultMap.getBody()));
            //String jString = bigObj.toJSONString();
            JSONArray weatherObj = (JSONArray) bigObj.get("weather");
            JSONObject smallObj = (JSONObject) weatherObj.get(0);
            String element = (String) smallObj.get("description");
            result.put("description", element);

            smallObj = (JSONObject)bigObj.get("main");
            element = (String) smallObj.get("temp");
            result.put("temp", Float.parseFloat(element));

            smallObj = (JSONObject)bigObj.get("clouds");
            element = (String) smallObj.get("all");
            result.put("clouds", Integer.parseInt(element));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            /*
            result.put("statusCode", e.getRawStatusCode());
            result.put("body", "exception");
             */
            System.out.println(e.toString());
        } catch (Exception e) {
            /*
            result.put("statusCode", "999");
            result.put("body", "exception");
             */
            System.out.println(e.toString());
        }

        return result;
    }



    public HashMap<String, Object> getGeoInfo(double lat, double lon) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            String apiURL = new StringBuilder().append("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?").append("request=coordsToaddr&coords=").append(lat).append(",").append(lon).append("&orders=legalcode&output=json").toString();
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(apiURL).build();

            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            JSONObject bigObj = new JSONObject(Objects.requireNonNull(resultMap.getBody()));
            JSONArray results = (JSONArray) bigObj.get("results");
            JSONObject smallObj = (JSONObject) results.get(0);
            smallObj = (JSONObject) smallObj.get("region");
            smallObj = (JSONObject) smallObj.get("area1");
            String element = (String) smallObj.get("name");
            result.put("do", element);

            smallObj = (JSONObject) smallObj.get("region");
            smallObj = (JSONObject) smallObj.get("area2");
            element = (String) smallObj.get("name");
            result.put("si,gu", element);

            smallObj = (JSONObject) smallObj.get("region");
            smallObj = (JSONObject) smallObj.get("area3");
            element = (String) smallObj.get("name");
            result.put("dong", element);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return result;
    }
}