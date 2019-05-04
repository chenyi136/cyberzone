package com.safecode.cyberzone.web.authorize.provider;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.safecode.cyberzone.web.auth.service.AuthorizeConfigProvider;

@Component
@Order(Integer.MAX_VALUE)
public class MyAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.anyRequest().access("@rbacService.hasPermission(request , authentication)");
	}

}
