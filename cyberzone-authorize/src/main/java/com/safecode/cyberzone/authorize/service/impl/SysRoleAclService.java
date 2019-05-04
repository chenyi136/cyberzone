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
import com.safecode.cyberzone.authorize.entity.SysRoleAcl;
import com.safecode.cyberzone.authorize.mapper.SysRoleAclMapper;

@Service("sysRoleAclService")
@Transactional
public class SysRoleAclService {
	@Autowired
	private SysRoleAclMapper sysRoleAclMapper;
	
	public void changRoleAcls(Integer roleId , List<Integer> aclIdList){
		List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
		if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
	}
	 public void updateRoleAcls(int roleId, List<Integer> aclIdList) {
	        sysRoleAclMapper.deleteByRoleId(roleId);

	        if (CollectionUtils.isEmpty(aclIdList)) {
	            return;
	        }
	        List<SysRoleAcl> roleAclList = Lists.newArrayList();
	        for(Integer aclId : aclIdList) {
	            SysRoleAcl roleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).operator("ludongibn")
	                    .operateIp("127.0.0.1").operateTime(new Date()).build();
	            roleAclList.add(roleAcl);
	        }
	        sysRoleAclMapper.batchInsert(roleAclList);
	    }
}
