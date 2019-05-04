package com.safecode.cyberzone.web.authorize.dto;

import org.springframework.beans.BeanUtils;

import com.safecode.cyberzone.web.authorize.entity.SysAcl;

public class AclDto extends SysAcl{
	
	// 是否要默认选中
	private boolean checked = false;
			
	// 是否有权限操作    他能操作的权限不能超出已分配权限的上限
	private boolean hasAcl = false;
	
	public static AclDto adapt(SysAcl acl){
		AclDto dto = new AclDto();
		BeanUtils.copyProperties(acl, dto);
		return dto;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isHasAcl() {
		return hasAcl;
	}

	public void setHasAcl(boolean hasAcl) {
		this.hasAcl = hasAcl;
	}
	
	

}
