package com.mashup.ootd.domain.post.service;

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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class GetWeatherInfo {
    public HashMap<String, Object> getCurrentWeatherInfo(@RequestParam("lat") float lat, @RequestParam("lon") float lon) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        //String jsonInString = "";
        //StringBuilder urlBuilder = new StringBuilder();
        try {
        /*
            String apiURL = "api.openweathermap.org/data/2.5/weather?"
            + "lat=" + lat + "lon=" + lon + "appid=";
            URL url = new URL(apiURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //urlConnection.setRequestProperty("Client-Id", clientId);
            //urlConnection.setRequestProperty("Client-Secret", clientSecret);
            int responseCode = urlConnection.getResponseCode();
            BufferedReader br;
            //이제 br에 데이터 받을 것. 근데 응답이 json 으로 오는데 이걸 이렇게 할수가 있나...?
            if (responseCode == 200)
                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            else
                br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), StandardCharsets.UTF_8));

            String line;
            //real data 인 br을 읽어들여 분석.
            while ((line = br.readLine()) != null) {
                urlBuilder.append(line).append("\n");
            }

            br.close();
            urlConnection.disconnect();

            return urlBuilder;
            */
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
            //result.put("statusCode", resultMap.getStatusCodeValue());
            //result.put("header", resultMap.getHeaders());
            //result.put("body", resultMap.getBody());

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



    public HashMap<String, Object> getGeoInfo(@RequestParam("lat") float lat, @RequestParam("lon") float lon) {
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
