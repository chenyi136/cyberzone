package com.safecode.cyberzone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safecode.cyberzone.mapper.SysUserMapper;
import com.safecode.cyberzone.pojo.SysUser;
import com.safecode.cyberzone.service.SessionProvider;
import com.safecode.cyberzone.service.SysUserService;

@Service("sysUserService")
@Transactional
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SessionProvider sessionProvider;

    @Override
    public SysUser findSysUserByAccount(String account, String password) {
        if (account != null && !"".equals(account)) {
            SysUser sysUser = sysUserMapper.findSysUserByAccount(account);
            if (sysUser != null) {
                if (password != null && password.equals(sysUser.getPassword())) {
                    return sysUser;
                }
            }
        }
        return null;
    }

    @Override
    public void updateFaceIdForAccount(long accountid, String faceid) {
        sysUserMapper.updateFaceIdForAccount(accountid, faceid);
    }

    @Override
    public String selectFaceIdForUserId(long userid) {
        return sysUserMapper.selectFaceIdForUserId(userid);
    }

}
