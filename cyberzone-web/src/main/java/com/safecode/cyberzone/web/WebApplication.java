package com.safecode.cyberzone.web;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class WebApplication {
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
//		RestTemplate restTemplate = new RestTemplate();
//		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//		messageConverters.remove(6);
//		messageConverters.add(6,new FormHttpMessageConverter());
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
