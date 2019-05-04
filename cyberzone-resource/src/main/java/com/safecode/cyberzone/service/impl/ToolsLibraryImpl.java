package com.safecode.cyberzone.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.dto.CommonJsonResponse;
import com.safecode.cyberzone.global.consts.HttpCodeEnum;
import com.safecode.cyberzone.mapper.ToolsLibraryMapper;
import com.safecode.cyberzone.pojo.ToolsLibrary;
import com.safecode.cyberzone.service.ToolsLibraryService;

@Service
@Transactional
public class ToolsLibraryImpl implements ToolsLibraryService {

	 @Autowired
	 private ToolsLibraryMapper toolsLibraryMapper;
	 
	 @Override
	 public PageInfo<ToolsLibrary> queryPageList(ToolsLibrary toolsLibrary, Pageable pageable) {
		PageHelper.startPage(pageable);
		Page<ToolsLibrary> page = toolsLibraryMapper.queryPageList(toolsLibrary);
		
		return new PageInfo<ToolsLibrary>(page);
	 }
	 
	 
	 @Override
	 public ToolsLibrary selectById(Long id) {
		// TODO Auto-generated method stub
		return toolsLibraryMapper.selectById(id);
	 }
	 
	 @Override
	 public int delete(List id) {
		return toolsLibraryMapper.deleteById(id);
	 }

	 @Override
	 public int insert(ToolsLibrary toolsLibrary) {
		return toolsLibraryMapper.insert(toolsLibrary);
	 }
	
	 @Override
	 public int updateById(ToolsLibrary toolsLibrary) {
		return toolsLibraryMapper.updateById(toolsLibrary);
	 }

	@Override
	public Map<String, Object> getToolsView() {
//		return toolsLibraryMapper.getToolsView();
		int oneWeekVisitCount = toolsLibraryMapper.oneWeekVisitCount(); //一周访问量
		int oneWeekdownloadCount = toolsLibraryMapper.oneWeekdownloadCount();//一周下载量
		List systemList = toolsLibraryMapper.systemList();//操作系统
		List toolsTypeList = toolsLibraryMapper.toolsTypeList();//工具类别
		List topTenList = toolsLibraryMapper.topTenList();//工具下载排行榜
		List nearlyOneWeekList = toolsLibraryMapper.nearlyOneWeekList();//近一周下载量
		
		Map map = new HashMap();
		map.put("oneWeekVisitCount", oneWeekVisitCount);
		map.put("oneWeekdownloadCount", oneWeekdownloadCount);
		map.put("systemList", systemList);
		map.put("toolsTypeList", toolsTypeList);
		map.put("topTenList", topTenList);
		map.put("nearlyOneWeekList", nearlyOneWeekList);
		
		return map;
	}

}
