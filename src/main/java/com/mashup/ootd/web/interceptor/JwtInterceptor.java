package com.mashup.ootd.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mashup.ootd.domain.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (!jwtService.isUsable(accessToken))
			return false;

		return true;
	}

}
