package com.safecode.cyberzone.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.web.entity.SysAclModule;
import com.safecode.cyberzone.web.entity.SysUser;
import com.safecode.cyberzone.web.utils.ObjectUtil;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/save")
	public ResponseData<SysAclModule> save(SysAclModule aclModule) throws IllegalAccessException {
		MultiValueMap<String, Object> paramMap = ObjectUtil.objectToMap(aclModule);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				headers);
		ResponseEntity<ResponseData<SysAclModule>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/authorize/test/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysAclModule>>() {
				}); 
		return exchange.getBody();
	}

	@PostMapping("/update")
	public ResponseData<SysUser> update(String username, String password) {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("username", username);
		paramMap.add("password", password);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				headers);
		ResponseEntity<ResponseData<SysUser>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/authorize/test/update", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysUser>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/delete")
	public ResponseData<Map<String, Object>> delete(SysAclModule aclModule, SysUser sysUser)
			throws IllegalAccessException {
		MultiValueMap<String, Object> aclParam = ObjectUtil.objectToMap(aclModule);
		MultiValueMap<String, Object> userParam = ObjectUtil.objectToMap(sysUser);

		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("aclParam", aclParam);
		paramMap.add("userParam", userParam);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				headers);
		ResponseEntity<ResponseData<Map<String, Object>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/authorize/test/delete", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Map<String, Object>>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/select")
	public ResponseData<Map<String, Object>> select(SysUser sysUser, int pageNum, int pageSize)
			throws IllegalAccessException {
		MultiValueMap<String, Object> paramMap = ObjectUtil.objectToMap(sysUser);
		paramMap.add("pageNum", pageNum);
		paramMap.add("pageSize", pageSize);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				headers);
		ResponseEntity<ResponseData<Map<String, Object>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/authorize/test/select", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Map<String, Object>>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/other/{currentUserId}")
	public ResponseData<Map<String, Object>> select(@PathVariable("currentUserId") int currentUserId, SysUser sysUser)
			throws IllegalAccessException {
		MultiValueMap<String, Object> paramMap = ObjectUtil.objectToMap(sysUser);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				headers);
		ResponseEntity<ResponseData<Map<String, Object>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/authorize/test/other/" + currentUserId, HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Map<String, Object>>>() {
				});
		return exchange.getBody();
	}

}
