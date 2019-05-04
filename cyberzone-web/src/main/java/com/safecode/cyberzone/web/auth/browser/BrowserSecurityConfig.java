package com.safecode.cyberzone.web.auth.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.safecode.cyberzone.web.auth.service.AuthorizeConfigManager;
import com.safecode.cyberzone.web.auth.session.ExpiredSessionStrategy;

//安全配置
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler myAuthenticationFailureHandler;
	@Autowired
	private LogoutSuccessHandler myLogoutSuccessHandler;
	
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	@Autowired
	private ExpiredSessionStrategy expiredSessionStrategy;
	
	@Autowired
	private AccessDeniedHandler myAccessDeniedHandler;
	// 密码加密
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
// 	判断返回json还是标准登录页   在controller中映射url  配置后导致重定向次数过多 是因为所有的请求都需要认证  所以需要放行此请求
			.loginPage("/authentication/require")
//			form表单提交的路径  如果不配置默认走UsernamePasswordAuthenticationFilter过滤器下的/login 路径
			.loginProcessingUrl("/signIn")
			.successHandler(myAuthenticationSuccessHandler)
			.failureHandler(myAuthenticationFailureHandler)
			.and()
			.sessionManagement()
//			当session失效的时候跳转的地址
			.invalidSessionUrl("/session/invalid")
//			session 的并发控制，只允许一个用户登录
			.maximumSessions(1)
//			当达到session并发数量时，阻止后续行为
//			.maxSessionsPreventsLogin(true)
//			针对后面登录把前面的踢掉，可以导到一个url上面做一个相应的处理
			.expiredSessionStrategy(expiredSessionStrategy)
			.and()
			.and()
			.logout()
			.logoutUrl("/signOut")
			.logoutSuccessHandler(myLogoutSuccessHandler)
			.deleteCookies("JSESSIONID")
			.and()
			// 授权请求
//			.authorizeRequests()
////			当访问此url时放行  不需要身份认证
//				.antMatchers("/authentication/require" , securityProperties.getBrowser().getLoginPage() , "/session/invalid")
//					.permitAll()
//			// 所有的请求必须通过认证才能访问
//			.anyRequest()
//			.authenticated()
//			.and()
//			把跨域请求伪造的防护 关掉
			.csrf().disable()
			.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
			
			authorizeConfigManager.config(http.authorizeRequests());
	}
}
