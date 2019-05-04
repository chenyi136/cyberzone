package com.safecode.cyberzone.web.authorize.rbac.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.web.auth.entity.User;
import com.safecode.cyberzone.web.authorize.entity.SysAcl;
import com.safecode.cyberzone.web.authorize.rbac.RbacService;

@Component("rbacService")
public class RbacServiceImpl implements RbacService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public boolean hasPermission(HttpServletRequest request,
			org.springframework.security.core.Authentication authentication) {
		// 注意权限点status是否有效

		boolean hasPermission = false;

		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			Integer userId = ((User) principal).getUserId();
			logger.info("用户Id :" + userId);
			MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
			paramMap.add("userId", userId);
			HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
					new HttpHeaders());
			// 读数据库 读取用户所拥有权限的所有url
			ResponseEntity<ResponseData<List<SysAcl>>> exchange = restTemplate.exchange(
					"http://cyberzone-gateway/authorize/sys/user/acl/" + userId, HttpMethod.POST, entity,
					new ParameterizedTypeReference<ResponseData<List<SysAcl>>>() {
					});
			List<SysAcl> acls = exchange.getBody().getData();
			for (SysAcl acl : acls) {
				if (acl.getUrl() != null && acl.getStatus() == 1) {
					if (antPathMatcher.match(acl.getUrl(), request.getRequestURI())) {
						hasPermission = true;
						break;
					}
				}
			}
		}
		return hasPermission;
	}

}
