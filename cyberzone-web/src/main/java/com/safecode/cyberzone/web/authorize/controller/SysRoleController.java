package com.safecode.cyberzone.web.authorize.controller;

import java.util.List;
import java.util.Map;

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
import com.safecode.cyberzone.web.auth.holder.AuthHolder;
import com.safecode.cyberzone.web.authorize.dto.AclModuleLevelDto;
import com.safecode.cyberzone.web.authorize.entity.SysAcl;
import com.safecode.cyberzone.web.authorize.entity.SysRole;
import com.safecode.cyberzone.web.authorize.entity.SysUser;
import com.safecode.cyberzone.web.authorize.param.RoleParam;
import com.safecode.cyberzone.web.contants.HttpContants;
import com.safecode.cyberzone.web.exception.CustomResponseErrorHandler;

@RestController
@RequestMapping("/sys/role")
@CrossOrigin
public class SysRoleController {

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/save")
	public ResponseData<SysRole> saveRole(@RequestBody RoleParam param) {
		HttpEntity<RoleParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysRole>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/role/save", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysRole>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/update")
	public ResponseData<SysRole> updateRole(@RequestBody RoleParam param) {
		HttpEntity<RoleParam> entity = HttpContants.setEntity(param);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysRole>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/role/update", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysRole>>() {
				});
		return exchange.getBody();

	}

	@GetMapping("/list")
	public ResponseData<List<SysRole>> list() {

		HttpEntity<Object> entity = HttpContants.setEntity(null);
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<List<SysRole>>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/role/list", HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<List<SysRole>>>() {
				});
		return exchange.getBody();
	}

	@PostMapping("/roleTree")
	public ResponseData<List<AclModuleLevelDto>> roleTree(@RequestParam("roleId") Integer roleId) {

		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("roleId", roleId);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());

		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<List<AclModuleLevelDto>>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/role/roleTree/" + AuthHolder.getCurrentUser().getUserId(),
				HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseData<List<AclModuleLevelDto>>>() {
				});

		return exchange.getBody();

	}

	@PostMapping("/changeAcls")
	public ResponseData<SysAcl> changeAcls(@RequestParam("roleId") int roleId,
			@RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("roleId", roleId);
		paramMap.add("aclIds", aclIds);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysAcl>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/role/changeAcls", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysAcl>>() {
				});

		return exchange.getBody();
	}

	@PostMapping("/changeUsers")
	public ResponseData<SysUser> changeUsers(@RequestParam("roleId") int roleId,
			@RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("roleId", roleId);
		paramMap.add("userIds", userIds);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<SysUser>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/role/changeUsers", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<SysUser>>() {
				});

		return exchange.getBody();
	}

	@PostMapping("/users")
	public ResponseData<Map<String, List<SysUser>>> users(@RequestParam("roleId") int roleId) {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("roleId", roleId);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,
				new HttpHeaders());
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		ResponseEntity<ResponseData<Map<String, List<SysUser>>>> exchange = restTemplate.exchange(
				"http://cyberzone-gateway/authorize/sys/role/users", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Map<String, List<SysUser>>>>() {
				});

		return exchange.getBody();
	}

}
