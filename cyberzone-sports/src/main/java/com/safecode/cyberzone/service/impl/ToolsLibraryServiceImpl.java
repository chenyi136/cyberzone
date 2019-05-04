package com.safecode.cyberzone.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.mapper.ToolsLibraryMapper;
import com.safecode.cyberzone.service.ToolsLibraryService;

@Service
@Transactional
public class ToolsLibraryServiceImpl implements ToolsLibraryService {

	 @Autowired
	 private ToolsLibraryMapper toolsLibraryMapper;
	 @Autowired
	 private SessionProvider sessionProvider;
	 
	 @Override
	 /** 工具库分页列表  */
	 public PageInfo<Map<String, Object>> queryPageList(Map<String, Object> params,HttpServletRequest request) {
		if (DataUtil.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (DataUtil.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 10);
        }
        
		PageHelper.startPage(params);
		Object createBy = params.get("createBy");
//		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
		long userId = 1;
		if(StringUtils.isNotBlank((String) createBy)){
			if("me".equals(createBy)){
				params.put("createBy", userId);
//				params.put("createBy", "test");
			}else {
				params.put("createBy", "");
			}
		}else{
			params.put("createBy", "");
		}
		
		Page<Map<String, Object>> page = toolsLibraryMapper.queryPageList(params);
		
		return new PageInfo<Map<String, Object>>(page);
	 }
	 
	 @Override
	 /** 工具详情  */
	 public Map<String, Object> selectById(Long id) {
		return toolsLibraryMapper.selectById(id);
	 }
	 
}
