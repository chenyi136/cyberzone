package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.SysLog;
import com.safecode.cyberzone.pojo.SysLogSaveTime;
import com.safecode.cyberzone.pojo.SystemLog;
import com.safecode.cyberzone.vo.SysLogVo;

import java.util.List;
import java.util.Map;

public interface SystemLogMapper {

	Page<SysLog> queryPageList(SysLogVo sysLogVo);

	int selectCount(SysLogSaveTime sysLogSaveTime);

	void update(SysLogSaveTime sysLogSaveTime);

	void insert(SysLogSaveTime sysLogSaveTime);

	List selectSaveTimeById();

	int selectDay();

	void delete(int day);

	List querysystemList();

	List exportSysLogList(Map<String, Object> params);

	SystemLog queryLastLoginData(String account);
	
}