package com.safecode.cyberzone.base.service;

import com.safecode.cyberzone.base.pojo.SysUser;

public interface SessionProvider {
    public void setAttributeForSysUser(String key, SysUser sysUser);
    public SysUser getAttributeForSysUser(String key);
    public void delAttributeForSysUser(String key);
}
