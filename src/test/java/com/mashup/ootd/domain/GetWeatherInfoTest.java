
package com.mashup.ootd.domain;

import com.mashup.ootd.domain.post.service.GetWeatherInfo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GetWeatherInfo.class)
@AutoConfigureRestDocs
public class GetWeatherInfoTest {
    @MockBean
    private GetWeatherInfo getWeatherInfo;

    @Test
    public void test_weather() throws Exception {
        HashMap<String, Object> result = getWeatherInfo.getCurrentWeatherInfo(55, 127);
        System.out.println(result);
    }


    @Test
    public void test_geoInfo() throws Exception {
        HashMap<String, Object> result = getWeatherInfo.getGeoInfo(127.1054065,37.3595669);
        Assertions.assertThat(result.get("do")).isEqualTo("경기도");
    }
}

