package com.safecode.cyberzone.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.mapper.SysRoleMapper;
import com.safecode.cyberzone.mapper.SysUserRoleMapper;
import com.safecode.cyberzone.pojo.SysRole;
import com.safecode.cyberzone.pojo.SysUserRole;
import com.safecode.cyberzone.service.SysRoleService;


@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    
	@Override
	public PageInfo<SysRole> queryList(PageAttribute pageAttribute) {
		// TODO Auto-generated method stub
		
		if (DataUtil.isEmpty(pageAttribute.getPageNum())) {
			pageAttribute.setPageNum(1);
        }
        if (DataUtil.isEmpty(pageAttribute.getPageSize())) {
        	pageAttribute.setPageSize(10);
        }
        
		PageHelper.startPage(pageAttribute);
		Page<SysRole> page = sysRoleMapper.queryList();
		
		return new PageInfo<SysRole>(page);
	}

	@Override
	public void insert(SysRole sysRole) {
		// TODO Auto-generated method stub
		sysRoleMapper.insert(sysRole);
	}
	
	@Override
	public List<SysRole> queryAll() {
		// TODO Auto-generated method stub
		return sysRoleMapper.queryAll();
	}

	@Override
	public List<SysUserRole> queryUserRole(Long userId) {
		// TODO Auto-generated method stub
		return sysUserRoleMapper.queryUserRole(userId);
	}
	
	@Override
	public void insertUserRole(SysUserRole userRole) {
		// TODO Auto-generated method stub
		sysUserRoleMapper.insert(userRole);
	}
	
	@Override
	public void deleteUserRole(Long userId) {
		// TODO Auto-generated method stub
		sysUserRoleMapper.deleteUserRole(userId);
	}
}
