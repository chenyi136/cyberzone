package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.ToolsLibrary;

public interface ToolsLibraryService {

	int delete(List id);

	ToolsLibrary selectById(Long id);


	int insert(ToolsLibrary toolsLibrary);

	int updateById(ToolsLibrary toolsLibrary);

	Map<String, Object> getToolsView();

	PageInfo<ToolsLibrary> queryPageList(ToolsLibrary toolsLibrary, Pageable pageable);


}
