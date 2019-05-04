package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.ToolsLibrary;

import java.util.List;
import java.util.Map;

public interface ToolsLibraryMapper {
	
	Page<ToolsLibrary> queryPageList(ToolsLibrary toolsLibrary);
	ToolsLibrary selectById(Long id);
	int deleteById(List id);
	int insert(ToolsLibrary toolsLibrary);
	int updateById(ToolsLibrary toolsLibrary);
	void addToolsLibrary(ToolsLibrary toolsLibrary);
	Map<String, Object> getToolsView();
	
	int oneWeekVisitCount();
	int oneWeekdownloadCount();
	List systemList();
	List toolsTypeList();
	List topTenList();
	List nearlyOneWeekList();
	
}