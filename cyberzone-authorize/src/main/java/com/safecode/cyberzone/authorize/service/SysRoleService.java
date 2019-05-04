package com.safecode.cyberzone.authorize.service;

import java.util.List;

import com.safecode.cyberzone.authorize.entity.SysRole;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.authorize.param.RoleParam;

public interface SysRoleService {

	void save(RoleParam param);

	void update(RoleParam param);

	List<SysRole> getAll();

	List<SysRole> getRoleListByUserId(int userId);

	List<SysRole> getRoleListByAclId(int aclId);

	List<SysUser> getUserListByRoleList(List<SysRole> roleList);
}
