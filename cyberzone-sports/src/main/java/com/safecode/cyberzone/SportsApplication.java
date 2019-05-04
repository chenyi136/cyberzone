package com.safecode.cyberzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class SportsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SportsApplication.class, args);
	}
}
