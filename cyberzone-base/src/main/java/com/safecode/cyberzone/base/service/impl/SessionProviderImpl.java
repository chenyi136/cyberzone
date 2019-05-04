package com.safecode.cyberzone.base.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.service.SessionProvider;

@Service("sessionProvider")
public class SessionProviderImpl implements SessionProvider {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public void setAttributeForSysUser(String key, SysUser sysUser) {
    	String jsonString = JSON.toJSONString(sysUser);
    	redisTemplate.opsForValue().set(key, jsonString, 60 * 60 * 12, TimeUnit.MINUTES);
    }

    @Override
    public SysUser getAttributeForSysUser(String key) {
        if(key != null){
        	String jsonString = redisTemplate.opsForValue().get(key);
        	SysUser sysUser = JSON.parseObject(jsonString,SysUser.class);
            if(sysUser != null){
                return sysUser;
            }
        }
        return null;
    }


    @Override
    public void delAttributeForSysUser(String key) {
        redisTemplate.delete(key);
    }


}
