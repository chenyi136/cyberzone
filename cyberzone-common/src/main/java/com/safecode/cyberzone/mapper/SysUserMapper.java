package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.base.pojo.SysUser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);

	Page<SysUser> queryList();

	List<SysUser> queryBy(Map<String, Object> params);
	
	//后加
	SysUser finduserByname(@Param("account") String username);

	boolean upDateByname(@Param("time") String date);
	
}
