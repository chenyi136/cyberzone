package com.safecode.cyberzone.web.authorize.dto;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;

import com.safecode.cyberzone.web.authorize.entity.SysAclModule;

public class AclModuleLevelDto extends SysAclModule {
	
	private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();
	
	private List<AclDto> aclList = Lists.newArrayList();
	
	public static AclModuleLevelDto adapt(SysAclModule aclModule){
		AclModuleLevelDto dto = new AclModuleLevelDto();
		BeanUtils.copyProperties(aclModule, dto);
		return dto; 
	}

	public List<AclModuleLevelDto> getAclModuleList() {
		return aclModuleList;
	}

	public void setAclModuleList(List<AclModuleLevelDto> aclModuleList) {
		this.aclModuleList = aclModuleList;
	}

	public List<AclDto> getAclList() {
		return aclList;
	}

	public void setAclList(List<AclDto> aclList) {
		this.aclList = aclList;
	}
	
	

}
