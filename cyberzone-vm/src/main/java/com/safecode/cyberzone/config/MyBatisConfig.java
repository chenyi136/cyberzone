package com.safecode.cyberzone.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


/**  
* <p>Title: MyBatisConfig</p>  
* <p>Description: 此类为Mybatis和Spring Boot的整合  是MyBatis配置类</p>  
* @author ludongbin  
* @date 2018年6月11日  
*/  
@Configuration 
public class MyBatisConfig {
	@Resource
	private DataSource dataSource ;
	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource mybatisConfigXml = resolver.getResource("classpath:mybatis/SqlMapConfig.xml");
        sqlSessionFactoryBean.setConfigLocation(mybatisConfigXml);
        // 设置别名包
        sqlSessionFactoryBean.setTypeAliasesPackage("com.safecode.cyberzone.pojo");

        return sqlSessionFactoryBean;
	}

}
