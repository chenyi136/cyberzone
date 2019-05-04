package com.safecode.cyberzone.config;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jolbox.bonecp.BoneCPDataSource;


/**
* <p>Title: SpringConfig</p>  
* <p>Description: 此类为Spring的配置类  </p>  
* @author ludongbin  
* @date 2018年6月11日  
*/  
@Configuration 
@ComponentScan(basePackages = "com.safecode.cyberzone")
@PropertySource(value = {"classpath:swagger.properties","classpath:jdbc.properties","classpath:redis.properties","classpath:detection.properties"} , ignoreResourceNotFound = true)
public class SpringConfig{
	
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.driverClassName}")
	private String jdbcDriverClassName;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	
	@Bean(name = "datasource" , destroyMethod = "close")
	@ConditionalOnMissingBean
	public DataSource dataSource() {
	    BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
		// 数据库驱动
		boneCPDataSource.setDriverClass(jdbcDriverClassName);
		// 相应驱动的jdbcUrl
		boneCPDataSource.setJdbcUrl(jdbcUrl);
		// 数据库的用户名
		boneCPDataSource.setUsername(jdbcUsername);
		// 数据库的密码
		boneCPDataSource.setPassword(jdbcPassword);
		// 检查数据库连接池中空闲连接的间隔时间，单位是分，默认值：240，如果要取消则设置为0
		boneCPDataSource.setIdleConnectionTestPeriodInMinutes(60);
		// 连接池中未使用的链接最大存活时间，单位是分，默认值：60，如果要永远存活设置为0
		boneCPDataSource.setIdleMaxAgeInMinutes(30);
		// 每个分区最大的连接数
		boneCPDataSource.setMaxConnectionsPerPartition(100);
		// 每个分区最小的连接数
		boneCPDataSource.setMinConnectionsPerPartition(5);
		return boneCPDataSource;
	}

	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("10240000KB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("10240000KB");
		return factory.createMultipartConfig();
	}
}
