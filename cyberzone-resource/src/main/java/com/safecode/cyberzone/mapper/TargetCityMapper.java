package com.safecode.cyberzone.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.TargetCity;

public interface TargetCityMapper {
	
	Page<TargetCity> queryPageList(TargetCity targetCity);
	TargetCity selectById(Long id);
	int deleteById(List id);
	int insert(TargetCity targetCity);
	int updateById(TargetCity targetCity);
	int oneWeekVisitCount();
	List cityTargetList();
	List nearlyOneWeekList();
}