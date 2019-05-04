package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.TargetCity;
import com.safecode.cyberzone.pojo.ToolsLibrary;

public interface TargetCityService {

	int delete(List id);
	
	TargetCity selectById(Long id);

	int insert(TargetCity targetCity);

	int updateById(TargetCity targetCity);

	Map<String, Object> getCityView();

	PageInfo<TargetCity> queryPageList(TargetCity targetCity, Pageable pageable);


}
