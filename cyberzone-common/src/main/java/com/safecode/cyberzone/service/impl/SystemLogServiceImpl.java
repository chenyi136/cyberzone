package com.safecode.cyberzone.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safecode.cyberzone.vo.SysLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.mapper.SystemLogMapper;
import com.safecode.cyberzone.pojo.SysLog;
import com.safecode.cyberzone.pojo.SysLogSaveTime;
import com.safecode.cyberzone.pojo.SystemLog;
import com.safecode.cyberzone.service.SystemLogService;

@Service
@Transactional
public class SystemLogServiceImpl implements SystemLogService {


	@Autowired
	 private SystemLogMapper sysLogMapper;
	 
	 @Override
	 public PageInfo<SysLog> queryPageList(SysLogVo sysLogVo) {
	     Map params = new HashMap();
		if (DataUtil.isEmpty(params.get("pageNum"))) {
           params.put("pageNum", 1);
       }
       if (DataUtil.isEmpty(params.get("pageSize"))) {
           params.put("pageSize", 10);
       }

		 PageHelper.startPage(params);
		 Page<SysLog> page = sysLogMapper.queryPageList(sysLogVo);
		
		return new PageInfo<SysLog>(page);
	 }

	@Override
	public void insert(SysLogSaveTime sysLogSaveTime) {
		int count = sysLogMapper.selectCount(sysLogSaveTime);
		if(count>0) {
			sysLogMapper.update(sysLogSaveTime);
		}else {
			sysLogMapper.insert(sysLogSaveTime);
		}
	}

	@Override
	public List selectSaveTimeById() {
		return sysLogMapper.selectSaveTimeById();
	}
	
	@Override
	public void deleteSysLogBySaveTime() {
		int day = sysLogMapper.selectDay();
		if(day > 0) {
			sysLogMapper.delete(day);
		}
	}

	@Override
	public List querysystemList() {
		return sysLogMapper.querysystemList();
	}

	@Override
	public List exportSysLogList(Map<String, Object> params) {
		return sysLogMapper.exportSysLogList(params);
	}
	
	@Override
	public SystemLog queryLastLoginData(String account) {
		// TODO Auto-generated method stub
		return sysLogMapper.queryLastLoginData(account);
	}
	
}
