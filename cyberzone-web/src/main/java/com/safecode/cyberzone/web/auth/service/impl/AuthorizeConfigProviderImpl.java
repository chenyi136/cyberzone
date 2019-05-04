package com.safecode.cyberzone.web.auth.service.impl;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.safecode.cyberzone.web.auth.service.AuthorizeConfigProvider;

@Component("authorizeConfigProvider")
@Order(Integer.MIN_VALUE)
public class AuthorizeConfigProviderImpl implements AuthorizeConfigProvider {
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers("/authentication/require", "/autozi-signIn.html",
				"/session/invalid" , "/index.html").permitAll();
	}

}
