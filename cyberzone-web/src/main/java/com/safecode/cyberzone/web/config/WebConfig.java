package com.safecode.cyberzone.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.safecode.cyberzone.web.interceptor.UrlInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	// 因为TimeInterceptor已在spring容器中
	private UrlInterceptor urlInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(urlInterceptor);
	}

}
