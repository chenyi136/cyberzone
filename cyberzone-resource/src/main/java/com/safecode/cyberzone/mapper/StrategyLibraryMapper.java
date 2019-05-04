package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.StrategyLibrary;
import com.safecode.cyberzone.pojo.TargetInfrastructure;
import com.safecode.cyberzone.vo.StrategyLibraryVo;

import java.util.List;
import java.util.Map;

public interface StrategyLibraryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrategyLibrary record);

    StrategyLibrary selectByPrimaryKey(Long id);

    List<StrategyLibrary> selectAll();

    int updateByPrimaryKey(StrategyLibrary record);

	List<StrategyLibrary> queryAll(StrategyLibraryVo strategyLibraryVo);

	Page<StrategyLibrary> queryPageList(StrategyLibraryVo strategyLibraryVo);
}