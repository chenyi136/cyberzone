package com.safecode.cyberzone.auth.mapper;

import org.apache.ibatis.annotations.Param;

import com.safecode.cyberzone.auth.entity.SysUser;

public interface SysUserMapper {
	 SysUser findByKeyword(@Param("keyword") String keyword);
}
