package com.mashup.ootd.web.controller.post;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.mashup.ootd.ApiDocumentUtils.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.mashup.ootd.config.JsonConfig;
import com.mashup.ootd.domain.post.service.PostService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@AutoConfigureRestDocs
@Import(JsonConfig.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;
	
	@Test
	public void test_create() throws Exception {
		ResultActions result = mockMvc.perform(fileUpload("/api/posts")
				.file("uploadFile", "image".getBytes())
				.param("userId", "1")
				.param("message", "메세지")
				.param("address", "주소")
				.param("weather", "날씨")
				.param("temperature", "온도")
				.param("styleIds", "1,2,3"))
				.andExpect(status().isCreated());

		result.andDo(print())
				.andExpect(status().isCreated())
				.andDo(document("upload",
						getDocumentResponse(),
						requestParts(
								partWithName("uploadFile").description("업로드 할 이미지")
						), 
						requestParameters(
								parameterWithName("userId").description("유저 id 입력"),
								parameterWithName("message").description("주소 입력"),
								parameterWithName("address").description("주소 입력"),
								parameterWithName("weather").description("날씨 입력"),
								parameterWithName("temperature").description("온도 입력"),
								parameterWithName("styleIds").description("스타일 id 입력"))
						));
	}

}
