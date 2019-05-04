package com.safecode.cyberzone.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

import com.safecode.cyberzone.base.dto.ResponseData;


//全局捕获异常  使用AOP技术，采用异常通知
//1，捕获返回json格式
//2，捕获返回页面
//@ControllerAdvice是Controller 的一个辅助类，最常用的就是作为全局异常处理的切面
//@ControllerAdvice可以指定扫描范围
//@ControllerAdvice约定了几种可行的返回值，如果直接返回model类的话，需要使用@ResponseBody响应json

@ControllerAdvice(basePackages = "com.safecode.cyberzone.auth")
public class GlobalExceptionHandler {

	private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// 表示拦截异常
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseData<RuntimeException> handlerException(RuntimeException e, HandlerMethod handlerMethod) {
		ResponseData<RuntimeException> response = new ResponseData<RuntimeException>();
		log.error("***全局异常处理***");
		log.error("errorCode：" + HttpStatus.INTERNAL_SERVER_ERROR);
		log.error("errorClass：" + handlerMethod.getBean().getClass());
		log.error("errorMethod：" + handlerMethod.getMethod().getName());
		log.error("errorMsg：", e);

		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMsg("服务异常");
		response.setData(e);
		return response;
	}

}
