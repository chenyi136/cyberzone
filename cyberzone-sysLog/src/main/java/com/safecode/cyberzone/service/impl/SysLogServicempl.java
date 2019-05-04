package com.safecode.cyberzone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safecode.cyberzone.mapper.SysLogMapper;
import com.safecode.cyberzone.pojo.SysLog;
import com.safecode.cyberzone.service.SysLogService;

@Service
@Transactional
public class SysLogServicempl implements SysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;
	
	@Override
	public int insert(SysLog sysLog) {
        return sysLogMapper.insert(sysLog);
	}
}
