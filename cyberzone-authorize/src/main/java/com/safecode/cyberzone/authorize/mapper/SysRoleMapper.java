package com.safecode.cyberzone.authorize.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.safecode.cyberzone.authorize.entity.SysRole;

public interface SysRoleMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysRole record);

	int insertSelective(SysRole record);

	SysRole selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysRole record);

	int updateByPrimaryKey(SysRole record);

	int countByName(@Param("name") String name, @Param("id") Integer id);

	List<SysRole> getAll();

	List<SysRole> getByIdList(@Param("idList") List<Integer> idList);
}