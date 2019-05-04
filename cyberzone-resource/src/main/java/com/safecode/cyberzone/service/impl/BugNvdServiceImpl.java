package com.safecode.cyberzone.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.mapper.BugNvdMapper;
import com.safecode.cyberzone.pojo.BugNvd;
import com.safecode.cyberzone.service.BugNvdService;
import com.safecode.cyberzone.vo.BugNvdVo;


@Service
@Transactional
public class BugNvdServiceImpl implements BugNvdService {

	
    @Autowired
    private BugNvdMapper bugNvdMapper;
    
    @Override
    public PageInfo<BugNvd> queryPageList(BugNvdVo bugNvdVo) {
		Map<Object, Object> params = new HashMap<>();
    	if (bugNvdVo == null || DataUtil.isEmpty(bugNvdVo.getPageNum())) {
            params.put("pageNum", 1);
        }else{
        	params.put("pageNum", bugNvdVo.getPageNum());
        }
        if (bugNvdVo == null || DataUtil.isEmpty(bugNvdVo.getPageSize())) {
            params.put("pageSize", 10);
        }else{
        	params.put("pageSize", bugNvdVo.getPageSize());
        }
        
		PageHelper.startPage(params);
		Page<BugNvd> page = bugNvdMapper.queryPageList(bugNvdVo);
		
		return new PageInfo<BugNvd>(page);
	}

	@Override
	public BugNvdVo queryById(Long id) {
		BugNvdVo bugNvdVo = bugNvdMapper.queryById(id);
		return bugNvdVo;
	}
	
    
}
