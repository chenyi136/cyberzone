package com.safecode.cyberzone.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.mapper.AdMessageMapper;
import com.safecode.cyberzone.mapper.AdMonitorMapper;
import com.safecode.cyberzone.mapper.VmApplyMapper;
import com.safecode.cyberzone.service.AdMonitorService;


@Service
@Transactional
public class AdMonitorServiceImpl implements AdMonitorService {

	
	@Autowired
	private SessionProvider sessionProvider;
    @Autowired
    private AdMonitorMapper adMonitorMapper;
	@Autowired
	private AdMessageMapper adMessageMapper;
    @Autowired
    private VmApplyMapper vmApplyMapper;
    
    
    @Override
    public List<Map<String, Object>> queryCorpsVm(Long corpsId) {
    	// TODO Auto-generated method stub
    	List<Map<String, Object>> queryCorpsVm = new ArrayList<>();
		List<Long> corpsUserIdlist = adMessageMapper.selectCorpsUserIdsByCorpsId(corpsId);
        if (null != corpsUserIdlist && 0 < corpsUserIdlist.size()) {
        	queryCorpsVm = vmApplyMapper.queryCorpsVm(corpsUserIdlist);
        }
    	return queryCorpsVm;
    }
    
    
    @Override
    public ModelMap bigScreen() {
    	// TODO Auto-generated method stub
    	return null;
    }
	
    
}
