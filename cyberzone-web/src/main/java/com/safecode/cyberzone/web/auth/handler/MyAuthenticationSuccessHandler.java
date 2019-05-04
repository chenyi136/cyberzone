package com.safecode.cyberzone.web.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safecode.cyberzone.base.dto.ResponseData;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	// springMVC启动的时候会帮我们创建一个ObjectMapper对象
	private ObjectMapper objectMapper;

	// 登录成功后会被调用 Authentication封装了我们的认证信息
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("登录成功！！！");
		@SuppressWarnings("rawtypes")
		ResponseData data = new ResponseData();
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		
		data.setCode(HttpStatus.OK.value());
		data.setMsg("登录成功");
		response.getWriter().write(objectMapper.writeValueAsString(data));
	}

}
