package com.safecode.javacv.service;


import com.safecode.javacv.pojo.SysUser;

public interface SessionProvider {
    public void setAttributeForSysUser(String key, SysUser sysUser);
    public SysUser getAttributeForSysUser(String key);
    public void delAttributeForSysUser(String key);
}
