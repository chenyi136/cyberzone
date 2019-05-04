package com.safecode.cyberzone.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.ToolsLibraryApply;

public interface ToolsLibraryApplyService {

	PageInfo<ToolsLibraryApply> queryPageList(Map<String, Object> params);

	int delete(Long id);

	Map<String, Object> selectById(Long id);

	int insert(ToolsLibraryApply toolsLibraryApply);

	int updateById(ToolsLibraryApply toolsLibraryApply);

	int examination(ToolsLibraryApply toolsLibraryApply);

	void addToToolsLibrary(ToolsLibraryApply toolsLibraryApply);

	PageInfo<ToolsLibraryApply> queryApplyPageList(Map<String, Object> params);

}
