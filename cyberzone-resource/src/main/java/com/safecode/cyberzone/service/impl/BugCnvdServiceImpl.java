package com.safecode.cyberzone.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.mapper.BugCnvdMapper;
import com.safecode.cyberzone.pojo.BugCnvd;
import com.safecode.cyberzone.service.BugCnvdService;
import com.safecode.cyberzone.vo.BugCnvdVo;


@Service
@Transactional
public class BugCnvdServiceImpl implements BugCnvdService {

	
    @Autowired
    private BugCnvdMapper bugCnvdMapper;
    
    
	@Override
	public PageInfo<BugCnvd> queryPageList(BugCnvdVo cnvdVo) {
		Map<Object,Object> params = new HashMap<>();
		if (cnvdVo == null || DataUtil.isEmpty(cnvdVo.getPageNum())) {
            params.put("pageNum", 1);
        }else{
        	params.put("pageNum", cnvdVo.getPageNum());
        }
        if (cnvdVo == null || DataUtil.isEmpty(cnvdVo.getPageSize())) {
            params.put("pageSize", 10);
        }else{
        	params.put("pageSize", cnvdVo.getPageSize());
        }
		PageHelper.startPage(params);
		Page<BugCnvd> page = bugCnvdMapper.queryPageList(cnvdVo);
		
		return new PageInfo<BugCnvd>(page);
	}

	@Override
	public BugCnvdVo queryById(Long id) {
		return bugCnvdMapper.queryById(id);
	}

	@Override
	public List<Map<String, Object>> getBugCountByServerity() {
		// TODO Auto-generated method stub
		return bugCnvdMapper.getBugCountByServerity();
	}

	@Override
	public List<Map<String, Object>> getBugCountByYear() {
		// TODO Auto-generated method stub
		return bugCnvdMapper.getBugCountByYear();
	}

	@Override
	public List<BugCnvd> getKeyNews() {
		// TODO Auto-generated method stub
		return bugCnvdMapper.getKeyNews();
	}

	@Override
	public List<Map<String, Object>> getBugCountByClassify() {
		// TODO Auto-generated method stub
		return bugCnvdMapper.getBugCountByClassify();
	}
	
}
