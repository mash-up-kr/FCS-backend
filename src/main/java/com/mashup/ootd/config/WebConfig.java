package com.mashup.ootd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mashup.ootd.web.interceptor.JwtInterceptor;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private JwtInterceptor jwtInterceptor;

	private static final String[] EXCLUDE_PATHS = { "/api/ping", "/api/users/sign-up", "/api/users/sign-in" };

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns(EXCLUDE_PATHS);
	}

}
