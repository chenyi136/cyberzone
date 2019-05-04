package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.pojo.SysRole;
import com.safecode.cyberzone.pojo.SysUserRole;

public interface SysRoleService {

	PageInfo<SysRole> queryList(PageAttribute pageAttribute);

	void insert(SysRole sysRole);

	List<SysRole> queryAll();

	List<SysUserRole> queryUserRole(Long userId);

	void insertUserRole(SysUserRole userRole);

	void deleteUserRole(Long userId);

}
