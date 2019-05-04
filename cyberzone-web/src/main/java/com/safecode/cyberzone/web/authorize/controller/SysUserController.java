package com.safecode.cyberzone.web.authorize.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.web.auth.holder.AuthHolder;
import com.safecode.cyberzone.web.authorize.entity.SysAcl;
import com.safecode.cyberzone.web.authorize.entity.SysUser;
import com.safecode.cyberzone.web.authorize.param.UserParam;
import com.safecode.cyberzone.web.contants.HttpContants;
import com.safecode.cyberzone.web.exception.CustomException;
import com.safecode.cyberzone.web.exception.CustomResponseErrorHandler;

@RestController
@RequestMapping("/sys/user")
@CrossOrigin
public class SysUserController {

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/save")
	public ResponseData<Object> saveUser(@RequestBody UserParam param) {
		HttpEntity<UserParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<Object>> exchange = restTemplate.exchange("http://cyberzone-gateway/authorize/sys/user/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Object>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/update")
	public ResponseData<SysUser> updateUser(@RequestBody UserParam param) {
		HttpEntity<UserParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysUser>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/user/update", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysUser>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/page")
	public Object page(@RequestParam("deptId") Integer deptId,
			@PageableDefault(page = 1, size = 10, sort = "id,asc") Pageable pageable) {

		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("deptId", deptId);
		paramMap.add("page", pageable.getPageNumber());
		paramMap.add("size", pageable.getPageSize());
		paramMap.add("sort", pageable.getSort());
		
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<Object> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/user/page", HttpMethod.POST, entity,
				new ParameterizedTypeReference<Object>() {
				});

		return exchange.getBody();
	}

	@PostMapping("/acls")
	public ResponseData<Map<String, Object>> acls(@RequestParam("userId") int userId) {

		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("userId", userId);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());

		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<Map<String, Object>>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/user/acls", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Map<String, Object>>>() {
				});

		return exchange.getBody();
	}

	@PostMapping("/acl")
	public ResponseData<List<SysAcl>> acl() {
		HttpEntity<Object> entity = HttpContants.setEntity(null);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<List<SysAcl>>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/user/acl/" + AuthHolder.getCurrentUser().getUserId(),
				HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseData<List<SysAcl>>>() {
				});

		return exchange.getBody();
	}
}
