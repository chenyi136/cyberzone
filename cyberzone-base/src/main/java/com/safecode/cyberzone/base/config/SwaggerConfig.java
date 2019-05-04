package com.safecode.cyberzone.base.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by xuq on 2018/7/2.
 */
@Configuration
@EnableSwagger2
@PropertySource(value = {"classpath:swagger.properties"} , ignoreResourceNotFound = true)
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private Boolean swaggerShow;

    @Bean
    public Docket createRestApi(){
        return  new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .enable(swaggerShow)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.safecode.cyberzone.controller"))
                    .paths(PathSelectors.any())
                    .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful API")
                .description("rest api 文档构建利器")
//                .termsOfServiceUrl("http://blog.csdn.net/itguangit")
                .contact("safecode")
                .version("1.0")
                .build();
    }
}
