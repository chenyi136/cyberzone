package com.safecode.cyberzone.service;


import com.safecode.cyberzone.pojo.SysUser;

public interface SessionProvider {
    public void setAttributeForSysUser(String key, SysUser sysUser);
    public SysUser getAttributeForSysUser(String key);
    public void delAttributeForSysUser(String key);
}
