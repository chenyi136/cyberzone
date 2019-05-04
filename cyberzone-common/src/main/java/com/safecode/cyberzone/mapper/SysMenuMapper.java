package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.SysMenu;
import java.util.List;
import java.util.Map;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    List<SysMenu> selectAll();

    int updateByPrimaryKey(SysMenu record);

	Page<SysMenu> queryList();

	List<SysMenu> queryAll();

	List<SysMenu> queryByparentId(Long id);

	List<SysMenu> queryUserMenu(Map<String, Object> params);

	List queryUserSubsystem(Long userId);
}