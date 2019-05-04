package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.SysRoleMenu;
import java.util.List;
import java.util.Map;

public interface SysRoleMenuMapper {
    int insert(SysRoleMenu record);

    List<SysRoleMenu> selectAll();

	List<SysRoleMenu> queryRoleMenu(Map<String, Object> params);

	void deleteRoleMenu(Long roleId);
}