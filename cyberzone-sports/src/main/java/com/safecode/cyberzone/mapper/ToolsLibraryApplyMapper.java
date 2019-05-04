package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.ToolsLibraryApply;
import java.util.Map;

public interface ToolsLibraryApplyMapper {
    int insert(ToolsLibraryApply toolsLibraryApply);

	Map<String, Object> selectById(Long id);

	int deleteById(Long id);

	Page<ToolsLibraryApply> queryPageList(Map<String, Object> params);

	int updateById(ToolsLibraryApply toolsLibraryApply);

	int examinationById(ToolsLibraryApply toolsLibraryApply);

	void addToToolsLibrary(ToolsLibraryApply toolsLibraryApply);

	Page<ToolsLibraryApply> queryApplyPageList(Map<String, Object> params);
}