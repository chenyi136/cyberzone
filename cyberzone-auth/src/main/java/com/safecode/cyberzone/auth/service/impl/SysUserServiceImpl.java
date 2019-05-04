package com.safecode.cyberzone.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safecode.cyberzone.auth.entity.SysUser;
import com.safecode.cyberzone.auth.mapper.SysUserMapper;
import com.safecode.cyberzone.auth.service.SysUserService;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser findByKeyword(String keyword) {
		SysUser sysUser = sysUserMapper.findByKeyword(keyword);
		if(sysUser != null && sysUser.getStatus() ==1){
			return sysUser;
		}
		return null;
	}

}
