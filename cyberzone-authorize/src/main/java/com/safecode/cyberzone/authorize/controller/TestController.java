package com.safecode.cyberzone.authorize.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.safecode.cyberzone.authorize.entity.SysAclModule;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.base.dto.ResponseData;

@RestController
@RequestMapping("/test")
public class TestController {

	@PostMapping("/save")
	public ResponseData<SysAclModule> save(SysAclModule aclModule) {
		return new ResponseData<SysAclModule>(HttpStatus.OK.value(), "OK", aclModule);
	}

	@PostMapping("/update")
	public ResponseData<SysUser> update(String username, String password) {
		SysUser user = SysUser.builder().username(username).password(password).build();
		return new ResponseData<SysUser>(HttpStatus.OK.value(), "OK", user);
	}

	@PostMapping("/delete")
	public ResponseData<Map<String, Object>> delete(SysAclModule aclModule, SysUser sysUser) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("aclModule", aclModule);
		map.put("sysUser", sysUser);
		return new ResponseData<Map<String, Object>>(HttpStatus.OK.value(), "OK", map);
	}
	
	@PostMapping("/select")
	public ResponseData<Map<String, Object>> select(SysUser sysUser , int pageNum , int pageSize){
		Map<String, Object> map = Maps.newHashMap();
		map.put("sysUser", sysUser);
		map.put("pageNum", pageNum);
		map.put("pageSize",pageSize);
		return new ResponseData<Map<String, Object>>(HttpStatus.OK.value(), "OK", map);
	}
	
	@PostMapping("/other/{currentUserId}")
	public ResponseData<Map<String, Object>> select(@PathVariable("currentUserId") int currentUserId ,SysUser sysUser){
		Map<String, Object> map = Maps.newHashMap();
		map.put("currentUserId", currentUserId);
		map.put("sysUser", sysUser);
		return new ResponseData<Map<String, Object>>(HttpStatus.OK.value(), "OK", map);
	}
}
