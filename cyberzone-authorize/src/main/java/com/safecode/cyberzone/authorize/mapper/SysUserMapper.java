package com.safecode.cyberzone.authorize.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.authorize.entity.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    int countByMail(@Param("mail") String mail , @Param("id") Integer id);
    
    int countByTelephone(@Param("telephone") String telephone , @Param("id") Integer id);
    
    Page<SysUser> getPageByDeptId(@Param("deptId") Integer deptId);
    
    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);
    
    List<SysUser> getAll();
    
    int countByDeptId(@Param("deptId") int deptId);
}