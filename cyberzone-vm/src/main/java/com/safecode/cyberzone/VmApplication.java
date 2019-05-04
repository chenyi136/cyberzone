package com.safecode.cyberzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;


//@EnableDiscoveryClient  //通过该注解让该应用注册为Eureka客户端应用，以获得服务发现的能力
@SpringBootApplication
public class VmApplication extends SpringBootServletInitializer {
	//@Bean
	//@LoadBalanced
	//创建RespTemplate的Spring Bean实例,并通过LoadBalanced注解开启客户端负载均衡
	/*public RestTemplate restTemplate(){
		return new RestTemplate();
	}*/
//	public static void main(String[] args) {
//		SpringApplication.run(SportsApplication.class, args);
//	}

    public static void main(String[] args) {
        SpringApplication.run(VmApplication.class, args);
    }
 
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VmApplication.class);
    }
}
