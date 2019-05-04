package com.safecode.cyberzone.service.impl;

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
import com.safecode.cyberzone.mapper.TargetCityMapper;
import com.safecode.cyberzone.pojo.TargetCity;
import com.safecode.cyberzone.pojo.ToolsLibrary;
import com.safecode.cyberzone.service.TargetCityService;

@Service
@Transactional
public class TargetCityImpl implements TargetCityService {

	 @Autowired
	 private TargetCityMapper targetCityMapper;
	 
	 @Override
	 public PageInfo<TargetCity> queryPageList(TargetCity targetCity, Pageable pageable) {
		 PageHelper.startPage(pageable);
		 Page<TargetCity> page = targetCityMapper.queryPageList(targetCity);
			
		 return new PageInfo<TargetCity>(page);
	 }
	 
	 @Override
	 public TargetCity selectById(Long id) {
		// TODO Auto-generated method stub
		return targetCityMapper.selectById(id);
	 }
	 
	 @Override
	 public int delete(List id) {
		return targetCityMapper.deleteById(id);
	 }

	 @Override
	 public int insert(TargetCity targetCity) {
		return targetCityMapper.insert(targetCity);
	 }
	
	 @Override
	 public int updateById(TargetCity targetCity) {
		return targetCityMapper.updateById(targetCity);
	 }

	@Override
	public Map<String, Object> getCityView() {
		Map map = new HashMap();
		int oneWeekVisitCount = targetCityMapper.oneWeekVisitCount(); //一周访问量
		List cityTargetList = targetCityMapper.cityTargetList();//工具说明关键字
		List nearlyOneWeekList = targetCityMapper.nearlyOneWeekList();//近一周访问量
		
		map.put("oneWeekVisitCount", oneWeekVisitCount);
		map.put("cityTargetList", cityTargetList);
		map.put("nearlyOneWeekList", nearlyOneWeekList);
		
		return map;
	}

}
