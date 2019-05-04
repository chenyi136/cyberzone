package com.safecode.cyberzone.authorize.service;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.authorize.param.UserParam;

public interface SysUserService {
	
	public void save(UserParam param); 
	
	public void update(UserParam param);
	
	/**
	 * 
	 * <p>Title: getPageByDeptId</p>  
	 * <p>Description: 获取该部门用户列表</p>  
	 * @param deptId
	 * @param pageable
	 * @return
	 */
	public PageInfo<SysUser> getPageByDeptId(Integer deptId , Pageable pageable);
	
	/**
	 * 
	 * <p>Title: getAll</p>  
	 * <p>Description: 用户列表</p>  
	 * @return
	 */
	public List<SysUser> getAll();
	
}
