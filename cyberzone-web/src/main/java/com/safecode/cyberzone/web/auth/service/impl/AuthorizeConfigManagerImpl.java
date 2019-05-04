package com.safecode.cyberzone.web.auth.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.safecode.cyberzone.web.auth.service.AuthorizeConfigManager;
import com.safecode.cyberzone.web.auth.service.AuthorizeConfigProvider;

//把系统里的所有provider收集起来
@Component("authorizeConfigManager")
public class AuthorizeConfigManagerImpl implements AuthorizeConfigManager {
	@Autowired
	private List<AuthorizeConfigProvider> authorizeConfigProviders;
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
			authorizeConfigProvider.config(config);
		}
	}

}
