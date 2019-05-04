package com.safecode.cyberzone.service;

import com.safecode.cyberzone.pojo.SysUser;

public interface SysUserService {
     public SysUser findSysUserByAccount(String account, String password);
    //根据用户添加人脸id
    void updateFaceIdForAccount(long accountid, String faceid);

    //根据帐户名查找faceid
    String selectFaceIdForUserId(long userid);

}
