package com.mashup.ootd.web.controller.style;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static com.mashup.ootd.ApiDocumentUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.style.dto.StyleResponse;
import com.mashup.ootd.domain.style.service.StyleService;
import com.mashup.ootd.web.controller.ControllerTest;

@WebMvcTest(StyleController.class)
public class StyleControllerTest extends ControllerTest {

	@MockBean
	private StyleService styleService;
	
	@Test
	public void test_list() throws Exception {

		// given
		given(jwtService.isUsable(any())).willReturn(true);
		
		Style style1 = Style.builder()
				.id(1L)
				.name("댄디")
				.build();

		Style style2 = Style.builder()
				.id(2L)
				.name("캐주얼")
				.build();

		List<Style> styles = new ArrayList<>();
		styles.add(style1);
		styles.add(style2);

		List<StyleResponse> response = styles.stream().map(StyleResponse::of).collect(Collectors.toList());

		BDDMockito.given(styleService.list()).willReturn(response);

		// when
		ResultActions result = mockMvc.perform(get("/api/styles")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk())
				.andDo(document("style-list",
						getDocumentRequest(),
						getDocumentResponse(),
						responseFields(
								fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드"),
								fieldWithPath("msg").type(JsonFieldType.STRING).description("상태 메시지"),
								fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("id"),
								fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름")
						)
				));
	}

}
