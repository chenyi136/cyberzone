package com.safecode.cyberzone.web.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.web.auth.entity.SysUser;
import com.safecode.cyberzone.web.auth.entity.User;

//处理用户信息获取逻辑
@Component
public class MyUserDetailsService implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RestTemplate restTemplate;

	// 处理用户校验逻辑
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("登录用户名：" + username);
		// 注：密码是否匹配是由security自己来做的 同样的密码加密之后出来的结果每次都是不一样的
		// 根据用户名 查找用户信息
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("username", username);
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());
		ResponseEntity<ResponseData<SysUser>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/auth/user/check", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysUser>>() {
				});
		SysUser sysUser = exchange.getBody().getData();
		if (sysUser != null) {
			// username和password用来做用户认证
			// authorities用来做授权的，根据BrowserSecurityConfig.class的configure方法的.authorizeRequests()做授权操作
			return new User(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(),
					AuthorityUtils.commaSeparatedStringToAuthorityList("xxx"));
		} else {
			throw new UsernameNotFoundException("用户[" + username + "]不存在");
		}
	}

}
