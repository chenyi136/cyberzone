package com.safecode.cyberzone.authorize.dto;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;

import com.safecode.cyberzone.authorize.entity.SysAclModule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AclModuleLevelDto extends SysAclModule {
	
	private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();
	
	private List<AclDto> aclList = Lists.newArrayList();
	
	public static AclModuleLevelDto adapt(SysAclModule aclModule){
		AclModuleLevelDto dto = new AclModuleLevelDto();
		BeanUtils.copyProperties(aclModule, dto);
		return dto; 
	}

}
