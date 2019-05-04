package com.safecode.cyberzone.web.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safecode.cyberzone.base.dto.ResponseData;


@RestController
public class BrowserSecurityController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 当需要身份认证时，跳转到这里
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseData requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResponseData data = new ResponseData();
		
		data.setCode(HttpStatus.UNAUTHORIZED.value());
		data.setMsg("访问的服务需要身份认证，请引导用户到登录页");
		return data;
	}
//	session 失效
	@SuppressWarnings("rawtypes")
	@GetMapping("/session/invalid")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseData sessionInvalid(){
		logger.warn("session失效");
		ResponseData data = new ResponseData();
		data.setCode(HttpStatus.UNAUTHORIZED.value());
		data.setMsg("登录超时，session失效");
		return data;
	}
}
