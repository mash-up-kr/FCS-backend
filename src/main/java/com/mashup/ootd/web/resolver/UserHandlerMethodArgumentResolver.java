package com.mashup.ootd.web.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.mashup.ootd.domain.jwt.service.JwtService;
import com.mashup.ootd.domain.user.entity.User;
import com.mashup.ootd.domain.user.service.UserService;

@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		Long id = jwtService.getId();

		return userService.get(id);
	}

}
