package com.safecode.cyberzone.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//通过此注解申明为spring的组件，但是切记需要手动配置，它和Filter 不一样，光申明拦截器是不起作用的
@Component("urlInterceptor")
public class UrlInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(UrlInterceptor.class);

	// 在控制器的方法调用之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle");
		logger.info(((HandlerMethod) handler).getBean().getClass().getName());
		logger.info(((HandlerMethod) handler).getMethod().getName());
		request.setAttribute("start", new Date().getTime());
		return true;
	}

	// 在控制器的方法处理之后调用 如果控制器里的方法出现异常，则不会被调用
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle");
		long start = (long) request.getAttribute("start");
		logger.info("time interceptor 耗时：" + (new Date().getTime() - start));
	}

	// 不管是控制器方法出异常，还是正常执行都会被调用
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
		logger.info("afterCompletion");
		long start = (long) request.getAttribute("start");
		logger.info("time interceptor 耗时：" + (new Date().getTime() - start));
		logger.info("e is : " + e);
	}

}
