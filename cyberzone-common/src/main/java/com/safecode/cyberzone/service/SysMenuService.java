package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.pojo.SysMenu;
import com.safecode.cyberzone.pojo.SysRoleMenu;

public interface SysMenuService {

	PageInfo<SysMenu> queryList(PageAttribute pageAttribute);

	SysMenu selectByPrimaryKey(long id);

	void delete(SysMenu sysMenu);

	void insert(SysMenu sysMenu);

	List<SysMenu> queryAll();

	List<SysRoleMenu> queryRoleMenu(Map<String, Object> params);

	void deleteRoleMenu(Long roleId);

	void insertRoleMenu(SysRoleMenu roleMenu);

	List<SysMenu> queryByparentId(Long id);

	List<SysMenu> queryUserMenu(Map<String, Object> params);

	List queryUserSubsystem(Long userId);

	void update(Long currentUserId, SysMenu sysMenu);

}
