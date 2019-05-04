package com.safecode.cyberzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient  //通过该注解让该应用注册为Eureka客户端应用，以获得服务发现的能力
@SpringBootApplication
public class SysLogApplication {
	//@Bean
	//@LoadBalanced
	//创建RespTemplate的Spring Bean实例,并通过LoadBalanced注解开启客户端负载均衡
	/*public RestTemplate restTemplate(){
		return new RestTemplate();
	}*/
	public static void main(String[] args) {
		SpringApplication.run(SysLogApplication.class, args);
	}
	
}
