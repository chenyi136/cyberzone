package com.safecode.cyberzone.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safecode.cyberzone.auth.entity.SysUser;
import com.safecode.cyberzone.auth.service.SysUserService;
import com.safecode.cyberzone.base.dto.ResponseData;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SysUserService sysUserService;

	@PostMapping("/check")
	public ResponseData<SysUser> check(@RequestParam("username") String username) {
		ResponseData<SysUser> data = new ResponseData<SysUser>();
		SysUser sysUser = sysUserService.findByKeyword(username);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(sysUser);
		return data;
	}
}
