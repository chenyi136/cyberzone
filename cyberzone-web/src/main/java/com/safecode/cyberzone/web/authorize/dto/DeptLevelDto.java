package com.safecode.cyberzone.web.authorize.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.safecode.cyberzone.web.authorize.entity.SysDept;

//Tree返回结构 用来做适配
public class DeptLevelDto extends SysDept {
	// 形成树形结构
	private List<DeptLevelDto> deptList = Lists.newArrayList();

	// 适配器
	public static DeptLevelDto adapt(SysDept dept) {
		DeptLevelDto dto = new DeptLevelDto();
		BeanUtils.copyProperties(dept, dto);
		return dto;
	}

	public List<DeptLevelDto> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DeptLevelDto> deptList) {
		this.deptList = deptList;
	}
	
	
	

}
