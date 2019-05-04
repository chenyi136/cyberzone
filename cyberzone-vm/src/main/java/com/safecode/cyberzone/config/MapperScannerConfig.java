package com.safecode.cyberzone.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**  
* <p>Title: MapperScannerConfig</p>  
* <p>Description: 此类为Mapper接口的扫描类</p>  
* @author ludongbin  
* @date 2018年6月11日  
*/  
@Configuration 
@AutoConfigureAfter(MyBatisConfig.class) 
public class MapperScannerConfig {
	@Bean 
	@ConditionalOnMissingBean
	public MapperScannerConfigurer mapperScannerConfigurer() {
	    MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.safecode.cyberzone.mapper");
		return mapperScannerConfigurer;
    }

}
