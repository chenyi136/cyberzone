package com.safecode.cyberzone.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.ToolsLibrary;

public interface ToolsLibraryService {

	PageInfo<Map<String, Object>> queryPageList(Map<String, Object> params, HttpServletRequest request);

	Map<String, Object> selectById(Long id);

}
