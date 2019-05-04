package com.safecode.cyberzone.authorize.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.safecode.cyberzone.authorize.entity.SysRole;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.authorize.exception.ParamException;
import com.safecode.cyberzone.authorize.mapper.SysRoleAclMapper;
import com.safecode.cyberzone.authorize.mapper.SysRoleMapper;
import com.safecode.cyberzone.authorize.mapper.SysRoleUserMapper;
import com.safecode.cyberzone.authorize.mapper.SysUserMapper;
import com.safecode.cyberzone.authorize.param.RoleParam;
import com.safecode.cyberzone.authorize.service.SysRoleService;
import com.safecode.cyberzone.authorize.utils.BeanValidator;

@Service("sysRoleService")
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysRoleAclMapper sysRoleAclMapper;

	@Override
	public void save(RoleParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getName(), param.getId())) {
			throw new ParamException("角色名称已经存在");
		}
		SysRole role = SysRole.builder().name(param.getName()).status(param.getStatus()).type(param.getType())
				.remark(param.getRemark()).build();
		role.setOperator("ludongbin");
		role.setOperateIp("127.0.0.1");
		role.setOperateTime(new Date());
		sysRoleMapper.insertSelective(role);
	}

	@Override
	public void update(RoleParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getName(), param.getId())) {
			throw new ParamException("角色名称已经存在");
		}
		SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "待更新的角色不存在");

		SysRole after = SysRole.builder().id(param.getId()).name(param.getName()).status(param.getStatus())
				.type(param.getType()).remark(param.getRemark()).build();
		after.setOperator("ludongbin");
		after.setOperateIp("127.0.0.1");
		after.setOperateTime(new Date());
		sysRoleMapper.updateByPrimaryKeySelective(after);
	}

	@Override
	public List<SysRole> getAll() {
		return sysRoleMapper.getAll();
	}

	@Override
	public List<SysRole> getRoleListByUserId(int userId) {
		List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
		if (CollectionUtils.isEmpty(roleIdList)) {
			return Lists.newArrayList();
		}
		return sysRoleMapper.getByIdList(roleIdList);
	}

	@Override
	public List<SysRole> getRoleListByAclId(int aclId) {
		List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
		if (CollectionUtils.isEmpty(roleIdList)) {
			return Lists.newArrayList();
		}
		return sysRoleMapper.getByIdList(roleIdList);
	}

	@Override
	public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
		if (CollectionUtils.isEmpty(roleList)) {
			return Lists.newArrayList();
		}
		List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
		List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
		if (CollectionUtils.isEmpty(userIdList)) {
			return Lists.newArrayList();
		}
		return sysUserMapper.getByIdList(userIdList);
	}

	private boolean checkExist(String roleName, Integer roleId) {
		return sysRoleMapper.countByName(roleName, roleId) > 0;
	}

}
