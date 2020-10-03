package com.mashup.ootd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashup.ootd.config.JsonConfig;
import com.mashup.ootd.domain.jwt.service.JwtService;
import com.mashup.ootd.domain.user.service.UserService;

@AutoConfigureRestDocs
@Import(JsonConfig.class)
public class ControllerTest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@MockBean
	protected JwtService jwtService;
	
	@MockBean
	protected UserService userService;

}
