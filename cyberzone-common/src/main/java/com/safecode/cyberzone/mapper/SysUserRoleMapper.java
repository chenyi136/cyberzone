package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.SysUserRole;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper {
    int insert(SysUserRole record);

    List<SysUserRole> selectAll();

	List<SysUserRole> queryUserRole(@Param("userId") Long userId);

	void deleteUserRole(Long userId);
}