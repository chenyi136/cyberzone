package com.safecode.cyberzone.web.authorize.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.web.authorize.entity.SysAcl;
import com.safecode.cyberzone.web.authorize.param.AclParam;
import com.safecode.cyberzone.web.contants.HttpContants;
import com.safecode.cyberzone.web.exception.CustomResponseErrorHandler;

@RestController
@RequestMapping("/sys/acl")
@CrossOrigin
public class SysAclController {

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/save")
	public ResponseData<SysAcl> saveAcl(@RequestBody AclParam param) {
		HttpEntity<AclParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysAcl>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/acl/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysAcl>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/update")
	public ResponseData<SysAcl> updateAcl(@RequestBody AclParam param) {
		HttpEntity<AclParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysAcl>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/acl/update", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysAcl>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/page")
	public Object page(@RequestParam("aclModuleId") Integer aclModuleId,
			@PageableDefault(page = 1, size = 10, sort = "id,asc") Pageable pageable) {

		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("aclModuleId", aclModuleId);
		paramMap.add("page", pageable.getPageNumber());
		paramMap.add("size", pageable.getPageSize());
		paramMap.add("sort", pageable.getSort());

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());

		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<Object> exchange = restTemplate.exchange("http://cyberzone-gateway/authorize/sys/acl/page",
				HttpMethod.POST, entity, new ParameterizedTypeReference<Object>() {
				});

		return exchange.getBody();
	}

	@PostMapping("/acls")
	public ResponseData<Map<String, Object>> acls(@RequestParam("aclId") int aclId) {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("aclId", aclId);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());

		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<Map<String, Object>>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/acl/acls", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Map<String, Object>>>() {
				});

		return exchange.getBody();
	}
}
