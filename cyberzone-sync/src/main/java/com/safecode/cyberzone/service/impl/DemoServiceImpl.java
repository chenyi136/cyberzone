package com.safecode.cyberzone.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.mapper.DemoMapper;
import com.safecode.cyberzone.service.DemoService;

@Service
@Transactional
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;
	
	@Override
	public PageInfo<Map> queryPageList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		if (DataUtil.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (DataUtil.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 10);
        }
        if (DataUtil.isEmpty(params.get("orderBy"))) {
            params.put("orderBy", "id_ desc");
        }

		PageHelper.startPage(params);
		Page<Map> page = demoMapper.queryPageList(params);
		
		return new PageInfo<Map>(page);		
	}
	
}
