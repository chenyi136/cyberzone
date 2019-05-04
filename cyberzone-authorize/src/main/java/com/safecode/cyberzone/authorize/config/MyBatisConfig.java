package com.safecode.cyberzone.authorize.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * <p>
 * Title: MyBatisConfig
 * </p>
 * <p>
 * Description: 此类为Mybatis和Spring Boot的整合 是MyBatis配置类
 * </p>
 * 
 * @author ludongbin
 * @date 2018年6月11日
 */
@Configuration
public class MyBatisConfig {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DataSource dataSource;

	@Bean("sqlSessionFactory")
	@ConditionalOnMissingBean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		try {
			sqlSessionFactoryBean.setDataSource(dataSource);
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			org.springframework.core.io.Resource mybatisConfigXml = resolver
					.getResource("classpath:mybatis/SqlMapConfig.xml");
			sqlSessionFactoryBean.setConfigLocation(mybatisConfigXml);
			// 设置mybatis扫描的mapper.xml文件的路径（非常重要，否则找不到mapper.xml文件）
			Resource[] mapperResources = resolver
					.getResources("classpath:com/safecode/cyberzone/authorize/mapper/*.xml");
			sqlSessionFactoryBean.setMapperLocations(mapperResources);
			// 设置别名包
			sqlSessionFactoryBean.setTypeAliasesPackage("com.safecode.cyberzone.authorize.entity");
			return sqlSessionFactoryBean;
		} catch (Exception e) {
			logger.error("sqlSessionFactory", e);
			throw new RuntimeException();
		}
	}

}
