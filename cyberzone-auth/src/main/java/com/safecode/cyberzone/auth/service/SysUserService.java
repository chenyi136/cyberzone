package com.safecode.cyberzone.auth.service;

import com.safecode.cyberzone.auth.entity.SysUser;

public interface SysUserService {
	SysUser findByKeyword(String keyword);
}
