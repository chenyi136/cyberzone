package com.safecode.cyberzone.web.authorize.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safecode.cyberzone.base.dto.ResponseData;

@Component("myAccessDeniedHandler")
public class MyAccessDeniedHandler implements AccessDeniedHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
			throws IOException, ServletException {
		@SuppressWarnings("rawtypes")
		ResponseData data = new ResponseData();
		logger.warn("没有权限");

		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("application/json;charset=UTF-8");

		data.setCode(HttpStatus.FORBIDDEN.value());
		data.setMsg("您没有权限");

		response.getWriter().write(objectMapper.writeValueAsString(data));
	}

}
