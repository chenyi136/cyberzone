package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.SysLog;
import com.safecode.cyberzone.pojo.SysLogSaveTime;
import com.safecode.cyberzone.pojo.SystemLog;
import com.safecode.cyberzone.vo.SysLogVo;

public interface SystemLogService {

	PageInfo<SysLog> queryPageList(SysLogVo sysLogVo);

	void insert(SysLogSaveTime sysLogSaveTime);

	List selectSaveTimeById();

	void deleteSysLogBySaveTime();

	List querysystemList();

	List exportSysLogList(Map<String, Object> params);

	SystemLog queryLastLoginData(String account);

}
