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
import com.safecode.cyberzone.web.authorize.dto.DeptLevelDto;
import com.safecode.cyberzone.web.authorize.entity.SysDept;
import com.safecode.cyberzone.web.authorize.param.DeptParam;
import com.safecode.cyberzone.web.contants.HttpContants;
import com.safecode.cyberzone.web.exception.CustomResponseErrorHandler;

@RestController
@RequestMapping("/sys/dept")
@CrossOrigin
public class SysDeptController {

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/save")
	public ResponseData<SysDept> saveDept(@RequestBody DeptParam param) {
		HttpEntity<DeptParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysDept>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/dept/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysDept>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/update")
	public ResponseData<SysDept> updateDept(@RequestBody DeptParam param) {
		HttpEntity<DeptParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysDept>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/dept/update", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysDept>>() {
				});
		return exchange.getBody();
	}

	@GetMapping("/tree")
	public ResponseData<List<DeptLevelDto>> tree() {
		HttpEntity<Object> entity = HttpContants.setEntity(null);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<List<DeptLevelDto>>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/dept/tree", HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<List<DeptLevelDto>>>() {
				});
		return exchange.getBody();

	}

	@PostMapping("/delete")
	public ResponseData<SysDept> delete(@RequestParam("id") int id) {

		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("id", id);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());

		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysDept>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/dept/delete", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysDept>>() {
				});

		return exchange.getBody();
	}

}
