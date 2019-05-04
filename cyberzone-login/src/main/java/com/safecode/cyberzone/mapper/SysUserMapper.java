package com.safecode.cyberzone.mapper;

import org.apache.ibatis.annotations.Param;

import com.safecode.cyberzone.pojo.SysUser;

public interface SysUserMapper {
    //根据用户名查询用户信息
    SysUser findSysUserByAccount(@Param("account") String account);

    boolean upDateByname(@Param("time") String date);

    //根据用户名添加人脸识别id
    void updateFaceIdForAccount(@Param("accountid") long accountid, @Param("faceid") String faceid);

    //根据用户名名查询人脸id
    String selectFaceIdForUserId(@Param("userid") long userid);
}
