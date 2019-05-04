package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.ToolsLibrary;

import java.util.List;
import java.util.Map;

public interface ToolsLibraryMapper {
	
	Page<Map<String, Object>> queryPageList(Map<String, Object> params);
	Map<String, Object> selectById(Long id);
	int deleteById(Long id);
	int insert(ToolsLibrary toolsLibrary);
	int updateById(ToolsLibrary toolsLibrary);
	void addToolsLibrary(ToolsLibrary toolsLibrary);
	
}