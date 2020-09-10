package com.mashup.ootd.web.controller.weather;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static com.mashup.ootd.ApiDocumentUtils.getDocumentRequest;
import static com.mashup.ootd.ApiDocumentUtils.getDocumentResponse;
import static java.util.stream.Collectors.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.mashup.ootd.config.JsonConfig;
import com.mashup.ootd.domain.jwt.service.JwtService;
import com.mashup.ootd.domain.user.service.UserService;
import com.mashup.ootd.domain.weather.dto.WeatherResponse;
import com.mashup.ootd.domain.weather.service.WeatherService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)
@AutoConfigureRestDocs
@Import(JsonConfig.class)
public class WeatherControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WeatherService weatherService;
	
	@MockBean
	private JwtService jwtService;
	
	@MockBean
	private UserService userService;
	
	@Test
	void test_getWeather() throws Exception {
		
		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		WeatherResponse response = WeatherResponse.builder()
				.windChillTemp(31)
				.temp(24)
				.maxTemp(32)
				.minTemp(18)
				.weather("CLEAR")
				.iconUrl("http://openweathermap.org/img/wn/01d@4x.png")
				.precipitation("0")
				.build();
		
		given(weatherService.getWeather(any())).willReturn(response);
		
		// when
		ResultActions result = mockMvc.perform(get("/api/weather?lat=37.567181&lon=127.035647")
				.header(HttpHeaders.AUTHORIZATION,
						"eyJ9eDBiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0In0.6xuHoA28UlvljPs6lqrAFpwoPFVaVsF-wa_ABCZTY5Y")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andDo(print())
				.andExpect(status().isOk())
				.andDo(document("weather",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("JWT 토큰")
						),
						requestParameters(
								parameterWithName("lat").description("위도"),
								parameterWithName("lon").description("경도")
						),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메세지"),
								fieldWithPath("data.windChillTemp").type(JsonFieldType.NUMBER).description("체감 온도"),
								fieldWithPath("data.temp").type(JsonFieldType.NUMBER).description("현재 온도"),
								fieldWithPath("data.maxTemp").type(JsonFieldType.NUMBER).description("최고 온도"),
								fieldWithPath("data.minTemp").type(JsonFieldType.NUMBER).description("최저 온도"),
								fieldWithPath("data.weather").type(JsonFieldType.STRING).description("날씨"),
								fieldWithPath("data.iconUrl").type(JsonFieldType.STRING).description("날씨 이미지 url"),
								fieldWithPath("data.precipitation").type(JsonFieldType.STRING).description("강수량 (mm)")
						)
				));
		
	}

}
