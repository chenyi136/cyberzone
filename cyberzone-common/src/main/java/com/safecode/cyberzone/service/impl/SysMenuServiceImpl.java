package com.safecode.cyberzone.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.mapper.SysMenuMapper;
import com.safecode.cyberzone.mapper.SysRoleMenuMapper;
import com.safecode.cyberzone.pojo.SysMenu;
import com.safecode.cyberzone.pojo.SysRoleMenu;
import com.safecode.cyberzone.service.SysMenuService;


@Service
@Transactional
public class SysMenuServiceImpl implements SysMenuService {


    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
	@Autowired
	private SessionProvider sessionProvider;
	
	
    @Override
    public PageInfo<SysMenu> queryList(PageAttribute pageAttribute) {
    	// TODO Auto-generated method stub
		if (DataUtil.isEmpty(pageAttribute.getPageNum())) {
			pageAttribute.setPageNum(1);
        }
        if (DataUtil.isEmpty(pageAttribute.getPageSize())) {
        	pageAttribute.setPageSize(10);
        }
        
		PageHelper.startPage(pageAttribute);
		Page<SysMenu> page = sysMenuMapper.queryList();
		
		return new PageInfo<SysMenu>(page);
	}
    
    @Override
    public SysMenu selectByPrimaryKey(long id) {
    	// TODO Auto-generated method stub
    	return sysMenuMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public void delete(SysMenu sysMenu) {
    	// TODO Auto-generated method stub
    	sysMenuMapper.updateByPrimaryKey(sysMenu);
    }
    
    @Override
    public void insert(SysMenu sysMenu) {
    	// TODO Auto-generated method stub
    	sysMenuMapper.insert(sysMenu);
    }
    
    @Override
    public List<SysMenu> queryAll() {
    	// TODO Auto-generated method stub
    	return sysMenuMapper.queryAll();
    }
    
    @Override
    public List<SysRoleMenu> queryRoleMenu(Map<String, Object> params) {
    	// TODO Auto-generated method stub
    	return sysRoleMenuMapper.queryRoleMenu(params);
    }
    
    @Override
    public void insertRoleMenu(SysRoleMenu roleMenu) {
    	// TODO Auto-generated method stub
    	sysRoleMenuMapper.insert(roleMenu);
    }
    
    @Override
    public void deleteRoleMenu(Long roleId) {
    	// TODO Auto-generated method stub
    	sysRoleMenuMapper.deleteRoleMenu(roleId);
    }
    
    @Override
    public List<SysMenu> queryByparentId(Long id) {
    	// TODO Auto-generated method stub
    	
		return sysMenuMapper.queryByparentId(id);
    }
    
    @Override
    public List<SysMenu> queryUserMenu(Map<String, Object> params) {
    	// TODO Auto-generated method stub
    	return sysMenuMapper.queryUserMenu(params);
    }
    
    @Override
    public List queryUserSubsystem(Long userId) {
    	// TODO Auto-generated method stub
    	return sysMenuMapper.queryUserSubsystem(userId);
    }
    
    @Override
    public void update(Long currentUserId, SysMenu sysMenu) {
    	// TODO Auto-generated method stub
    	SysMenu menu= sysMenuMapper.selectByPrimaryKey(sysMenu.getId());
		Request2ModelUtil.covertObj(menu, sysMenu);
		
		menu.setUpdateTime(new Date());
		menu.setUpdateBy(currentUserId);
		sysMenuMapper.updateByPrimaryKey(menu);
    }
    
}
