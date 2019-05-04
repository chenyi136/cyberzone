package com.safecode.cyberzone.authorize.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safecode.cyberzone.authorize.entity.SysAcl;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.authorize.mapper.SysAclMapper;
import com.safecode.cyberzone.authorize.mapper.SysRoleAclMapper;
import com.safecode.cyberzone.authorize.mapper.SysRoleUserMapper;

@Service("sysCoreService")
public class SysCoreService {

	@Autowired
	private SysAclMapper sysAclMapper;

	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;

	@Autowired
	private SysRoleAclMapper sysRoleAclMapper;

	// 当前角色分配的权限点
	public List<SysAcl> getRoleAclList(int roleId) {
		List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer> newArrayList(roleId));
		if (CollectionUtils.isEmpty(aclIdList)) {
			return Lists.newArrayList();
		}
		// 根据权限点获取权限
		return sysAclMapper.getByIdList(aclIdList);
	}

	// 当前用户已分配的权限点
	public List<SysAcl> getCurrentUserAclList(int currentUserId) {
		return getUserAclList(currentUserId);
	}

	public List<SysAcl> getUserAclList(int userId) {
		if (isSuperAdmin()) {
			return sysAclMapper.getAll();
		}
		// 根据用户Id获取角色ID
		List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
		if (CollectionUtils.isEmpty(userRoleIdList)) {
			return Lists.newArrayList();
		}
		// 根据角色ID获取权限点ID
		List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
		if (CollectionUtils.isEmpty(userAclIdList)) {
			return Lists.newArrayList();
		}
		// 根据权限点获取权限点List
		return sysAclMapper.getByIdList(userAclIdList);

	}

	public boolean isSuperAdmin() {
		// 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
		// 可以是配置文件获取，可以指定某个用户，也可以指定某个角色

		// 获取当前用户
		// SysUser sysUser = null;
		// if(sysUser.getMail().contains("admin")){
		// return true;
		// }
		return false;
	}
}
