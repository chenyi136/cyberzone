package com.safecode.cyberzone.authorize.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.safecode.cyberzone.authorize.entity.SysRoleUser;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.authorize.mapper.SysRoleUserMapper;
import com.safecode.cyberzone.authorize.mapper.SysUserMapper;

@Service("sysRoleUserService")
@Transactional
public class SysRoleUserService {

	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	public List<SysUser> getListByRoleId(int roleId) {
		List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
		if (CollectionUtils.isEmpty(userIdList)) {
			return Lists.newArrayList();
		}
		return sysUserMapper.getByIdList(userIdList);
	}

	public void changeRoleUsers(int roleId, List<Integer> userIdList) {
		List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
		if (originUserIdList.size() == userIdList.size()) {
			Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
			Set<Integer> userIdSet = Sets.newHashSet(userIdList);
			originUserIdSet.removeAll(userIdSet);
			if (CollectionUtils.isEmpty(originUserIdSet)) {
				return;
			}
		}
		updateRoleUsers(roleId, userIdList);
	}

	@Transactional
	private void updateRoleUsers(int roleId, List<Integer> userIdList) {
		sysRoleUserMapper.deleteByRoleId(roleId);

		if (CollectionUtils.isEmpty(userIdList)) {
			return;
		}
		List<SysRoleUser> roleUserList = Lists.newArrayList();
		for (Integer userId : userIdList) {
			SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId).operator("ludongbin")
					.operateIp("127.0.0.1").operateTime(new Date()).build();
			roleUserList.add(roleUser);
		}
		sysRoleUserMapper.batchInsert(roleUserList);
	}

}
