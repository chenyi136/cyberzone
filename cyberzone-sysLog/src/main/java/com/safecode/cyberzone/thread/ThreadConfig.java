package com.safecode.cyberzone.thread;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.ComponentScan;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.scheduling.annotation.EnableAsync;  
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;  

/**
 * 描述:
 * @author: fx
 * @date: 2018/8/15 14:22:00
 */
@Configuration  
@ComponentScan("com.safecode.cyberzone.service.impl")
@EnableAsync
public class ThreadConfig  {  
     /**
      * 执行需要依赖线程池，这里就来配置一个线程池
      * @return
      */
     @Bean  
     public Executor getExecutor() {  
          ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();  
          executor.setCorePoolSize(10);
          executor.setMaxPoolSize(100);
          executor.setQueueCapacity(250);
          executor.initialize();  
          return executor;  
     }  
}
