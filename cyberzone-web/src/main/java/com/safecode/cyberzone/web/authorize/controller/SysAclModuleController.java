package com.safecode.cyberzone.web.authorize.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.web.authorize.dto.AclModuleLevelDto;
import com.safecode.cyberzone.web.authorize.entity.SysAclModule;
import com.safecode.cyberzone.web.authorize.param.AclModuleParam;
import com.safecode.cyberzone.web.contants.HttpContants;
import com.safecode.cyberzone.web.exception.CustomResponseErrorHandler;

@RestController
@RequestMapping("/sys/aclModule")
@CrossOrigin
public class SysAclModuleController {

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/save")
	public ResponseData<SysAclModule> saveAclModule(@RequestBody AclModuleParam param) {
		HttpEntity<AclModuleParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysAclModule>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/aclModule/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysAclModule>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/update")
	public ResponseData<SysAclModule> updateAclModule(@RequestBody AclModuleParam param) {
		HttpEntity<AclModuleParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysAclModule>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/aclModule/update", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysAclModule>>() {
				});
		return exchange.getBody();
	}

	@GetMapping("/tree")
	public ResponseData<List<AclModuleLevelDto>> tree() {
		HttpEntity<Object> entity = HttpContants.setEntity(null);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<List<AclModuleLevelDto>>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/aclModule/tree", HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<List<AclModuleLevelDto>>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/delete")
	public ResponseData<SysAclModule> delete(@RequestParam("id") int id) {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("id", id);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());

		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysAclModule>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/aclModule/delete", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysAclModule>>() {
				});

		return exchange.getBody();
	}
}
