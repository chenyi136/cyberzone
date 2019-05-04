package com.safecode.cyberzone.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.mapper.ToolsLibraryApplyMapper;
import com.safecode.cyberzone.pojo.ToolsLibraryApply;
import com.safecode.cyberzone.service.ToolsLibraryApplyService;

@Service
@Transactional
public class ToolsLibraryApplyImpl implements ToolsLibraryApplyService {

	 @Autowired
	 private ToolsLibraryApplyMapper toolsLibraryApplyMapper;
	 
	 @Override
	 public PageInfo<ToolsLibraryApply> queryPageList(Map<String, Object> params) {
		if (DataUtil.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (DataUtil.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 10);
        }
        
		PageHelper.startPage(params);
		Page<ToolsLibraryApply> page = toolsLibraryApplyMapper.queryPageList(params);
		
		return new PageInfo<ToolsLibraryApply>(page);
	 }
	 
	 @Override
	 public Map<String, Object> selectById(Long id) {
		return toolsLibraryApplyMapper.selectById(id);
	 }
	 
	 @Override
	 public int delete(Long id) {
		return toolsLibraryApplyMapper.deleteById(id);
	 }

	 @Override
	 public int insert(ToolsLibraryApply toolsLibraryApply) {
		return toolsLibraryApplyMapper.insert(toolsLibraryApply);
	 }
	
	 @Override
	 public int updateById(ToolsLibraryApply toolsLibraryApply) {
		return toolsLibraryApplyMapper.updateById(toolsLibraryApply);
	 }

	@Override
	public int examination(ToolsLibraryApply toolsLibraryApply) {
		return toolsLibraryApplyMapper.examinationById(toolsLibraryApply);
	}

	@Override
	public void addToToolsLibrary(ToolsLibraryApply toolsLibraryApply) {
		toolsLibraryApplyMapper.addToToolsLibrary(toolsLibraryApply);
	}

	@Override
	public PageInfo<ToolsLibraryApply> queryApplyPageList(Map<String, Object> params) {
		if (DataUtil.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (DataUtil.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 10);
        }
        
		PageHelper.startPage(params);
		Page<ToolsLibraryApply> page = toolsLibraryApplyMapper.queryApplyPageList(params);
		
		return new PageInfo<ToolsLibraryApply>(page);
	}

}
