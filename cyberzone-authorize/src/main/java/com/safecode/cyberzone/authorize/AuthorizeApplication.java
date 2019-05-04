package com.safecode.cyberzone.authorize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizeApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthorizeApplication.class, args);
	}
}
